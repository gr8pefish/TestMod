package gr8pefish.testmod.proxy;

import gr8pefish.testmod.TestMod;
import gr8pefish.testmod.network.GuiHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class CommonProxy {


    public void preInit(FMLPreInitializationEvent event) {
        //No-op
    }


    public void init(FMLInitializationEvent event) {
        //Register the GuiHandler to allow for containers/guis to work properly
        NetworkRegistry.INSTANCE.registerGuiHandler(TestMod.instance, new GuiHandler());
    }


    public void postInit(FMLPostInitializationEvent event) {
        //No-op
    }

}
