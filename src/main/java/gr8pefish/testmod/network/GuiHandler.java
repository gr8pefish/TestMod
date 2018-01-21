package gr8pefish.testmod.network;

import gr8pefish.testmod.block.crate.TileEntityCrate;
import gr8pefish.testmod.client.gui.GuiCrate;
import gr8pefish.testmod.inventory.ContainerCrate;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

/**
 * Delegates calls to each logical side to open containers or GUIs.
 *
 * Typically called from {@link net.minecraft.item.Item#onItemRightClick(World, EntityPlayer, EnumHand)}
 * or {@link net.minecraft.block.Block#onBlockActivated(World, BlockPos, IBlockState, EntityPlayer, EnumHand, EnumFacing, float, float, float)}
 */
public class GuiHandler implements IGuiHandler {

    /** All the containerized items, mapped to unique IDs for ease of use*/
    public static final int CRATE = 0;

    /** Initialize containers for the server */
    @Override
    public Container getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID) {
            case CRATE:
                return new ContainerCrate(player.inventory, (TileEntityCrate) world.getTileEntity(new BlockPos(x, y, z)));
            default:
                return null;
        }
    }

    /** Open GUIs for the client */
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID) {
            case CRATE:
                return new GuiCrate(getServerGuiElement(ID, player, world, x, y, z), player.inventory);
            default:
                return null;
        }
    }

}
