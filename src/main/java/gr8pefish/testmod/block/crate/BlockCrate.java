package gr8pefish.testmod.block.crate;

import gr8pefish.testmod.TestMod;
import gr8pefish.testmod.block.helper.BlockTileEntity;
import gr8pefish.testmod.network.GuiHandler;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;

/**
 * Basic block class
 *
 * Has a linked TileEntity here: {@link TileEntityCrate}
 */
public class BlockCrate extends BlockTileEntity<TileEntityCrate> {

    /** Field for use in unlocalized and registry naming*/
    public static final String CRATE_NAME = "crate";

    /** Constructor */
    public BlockCrate() {
        super(Material.ROCK, CRATE_NAME);
    }


    @Override
    public Class<TileEntityCrate> getTileEntityClass() {
        return TileEntityCrate.class;
    }

    @Nullable
    @Override
    public TileEntityCrate createTileEntity(World world, IBlockState state) {
        return new TileEntityCrate();
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            TileEntityCrate tile = getTileEntity(world, pos);
            IItemHandler itemHandler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side);
            if (!player.isSneaking()) {
                if (player.getHeldItem(hand).isEmpty()) {
                    player.setHeldItem(hand, itemHandler.extractItem(0, 64, false));
                } else {
                    player.setHeldItem(hand, itemHandler.insertItem(0, player.getHeldItem(hand), false));
                }
                tile.markDirty();
            } else {
                player.openGui(TestMod.instance, GuiHandler.CRATE, world, pos.getX(), pos.getY(), pos.getZ());
            }
        }
        return true;
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        TileEntityCrate tile = getTileEntity(world, pos);
        IItemHandler itemHandler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH);
        ItemStack stack = itemHandler.getStackInSlot(0);
        if (!stack.isEmpty()) {
            spawnAsEntity(world, pos, stack);
        }
        super.breakBlock(world, pos, state);
    }


}
