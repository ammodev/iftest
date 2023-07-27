package dev.slne.surf.gui.api.anvil;

import com.github.stefvanschie.inventoryframework.adventuresupport.ComponentHolder;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.AnvilGui;
import com.github.stefvanschie.inventoryframework.gui.type.util.NamedGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import dev.slne.surf.gui.api.SurfGui;
import dev.slne.surf.gui.api.anvil.requirement.AnvilRequirement;
import dev.slne.surf.gui.api.message.GuiMessageManager;
import dev.slne.surf.gui.api.utils.ItemUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class SurfAnvilGui extends AnvilGui implements SurfGui {

    private final SurfGui parent;
    private final Player viewingPlayer;

    private final StaticPane firstItemPane;
    private final StaticPane resultItemPane;
    private GuiItem firstItem;

    private boolean success;

    /**
     * Creates a new anvil gui.
     *
     * @param title the title of this gui
     */
    public SurfAnvilGui(@Nullable SurfGui parent, @NotNull Component title, @NotNull Player viewingPlayer) {
        super(ComponentHolder.of(title.colorIfAbsent(NamedTextColor.BLACK)));

        this.parent = parent;
        this.viewingPlayer = viewingPlayer;

        setOnGlobalClick(event -> event.setCancelled(true));
        setOnGlobalDrag(event -> event.setCancelled(true));

        setOnClose(event -> {
            if (!success) {
                onCancel(getRenameText());
            }
        });

        StaticPane secondItemPane = new StaticPane(0, 0, 1, 1);
        this.firstItemPane = new StaticPane(0, 0, 1, 1);
        this.resultItemPane = new StaticPane(0, 0, 1, 1);

        setFirstItem(getRenameText());

        getFirstItemComponent().addPane(firstItemPane);
        getSecondItemComponent().addPane(secondItemPane);
        getResultComponent().addPane(resultItemPane);

        handleInput(getRenameText());
        setOnNameInputChanged(this::handleInput);
    }

    /**
     * Returns the requirements
     *
     * @param requirements the requirements
     *
     * @return the requirements
     */
    public abstract List<AnvilRequirement> getRequirements(List<AnvilRequirement> requirements);

    /**
     * Called when the player submits the anvil gui
     *
     * @param input the input
     */
    public abstract void onSubmit(String input);

    /**
     * Called when the player cancels the anvil gui
     *
     * @param input the input
     */
    public abstract void onCancel(String input);

    /**
     * Sets the first item
     *
     * @param newInput the new input
     */
    private void setFirstItem(String newInput) {
        this.firstItem = new GuiItem(ItemUtils.item(Material.PAPER, 1, 0, Component.text(newInput), Component.empty(),
                Component.text("Klicke, um den Vorgang abzubrechen", NamedTextColor.GRAY), Component.empty()),
                event -> {
                    getViewingPlayer().closeInventory();
                });
        this.firstItemPane.addItem(this.firstItem, 0, 0);
    }

    /**
     * Handles the input
     *
     * @param newInput the new input
     */
    private void handleInput(String newInput) {
        setFirstItem(newInput);

        this.resultItemPane.addItem(buildItem(getMetRequirements(newInput), newInput), 0, 0);

        update();
    }

    /**
     * Returns the met requirements
     *
     * @param newInput the new input
     *
     * @return the met requirements
     */
    private Map<AnvilRequirement, Boolean> getMetRequirements(String newInput) {
        Map<AnvilRequirement, Boolean> requirementsMet = new HashMap<>();
        List<AnvilRequirement> requirements = getRequirements(new ArrayList<>());

        for (AnvilRequirement requirement : requirements) {
            requirementsMet.put(requirement, requirement.isMet(newInput).join());
        }

        return requirementsMet;
    }

    /**
     * Returns if all requirements are met
     *
     * @param newInput the new input
     *
     * @return if all requirements are met
     */
    private boolean allRequirementsMet(String newInput) {
        for (boolean met : getMetRequirements(newInput).values()) {
            if (!met) {
                return false;
            }
        }

        return true;
    }

    /**
     * Builds the item
     *
     * @param requirementsMet the requirements met
     * @param newInput        the new input
     *
     * @return the item
     */
    private GuiItem buildItem(Map<AnvilRequirement, Boolean> requirementsMet, String newInput) {
        List<Component> components = new ArrayList<>();

        if (requirementsMet.size() > 0) {
            components.add(Component.empty());
            components.add(Component.text("Voraussetzungen:", NamedTextColor.GRAY));
            components.add(Component.empty());
        }

        for (Map.Entry<AnvilRequirement, Boolean> requirementEntry : requirementsMet.entrySet()) {
            AnvilRequirement requirement = requirementEntry.getKey();
            boolean met = requirementEntry.getValue();

            String icon = met ? "✔" : "✖";
            TextColor stateColor = met ? GuiMessageManager.SUCCESS : GuiMessageManager.ERROR;

            List<Component> requirementLore =
                    requirement.getDescription(new ArrayList<>(), stateColor, newInput);

            boolean first = true;
            for (Component requirementComponent : requirementLore) {
                TextComponent.Builder builder = Component.text();
                if (first) {
                    builder.append(Component.text(icon, stateColor));
                    builder.append(Component.space());
                } else {
                    for (int i = 0; i < 3; i++) {
                        builder.append(Component.space());
                    }
                }
                builder.append(requirementComponent);

                components.add(builder.build());
                first = false;
            }
        }

        components.add(Component.empty());
        components.add(Component.text("Klicke, um zu bestätigen.", NamedTextColor.GRAY));
        components.add(Component.empty());
        Component[] loreArray = components.toArray(Component[]::new);

        return new GuiItem(ItemUtils.item(Material.NAME_TAG, 1, 0,
                Component.text(newInput), loreArray), event -> {
            if (allRequirementsMet(newInput)) {
                this.success = true;
                onSubmit(newInput);
            }
        });
    }

    @Override
    public @Nullable SurfGui getParent() {
        return parent;
    }

    @Override
    public @NotNull Player getViewingPlayer() {
        return viewingPlayer;
    }

    @Override
    public @NotNull NamedGui getGui() {
        return this;
    }
}
