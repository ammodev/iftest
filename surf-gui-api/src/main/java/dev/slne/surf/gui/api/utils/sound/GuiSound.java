package dev.slne.surf.gui.api.utils.sound;

import org.bukkit.Sound;

public enum GuiSound {

    CLICK(Sound.UI_BUTTON_CLICK),
    BACK(Sound.UI_BUTTON_CLICK),
    ERROR(Sound.ENTITY_VILLAGER_NO),
    SUCCESS(Sound.ENTITY_PLAYER_LEVELUP),
    DENY_ACTION(Sound.ENTITY_VILLAGER_NO),
    CONFIRM_ACTION(Sound.ENTITY_VILLAGER_YES);

    private final Sound sound;

    /**
     * Creates a new gui sound.
     *
     * @param sound The sound to play.
     */
    GuiSound(Sound sound) {
        this.sound = sound;
    }

    /**
     * Gets the sound to play.
     *
     * @return The sound to play.
     */
    public Sound getSound() {
        return sound;
    }

}
