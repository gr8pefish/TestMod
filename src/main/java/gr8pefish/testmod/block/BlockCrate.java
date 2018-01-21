package gr8pefish.testmod.block;

import gr8pefish.testmod.TestMod;
import gr8pefish.testmod.util.ModInfo;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;

/**
 * Basic block class
 *
 * Has a linked TileEntity here: {@link gr8pefish.testmod.tile.TileEntityCrate}
 */
public class BlockCrate extends Block {

    /** Field for use in unlocalized and registry naming*/
    public static final String CRATE_NAME = "crate";

    /**
     * Constructor
     *
     * Sets material type, unlocalized name, and creative tab
     */
    public BlockCrate() {
        super(Material.ROCK);
        setUnlocalizedName(ModInfo.MODID + "."+ CRATE_NAME);
        setCreativeTab(TestMod.CREATIVE_TAB_TEST_MOD);
    }

    /** {@link gr8pefish.testmod.tile.TileEntityCrate} */
    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

}
