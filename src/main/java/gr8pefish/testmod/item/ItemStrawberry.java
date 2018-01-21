package gr8pefish.testmod.item;

import gr8pefish.testmod.TestMod;
import gr8pefish.testmod.api.IFood;
import gr8pefish.testmod.util.ModInfo;
import net.minecraft.item.Item;

/**
 * Basic food item
 */
public class ItemStrawberry extends Item implements IFood {

    /** Field for use in unlocalized and registry naming*/
    public static final String STRAWBERRY_NAME = "strawberry";

    /**
     * Constructor
     *
     * Sets unlocalized name and creative tab
     */
    public ItemStrawberry() {
        setUnlocalizedName(ModInfo.MODID + "."+ STRAWBERRY_NAME);
        setCreativeTab(TestMod.CREATIVE_TAB_TEST_MOD);
    }

}
