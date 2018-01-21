package gr8pefish.testmod;

import gr8pefish.testmod.proxy.CommonProxy;
import gr8pefish.testmod.util.ModInfo;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = ModInfo.MODID, name = ModInfo.MOD_NAME, version = ModInfo.VERSION)
public class TestMod {

    //Logger
    public static final Logger LOGGER = LogManager.getLogger(ModInfo.MOD_NAME);

    //Creative Tab
    public static final CreativeTabs CREATIVE_TAB_TEST_MOD = new CreativeTabs(ModInfo.MODID) {
        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(RegistrarTestMod.STRAWBERRY);
        }
    };

    //Proxies
    @SidedProxy(clientSide = ModInfo.CLIENT_PROXY, serverSide = ModInfo.COMMON_PROXY)
    public static CommonProxy proxy;

    //Mod Instance
    @Mod.Instance
    public static TestMod instance;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }

}
