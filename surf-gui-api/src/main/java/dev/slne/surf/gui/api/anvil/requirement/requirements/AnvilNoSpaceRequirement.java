package dev.slne.surf.gui.api.anvil.requirement.requirements;

import dev.slne.surf.gui.api.anvil.requirement.AnvilRequirement;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class AnvilNoSpaceRequirement implements AnvilRequirement {

    @Override
    public List<Component> getDescription(List<Component> description, TextColor stateColor, String currentInput) {
        description.add(Component.text("Die Eingabe darf keine Leerzeichen enthalten.", stateColor));

        return description;
    }

    @Override
    public CompletableFuture<Boolean> isMet(String input) {
        return CompletableFuture.completedFuture(!input.contains(" "));
    }
}