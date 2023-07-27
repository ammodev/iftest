package dev.slne.surf.gui.api.anvil.requirement;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface AnvilRequirement {

    /**
     * Returns the description
     *
     * @param description  the description
     * @param stateColor   the state color
     * @param currentInput the current input
     *
     * @return the description
     */
    List<Component> getDescription(List<Component> description, TextColor stateColor, String currentInput);

    /**
     * Returns if the input is met
     *
     * @param input the input
     *
     * @return if the input is met
     */
    CompletableFuture<Boolean> isMet(String input);

}
