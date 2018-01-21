package gr8pefish.testmod.block.helper;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;

/**
 * Abstract class to bind a block to a TileEntity
 * Also extends {@link BlockBase} to allow easy construction of the block.
 *
 * @param <TE> - {@link TileEntity}
 */
public abstract class BlockTileEntity<TE extends TileEntity> extends BlockBase {

    /** Constructor that simply passes data to the BlockBase class to initialize data */
    public BlockTileEntity(Material material, String name) {
        super(material, name);
    }

    /** Get the TileEntity's Class */
    public abstract Class<TE> getTileEntityClass();

    /** Get the specific TileEntity at a given location */
    public TE getTileEntity(IBlockAccess world, BlockPos pos) {
        return (TE)world.getTileEntity(pos);
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    /** Create a TileEntity with the given data */
    @Nullable
    @Override
    public abstract TE createTileEntity(World world, IBlockState state);

}
