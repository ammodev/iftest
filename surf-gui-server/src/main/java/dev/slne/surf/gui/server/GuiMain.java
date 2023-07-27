package dev.slne.surf.gui.server;

import dev.slne.surf.gui.api.GuiApi;
import dev.slne.surf.gui.api.GuiInstance;
import org.bukkit.plugin.java.JavaPlugin;

public class GuiMain extends JavaPlugin {

    private static GuiMain instance;

    /**
     * Gets the instance of this plugin.
     *
     * @return The instance of this plugin.
     */
    public static GuiMain getInstance() {
        return instance;
    }

    @Override
    public void onLoad() {
        instance = this;

        GuiInstance guiInstance = new CoreGuiInstance();
        GuiApi.setInstance(guiInstance);
    }
}
