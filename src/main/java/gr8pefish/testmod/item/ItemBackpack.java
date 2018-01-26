package gr8pefish.testmod.item;

import gr8pefish.testmod.RegistrarTestMod;
import gr8pefish.testmod.TestMod;
import gr8pefish.testmod.api.BackpackVariantEnum;
import gr8pefish.testmod.block.backpack.BlockBackpack;
import gr8pefish.testmod.util.ModInfo;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class ItemBackpack extends Item {

    /** Field for use in unlocalized and registry naming*/
    public static final String BACKPACK_NAME = "backpack_item";

    private BackpackVariantEnum backpackVariant;

    /**
     * Constructor
     *
     * Sets unlocalized name and creative tab
     */
    public ItemBackpack(BackpackVariantEnum backpackVariant) {
        setUnlocalizedName(ModInfo.MODID + "."+ BACKPACK_NAME);
        setCreativeTab(TestMod.CREATIVE_TAB_TEST_MOD);
        this.backpackVariant = backpackVariant;
    }

    @Override
    public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {

        IBlockState blockState = world.getBlockState(pos);
        Block block = blockState.getBlock();

        if (player.isSneaking()) { //sneaking
            if (!block.equals(Blocks.AIR)) { //non-air block clicked
                if (block.hasTileEntity(blockState)) { //check for TE
                    TileEntity tile = world.getTileEntity(pos);
                    if (tile.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side)) { //check for IItemHandler
                        IItemHandler handler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, side);
                        //if backpack has quick deposit upgrade, deposit stuff and cancel
                        TestMod.LOGGER.warn("Clicked on IItemHandler");
                    }
                } else {
                    //try to place the backpack
                    Block backpackBlock = RegistrarTestMod.BACKPACK_BLOCK;
                    if (world.mayPlace(backpackBlock, pos.up(), false, side, player)) {
                        world.setBlockState(pos.up(), backpackBlock.getBlockState().getBaseState()
                                .withProperty(BlockBackpack.BACKPACK_VARIANT, this.backpackVariant)
                                .withProperty(BlockBackpack.FACING, player.getHorizontalFacing().getOpposite()));
                    }
                }
            }
        }
        return super.onItemUseFirst(player, world, pos, side, hitX, hitY, hitZ, hand);
    }


}
