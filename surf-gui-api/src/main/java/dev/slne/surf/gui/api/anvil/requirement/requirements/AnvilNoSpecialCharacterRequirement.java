package dev.slne.surf.gui.api.anvil.requirement.requirements;

import dev.slne.surf.gui.api.anvil.requirement.AnvilRequirement;
import dev.slne.surf.gui.api.message.GuiMessageManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class AnvilNoSpecialCharacterRequirement implements AnvilRequirement {

    private final String allowedCharacters;

    /**
     * Creates a new AnvilNoSpecialCharacterRequirement.
     *
     * @param allowedCharacters The characters that are allowed to be entered.
     */
    public AnvilNoSpecialCharacterRequirement(String allowedCharacters) {
        this.allowedCharacters = allowedCharacters;
    }

    @Override
    public List<Component> getDescription(List<Component> description, TextColor stateColor, String currentInput) {
        description.add(Component.text("Die Eingabe darf nur die folgenden Zeichen enthalten:", stateColor));
        description.add(Component.text(allowedCharacters, GuiMessageManager.VARIABLE_VALUE));

        return description;
    }

    @Override
    public CompletableFuture<Boolean> isMet(String input) {
        return CompletableFuture.completedFuture(input.matches("[" + allowedCharacters + "]+"));
    }
}
