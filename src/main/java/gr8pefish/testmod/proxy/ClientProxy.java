package gr8pefish.testmod.proxy;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {


    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event); //important

        //No-op
    }


    public void init(FMLInitializationEvent event) {
        super.init(event); //important

        //No-op
    }


    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event); //important

        //No-op
    }

}
