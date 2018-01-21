package gr8pefish.testmod.block.helper;


import gr8pefish.testmod.TestMod;
import gr8pefish.testmod.util.ModInfo;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

/**
 * Helper class for all blocks
 */
public class BlockBase extends Block {

    /**
     * Sets up the material, unlocalized name, and adds it to the creative tab for the mod.
     *
     * @param material - The {@link Material} to set the block to
     * @param name - The unlocalized name
     */
    public BlockBase(Material material, String name) {
        super(material);
        setUnlocalizedName(ModInfo.MODID + "."+ name);
        setCreativeTab(TestMod.CREATIVE_TAB_TEST_MOD);
    }

}
