package gr8pefish.testmod.block.helper;


import gr8pefish.testmod.TestMod;
import gr8pefish.testmod.util.ModInfo;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class BlockBase extends Block {

    public BlockBase(Material material, String name) {
        super(material);
        setUnlocalizedName(ModInfo.MODID + "."+ name);
        setCreativeTab(TestMod.CREATIVE_TAB_TEST_MOD);
    }

}
