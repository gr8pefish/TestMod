package gr8pefish.testmod;

import gr8pefish.testmod.block.crate.BlockCrate;
import gr8pefish.testmod.block.helper.BlockTileEntity;
import gr8pefish.testmod.item.ItemStrawberry;
import gr8pefish.testmod.block.crate.TileEntityCrate;
import gr8pefish.testmod.util.ModInfo;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Registry for all the items and blocks, and their corresponding models.
 */
@Mod.EventBusSubscriber
@GameRegistry.ObjectHolder(ModInfo.MODID) //ObjectHolder annotations will be prepended with modid
public class RegistrarTestMod {

    //Items
    @GameRegistry.ObjectHolder(ItemStrawberry.STRAWBERRY_NAME) //Static final instance populated through the registry event, with this name (prepended by modid)
    public static final Item STRAWBERRY = new ItemStrawberry(); //Instantiate

    //Blocks
    @GameRegistry.ObjectHolder(BlockCrate.CRATE_NAME)
    public static final Block CRATE = new BlockCrate();


    /**
     * Registers the Items (and ItemBlocks)
     */
    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        //Items
        event.getRegistry().register(STRAWBERRY.setRegistryName(ItemStrawberry.STRAWBERRY_NAME));
        //ItemBlocks
        event.getRegistry().register(new ItemBlock(CRATE).setRegistryName(CRATE.getRegistryName()));
    }

    /**
     * Registers the Blocks (and TileEntities)
     *
     */
    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        //Blocks
        event.getRegistry().register(CRATE.setRegistryName(BlockCrate.CRATE_NAME));
        //Set TEs
        GameRegistry.registerTileEntity(TileEntityCrate.class, CRATE.getRegistryName().toString()); //Can't use CRATE.getTileEntityClass(), as it is Block type, not BlockCrate
    }

    /**
     * Registers the textures/models for each item/block
     */
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {

        //Items

        //Uses assets/testmod/models/item/strawberry.json to reference testmod/items/strawberry.png
        ModelLoader.setCustomModelResourceLocation(STRAWBERRY, 0, new ModelResourceLocation(STRAWBERRY.getRegistryName(), "inventory"));

        //Blocks

        //Uses assets/testmod/blockstates/crate.json to point to testmod/models/block/crate.json to reference testmod/textures/blocks/crate_face.png
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(CRATE), 0, new ModelResourceLocation(CRATE.getRegistryName(), "inventory"));

    }


}
