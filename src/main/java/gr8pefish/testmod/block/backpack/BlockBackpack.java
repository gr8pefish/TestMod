package gr8pefish.testmod.block.backpack;

import gr8pefish.testmod.RegistrarTestMod;
import gr8pefish.testmod.TestMod;
import gr8pefish.testmod.api.BackpackVariantEnum;
import gr8pefish.testmod.block.crate.TileEntityCrate;
import gr8pefish.testmod.block.helper.BlockTileEntity;
import gr8pefish.testmod.network.GuiHandler;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;
import java.util.Random;

/**
 * Block with a TileEntity
 *
 * {@link TileEntityCrate}
 */
public class BlockBackpack extends BlockTileEntity<TileEntityBackpack> {

    /** Field for use in unlocalized and registry naming*/
    public static final String BACKPACK_NAME = "backpack_block";

    public static final IProperty<BackpackVariantEnum> BACKPACK_VARIANT = PropertyEnum.create("backpack_variant", BackpackVariantEnum.class);
    public static final PropertyDirection FACING = BlockHorizontal.FACING;

    private BackpackVariantEnum variant;

    /** Constructor */
    public BlockBackpack(BackpackVariantEnum variant) {
        super(Material.ROCK, BACKPACK_NAME+"_"+variant.toString().toLowerCase());
        this.variant = variant;
        setDefaultState(this.blockState.getBaseState().withProperty(BACKPACK_VARIANT, variant).withProperty(FACING, EnumFacing.NORTH)); //Default facing?
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
        switch (state.getValue(FACING)) {
            case NORTH: //working
                return new AxisAlignedBB(0.9D, 0.0D, 0.235D, 0.1, 1.0D, 0.635D); //x = length, y = height, z = width
            case EAST: //working
                return new AxisAlignedBB(0.36D, 0.0D, 0.1D, 0.76, 1.0D, 0.9D); //x = width, y = height, z = length
            case SOUTH:
                return new AxisAlignedBB(0.9D, 0.0D, 0.76D, 0.1, 1.0D, 0.36D); //x = length, y = height, z = width
            case WEST: //working
                return new AxisAlignedBB(0.635D, 0.0D, 0.1D, 0.235, 1.0D, 0.9D); //x = width, y = height, z = length
        }

        //North default (should be unreachable)
        return new AxisAlignedBB(0.635D, 0.0D, 0.1D, 0.235, 1.0D, 0.9D); //x1 = width end, x2 = width start, y2= height

    }

    //VARIANT block state

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, BACKPACK_VARIANT, FACING);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        BackpackVariantEnum backpack_variant = BackpackVariantEnum.values()[meta & 0b11]; //first 2 bits for backpack_variant, 4 possibilities
        EnumFacing facing = EnumFacing.Plane.HORIZONTAL.facings()[(meta >> 2) & 0b11]; //last 2 bits for facing, 4 possibilities
        return getDefaultState().withProperty(FACING, facing).withProperty(BACKPACK_VARIANT, backpack_variant);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        int backpack_variant = state.getValue(BACKPACK_VARIANT).ordinal(); //0-3
        int facing = state.getValue(FACING).ordinal() - EnumFacing.Plane.VERTICAL.facings().length; //0-3, ignore Up and Down
        return (facing << 2) | backpack_variant; //first 2 bits for backpack_variant, last 2 bits for facing
    }


    //

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return RegistrarTestMod.BACKPACK_ITEM; //drop item, not block
    }


    //stolen from vanilla furnace
    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
        this.setDefaultFacing(worldIn, pos, state);
    }

    //stolen from vanilla furnace
    private void setDefaultFacing(World worldIn, BlockPos pos, IBlockState state)
    {
        if (!worldIn.isRemote)
        {
            IBlockState iblockstate = worldIn.getBlockState(pos.north());
            IBlockState iblockstate1 = worldIn.getBlockState(pos.south());
            IBlockState iblockstate2 = worldIn.getBlockState(pos.west());
            IBlockState iblockstate3 = worldIn.getBlockState(pos.east());
            EnumFacing enumfacing = (EnumFacing)state.getValue(FACING);

            if (enumfacing == EnumFacing.NORTH && iblockstate.isFullBlock() && !iblockstate1.isFullBlock())
            {
                enumfacing = EnumFacing.SOUTH;
            }
            else if (enumfacing == EnumFacing.SOUTH && iblockstate1.isFullBlock() && !iblockstate.isFullBlock())
            {
                enumfacing = EnumFacing.NORTH;
            }
            else if (enumfacing == EnumFacing.WEST && iblockstate2.isFullBlock() && !iblockstate3.isFullBlock())
            {
                enumfacing = EnumFacing.EAST;
            }
            else if (enumfacing == EnumFacing.EAST && iblockstate3.isFullBlock() && !iblockstate2.isFullBlock())
            {
                enumfacing = EnumFacing.WEST;
            }

            worldIn.setBlockState(pos, state.withProperty(FACING, enumfacing), 2);
        }
    }

    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }

    /**
     * Called by ItemBlocks after a block is set in the world, to allow post-place logic
     */
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        worldIn.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);

    }

}
