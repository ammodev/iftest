package dev.slne.surf.gui.server;

import dev.slne.surf.gui.api.GuiInstance;
import org.bukkit.plugin.java.JavaPlugin;

public class CoreGuiInstance implements GuiInstance {
    
    @Override
    public JavaPlugin getPlugin() {
        return GuiMain.getInstance();
    }
}
