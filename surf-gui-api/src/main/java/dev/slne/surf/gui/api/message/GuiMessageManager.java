package dev.slne.surf.gui.api.message;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;

public class GuiMessageManager {

    public static final TextColor PRIMARY = TextColor.fromHexString("#3b92d1");
    public static final TextColor SECONDARY = TextColor.fromHexString("#5b5b5b");

    public static final TextColor INFO = TextColor.fromHexString("#40d1db");
    public static final TextColor SUCCESS = TextColor.fromHexString("#65ff64");
    public static final TextColor WARNING = TextColor.fromHexString("#f9c353");
    public static final TextColor ERROR = TextColor.fromHexString("#ee3d51");

    public static final TextColor VARIABLE_KEY = GuiMessageManager.INFO;
    public static final TextColor VARIABLE_VALUE = GuiMessageManager.WARNING;
    public static final TextColor SPACER = NamedTextColor.GRAY;
    public static final TextColor DARK_SPACER = NamedTextColor.DARK_GRAY;

    /**
     * Utility class
     */
    private GuiMessageManager() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Returns a prefix for the plugin.
     *
     * @return The prefix for the plugin.
     */
    public static Component prefix() {
        TextComponent.Builder builder = Component.text();

        builder.append(Component.text(">> ", NamedTextColor.DARK_GRAY));
        builder.append(Component.text("SG", GuiMessageManager.PRIMARY));
        builder.append(Component.text(" | ", NamedTextColor.DARK_GRAY));

        return builder.build();
    }

    /**
     * Returns the component that informs the user about not having enough
     * permissions
     *
     * @return the component
     */
    public static Component getNoPermissionComponent() {
        return prefix().append(Component.text("Du besitzt keine Berechtigung für diesen Befehl.", ERROR));
    }
}
