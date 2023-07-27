package dev.slne.surf.gui.api.anvil.requirement.requirements.player;

import dev.slne.surf.gui.api.anvil.requirement.AnvilRequirement;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.Bukkit;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class AnvilOfflinePlayerRequirement implements AnvilRequirement {

    @Override
    public List<Component> getDescription(List<Component> description, TextColor stateColor, String currentInput) {
        description.add(Component.text("Die Eingabe muss einem Spielernamen entsprechen.", stateColor));

        return description;
    }

    @Override
    public CompletableFuture<Boolean> isMet(String input) {
        input = input.trim().replace(" ", "");

        if (input.isEmpty()) {
            return CompletableFuture.completedFuture(false);
        }

        return CompletableFuture.completedFuture(Bukkit.getOfflinePlayer(input).hasPlayedBefore());
    }
}
