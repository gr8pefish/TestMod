package gr8pefish.testmod.core;

import gr8pefish.testmod.item.ItemStrawberry;
import gr8pefish.testmod.util.ModInfo;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber
@GameRegistry.ObjectHolder(ModInfo.MODID)
public class RegistrarTestMod {

    //Items
    @GameRegistry.ObjectHolder(ItemStrawberry.STRAWBERRY_NAME)
    public static final Item STRAWBERRY = Items.AIR;

    //Blocks

    //Custom

    //Custom Registries

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(new ItemStrawberry().setRegistryName(ItemStrawberry.STRAWBERRY_NAME));
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {

    }

    /**
     * Registers the textures/models for each item/block
     */
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {

        //Items
        ModelLoader.setCustomModelResourceLocation(STRAWBERRY, 0, new ModelResourceLocation(STRAWBERRY.getRegistryName(), "inventory"));

        //Blocks
    }

    //Custom

}
