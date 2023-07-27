package dev.slne.surf.gui.api.chest;

import com.github.stefvanschie.inventoryframework.adventuresupport.ComponentHolder;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.gui.type.util.NamedGui;
import dev.slne.surf.gui.api.SurfGui;
import dev.slne.surf.gui.api.utils.GuiUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class SurfChestGui extends ChestGui implements SurfGui {

    private final SurfGui parent;
    private final Player viewingPlayer;

    /**
     * Creates a new chest gui.
     *
     * @param parent        the parent gui
     * @param rows          the amount of rows this gui should have
     * @param title         the title of this gui
     * @param viewingPlayer the player viewing the gui
     */
    protected SurfChestGui(@Nullable SurfGui parent, int rows, @NotNull Component title,
                           @NotNull Player viewingPlayer) {
        super(rows, ComponentHolder.of(title.colorIfAbsent(NamedTextColor.BLACK)));

        this.parent = parent;
        this.viewingPlayer = viewingPlayer;

        if (rows < 2) {
            throw new IllegalArgumentException("Rows must be at least 2");
        }

        setOnGlobalClick(event -> event.setCancelled(true));
        setOnGlobalDrag(event -> event.setCancelled(true));

        addPane(GuiUtils.getOutline(0));
        addPane(GuiUtils.getOutline(rows - 1));
        addPane(GuiUtils.getNavigation(this, rows - 1));
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
