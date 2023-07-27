package dev.slne.surf.gui.api;

import com.github.stefvanschie.inventoryframework.gui.type.util.Gui;
import com.github.stefvanschie.inventoryframework.gui.type.util.NamedGui;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public interface SurfGui {

    /**
     * Gets the parent gui
     *
     * @return the parent gui
     */
    @Nullable SurfGui getParent();

    /**
     * Checks if this gui has a parent
     *
     * @return true if it has a parent, false otherwise
     */
    default boolean hasParent() {
        return getParent() != null;
    }

    /**
     * Goes back to the parent gui
     */
    default void backToParent() {
        if (hasParent()) {
            Gui gui = Objects.requireNonNull(getParent()).getGui();

            gui.show(getViewingPlayer());
            gui.update();

            return;
        }

        getViewingPlayer().closeInventory(InventoryCloseEvent.Reason.PLUGIN);
    }

    /**
     * Walks the parents of this gui
     *
     * @return the parents
     */
    default @NotNull List<SurfGui> walkParents() {
        List<SurfGui> parents = new ArrayList<>();

        SurfGui parent = getParent();
        while (parent != null) {
            parents.add(parent);
            parent = parent.getParent();
        }

        return parents;
    }

    /**
     * Gets the player viewing the gui
     *
     * @return the player
     */
    @NotNull Player getViewingPlayer();

    /**
     * Returns the gui
     *
     * @return the gui
     */
    @NotNull NamedGui getGui();
}
