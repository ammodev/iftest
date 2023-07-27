package dev.slne.surf.gui.api.confirmation;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.gui.type.util.NamedGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import dev.slne.surf.gui.api.SurfGui;
import dev.slne.surf.gui.api.utils.GuiUtils;
import dev.slne.surf.gui.api.utils.ItemUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ConfirmationGui extends ChestGui implements SurfGui {

    private final SurfGui parent;
    private final Player viewingPlayer;

    private final Consumer<InventoryClickEvent> onConfirm;
    private final Consumer<InventoryEvent> onCancel;

    /**
     * Creates a new confirmation gui.
     *
     * @param previousGui   the gui to return to when the player cancels
     * @param viewingPlayer the player viewing the gui
     * @param onConfirm     the action to perform when the player confirms
     * @param onCancel      the action to perform when the player cancels
     * @param questionLabel the label of the question
     * @param questionLore  the lore of the question
     */
    public ConfirmationGui(SurfGui previousGui, Player viewingPlayer, Consumer<InventoryClickEvent> onConfirm,
                           Consumer<InventoryEvent> onCancel, Component questionLabel, List<Component> questionLore) {
        super(5, "Bestätigung erforderlich");

        this.parent = previousGui;
        this.viewingPlayer = viewingPlayer;

        this.onConfirm = onConfirm;
        this.onCancel = onCancel;

        setOnGlobalClick(event -> event.setCancelled(true));

        setOnClose(event -> cancel(event, (Player) event.getPlayer()));

        StaticPane confirmationPane = new StaticPane(0, 0, 9, 5);

        confirmationPane.addItem(new GuiItem(ItemUtils.confirmationConfirmItem(), this::confirm), 1, 2);
        confirmationPane.addItem(
                new GuiItem(ItemUtils.confirmationCancelItem(), event -> cancel(event, (Player) event.getWhoClicked())),
                7, 2);

        List<Component> lore = new ArrayList<>();
        lore.add(Component.empty());
        lore.add(Component.text("Es ist eine Bestätigung erforderlich...", NamedTextColor.GRAY));
        lore.add(Component.empty());
        lore.addAll(questionLore);
        lore.add(Component.empty());

        confirmationPane.addItem(new GuiItem(
                        ItemUtils.confirmationQuestionItem(questionLabel, lore.stream().toArray(size -> new Component[size]))),
                4, 2);

        addPane(GuiUtils.getOutline(0));
        addPane(GuiUtils.getOutline(4));
        addPane(confirmationPane);
    }

    /**
     * Cancels the action
     *
     * @param event  the event
     * @param player the player
     */
    public void cancel(InventoryEvent event, Player player) {
        if (onCancel != null) {
            onCancel.accept(event);
        }
    }

    /**
     * Confirms the action
     *
     * @param event the event
     */
    public void confirm(InventoryClickEvent event) {
        if (onConfirm != null) {
            onConfirm.accept(event);
        }
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
