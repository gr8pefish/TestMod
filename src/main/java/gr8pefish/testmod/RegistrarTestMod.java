package gr8pefish.testmod;

import gr8pefish.testmod.api.BackpackVariantEnum;
import gr8pefish.testmod.block.backpack.BlockBackpack;
import gr8pefish.testmod.block.backpack.TileEntityBackpack;
import gr8pefish.testmod.block.crate.BlockCrate;
import gr8pefish.testmod.item.ItemBackpack;
import gr8pefish.testmod.item.ItemStrawberry;
import gr8pefish.testmod.block.crate.TileEntityCrate;
import gr8pefish.testmod.util.ModInfo;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Items;
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
    @GameRegistry.ObjectHolder(ItemBackpack.BACKPACK_NAME)
    public static final Item BACKPACK_ITEM = Items.AIR;

    //Blocks
    @GameRegistry.ObjectHolder(BlockCrate.CRATE_NAME)
    public static final Block CRATE = new BlockCrate();
    @GameRegistry.ObjectHolder(BlockBackpack.BACKPACK_NAME+"basic")
    public static final Block BACKPACK_BLOCK = new BlockBackpack(BackpackVariantEnum.BASIC);


    /**
     * Registers the Items (and ItemBlocks)
     */
    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        //Items
        event.getRegistry().register(STRAWBERRY.setRegistryName(ItemStrawberry.STRAWBERRY_NAME));
        event.getRegistry().register(new ItemBackpack(BackpackVariantEnum.BASIC).setRegistryName(ItemBackpack.BACKPACK_NAME));
        //ItemBlocks
        event.getRegistry().register(new ItemBlock(CRATE).setRegistryName(CRATE.getRegistryName()));
        //Backpack ItemBlock
        event.getRegistry().register(new ItemBlock(BACKPACK_BLOCK).setRegistryName(BACKPACK_BLOCK.getRegistryName())); //Remove for itemblock
    }

    /**
     * Registers the Blocks (and TileEntities)
     *
     */
    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        //Blocks
        event.getRegistry().register(CRATE.setRegistryName(BlockCrate.CRATE_NAME));
        event.getRegistry().register(BACKPACK_BLOCK.setRegistryName(BlockBackpack.BACKPACK_NAME));
        //Set TEs
        GameRegistry.registerTileEntity(TileEntityCrate.class, CRATE.getRegistryName().toString()); //Can't use CRATE.getTileEntityClass(), as it is Block type, not BlockCrate
        GameRegistry.registerTileEntity(TileEntityBackpack.class, BACKPACK_BLOCK.getRegistryName().toString());
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

        ModelLoader.setCustomModelResourceLocation(BACKPACK_ITEM, 0, new ModelResourceLocation(BACKPACK_ITEM.getRegistryName(), "inventory"));

        //Blocks

        //Uses assets/testmod/blockstates/crate.json to point to testmod/models/block/crate.json to reference testmod/textures/blocks/crate_face.png
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(CRATE), 0, new ModelResourceLocation(CRATE.getRegistryName(), "inventory"));

        //somehow do so without itemblock
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(BACKPACK_BLOCK), 0, new ModelResourceLocation(BACKPACK_BLOCK.getRegistryName(), "inventory"));

    }


}
