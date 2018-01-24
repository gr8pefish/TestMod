package gr8pefish.testmod.client.gui;

import gr8pefish.testmod.RegistrarTestMod;
import gr8pefish.testmod.util.ModInfo;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

/**
 * Gui for the crate
 */
public class GuiBackpack extends GuiContainer {

    /** Field to hold the background texture location */
    private static final ResourceLocation BG_TEXTURE = new ResourceLocation(ModInfo.MODID, "textures/gui/crate.png"); //TODO: 9 slot texture

    /** Field for the player's inventory */
    private InventoryPlayer playerInv;

    /** Basic constructor */
    public GuiBackpack(Container container, InventoryPlayer playerInv) {
        super(container);
        this.playerInv = playerInv;
    }

    /** Draw background texture */
    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1, 1, 1, 1); //no color
        mc.getTextureManager().bindTexture(BG_TEXTURE); //bind backgorund texture
        int x = (width - xSize) / 2; //math for correct size
        int y = (height - ySize) / 2;
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize); //draw the background
    }

    /** Draw foreground labels */
    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String name = I18n.format(RegistrarTestMod.BACKPACK_BLOCK.getUnlocalizedName() + ".name"); //get backpack name ("Backpack")
        fontRenderer.drawString(name, xSize / 2 - fontRenderer.getStringWidth(name) / 2, 6, 0x404040); //print crate name ("Backpack")
        fontRenderer.drawString(playerInv.getDisplayName().getUnformattedText(), 8, ySize - 94, 0x404040); //print player's inventory name ("Inventory")
    }

}
