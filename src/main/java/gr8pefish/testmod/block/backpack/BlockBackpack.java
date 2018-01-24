package gr8pefish.testmod.block.backpack;

import gr8pefish.testmod.TestMod;
import gr8pefish.testmod.api.BackpackVariantEnum;
import gr8pefish.testmod.block.crate.TileEntityCrate;
import gr8pefish.testmod.block.helper.BlockTileEntity;
import gr8pefish.testmod.network.GuiHandler;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;

/**
 * Block with a TileEntity
 *
 * {@link TileEntityCrate}
 */
public class BlockBackpack extends BlockTileEntity<TileEntityBackpack> {

    /** Field for use in unlocalized and registry naming*/
    public static final String BACKPACK_NAME = "backpack_block";

    public static final IProperty<BackpackVariantEnum> VARIANT = PropertyEnum.create("variant", BackpackVariantEnum.class);

    private BackpackVariantEnum variant;

    /** Constructor */
    public BlockBackpack(BackpackVariantEnum variant) {
        super(Material.ROCK, BACKPACK_NAME+"_"+variant.toString().toLowerCase());
        this.variant = variant;
        setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, variant));
    }

    public BackpackVariantEnum getBackpackVariant() {
        return variant;
    }

    //BlockTileEntity Methods

    @Override
    public Class<TileEntityBackpack> getTileEntityClass() {
        return TileEntityBackpack.class;
    }

    @Nullable
    @Override
    public TileEntityBackpack createTileEntity(World world, IBlockState state) {
        return new TileEntityBackpack();
    }

    //Block Overrides

    /**
     * When the block is clicked:
     *
     * if not sneaking:
     *      if empty hand:
     *          remove items from crate
     *      else:
     *          place items into crate
     * else:
     *      open inventory GUI
     */
    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            player.openGui(TestMod.instance, GuiHandler.BACKPACK, world, pos.getX(), pos.getY(), pos.getZ());
            getTileEntity(world, pos).markDirty(); //necessary?
        }
        return true;
    }

    /** When breaking the block, include the contents of the inventory. */
    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        TileEntityBackpack tile = getTileEntity(world, pos);
        IItemHandler itemHandler = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, EnumFacing.NORTH);
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            ItemStack stack = itemHandler.getStackInSlot(i);
            if (!stack.isEmpty()) {
                spawnAsEntity(world, pos, stack);
            }
        }

        this.dropBlockAsItem(world, pos, state, 0);
        //TODO: Doesn't drop main block

        super.breakBlock(world, pos, state);
    }

    //Uses custom JSON Model

    //Non-full cube
    //Apparently it's fine to override but not call if they are deprecated
    @Override
    @Deprecated
    public boolean isNormalCube(IBlockState state) {
        return false;
    }

    @Override
    @Deprecated
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    @Deprecated
    public boolean causesSuffocation(IBlockState state) {
        return false;
    }

    @Override
    @Deprecated
    public boolean isFullBlock(IBlockState state) {
        return false;
    }

    @Override
    @Deprecated
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    //Add bounding box
    //ToDo: Sidedness depending on how it is placed

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return new AxisAlignedBB(0.635D, 0.0D, 0.1D, 0.235, 1.0D, 0.9D); //x1 = width end, x2 = width start, y2= height
    }

    //VARIANT block state

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, VARIANT);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(VARIANT, BackpackVariantEnum.values()[meta]);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(VARIANT).ordinal();
    }

    //    "basic": {
//      "model": "testmod:backpack_basic"
//    },
//    "iron": {
//      "model": "testmod:backpack_iron"
//    },
//    "gold": {
//      "model": "testmod:backpack_gold"
//    },
//    "diamond": {
//      "model": "testmod:backpack_diamond"
//    },
//    "inventory": {
//      "model": "testmod:backpack_basic",
//      "transform": "forge:default-block"
//    }
//  }
//}


}
