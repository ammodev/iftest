package dev.slne.surf.gui.api;

public class GuiApi {

    private static GuiInstance instance;

    /**
     * Creates a new gui api.
     *
     * @param instance the instance
     */
    public GuiApi(GuiInstance instance) {
        GuiApi.instance = instance;
    }

    /**
     * Returns the instance of the gui api
     *
     * @return the instance
     */
    public static GuiInstance getInstance() {
        return instance;
    }

    /**
     * Sets the instance of the gui api
     *
     * @param instance the instance
     */
    public static void setInstance(GuiInstance instance) {
        GuiApi.instance = instance;
    }
}
