package ganymedes01.ironchestminecarts;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.oredict.OreDictionary;

import org.apache.logging.log4j.Level;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLMissingMappingsEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.ironchest.IronChest;
import cpw.mods.ironchest.IronChestType;
import ganymedes01.ironchestminecarts.gui.GuiHandler;
import ganymedes01.ironchestminecarts.lib.Reference;
import ganymedes01.ironchestminecarts.minecarts.EntityMinecartIronChestAbstract;
import ganymedes01.ironchestminecarts.minecarts.ItemMinecartChestRenderer;
import ganymedes01.ironchestminecarts.minecarts.ItemMinecartIronChest;

@Mod(
        modid = Reference.MOD_ID,
        name = Reference.MOD_NAME,
        version = Reference.VERSION_NUMBER,
        dependencies = Reference.DEPENDENCIES)
public class IronChestMinecarts {

    @Instance(Reference.MOD_ID)
    public static IronChestMinecarts instance;

    public static Map<IronChestType, Item> carts = new HashMap<IronChestType, Item>();

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        // Configuration
        boolean renderMinecarts3D = false;
        Configuration cfg = new Configuration(event.getSuggestedConfigurationFile());
        try {
            cfg.load();
            renderMinecarts3D = cfg
                    .get(
                            Configuration.CATEGORY_GENERAL,
                            "render minecarts in 3D on inventory",
                            renderMinecarts3D,
                            "If set to true minecarts will be rendered in 3D on the player's hand and on inventories")
                    .getBoolean(renderMinecarts3D);
        } catch (Exception e) {
            FMLLog.log(Level.ERROR, e, Reference.MOD_NAME + " had a problem loading its configs!");
        } finally {
            if (cfg.hasChanged()) cfg.save();
        }

        // GUI
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());

        // Ore Dictionary
        OreDictionary.registerOre("chestEnder", Blocks.ender_chest);
        OreDictionary.registerOre("chestTrapped", Blocks.trapped_chest);
        OreDictionary.registerOre("chestWood", Blocks.chest);

        for (IronChestType type : EntityMinecartIronChestAbstract.registeredTypes()) {
            String name = type.name().toLowerCase();

            // Item registering
            Item minecart = new ItemMinecartIronChest(type)
                    .setUnlocalizedName(Reference.MOD_ID + ".minecart_chest_" + name)
                    .setTextureName(Reference.MOD_ID + ":minecart_chest_" + name.replace("teel", "ilver"));
            GameRegistry.registerItem(minecart, "minecart_chest_" + name);
            carts.put(type, minecart);

            // Recipe
            ItemStack chest = new ItemStack(IronChest.ironChestBlock, 1, type.ordinal());
            if (type == IronChestType.DIRTCHEST9000) name = "dirt";
            String ore = "chest" + name.substring(0, 1).toUpperCase() + name.substring(1);
            OreDictionary.registerOre(ore, chest);

            // Rendering
            if (renderMinecarts3D) if (event.getSide() == Side.CLIENT)
                MinecraftForgeClient.registerItemRenderer(minecart, new ItemMinecartChestRenderer());
        }
    }

    // cpw.mods.fml.common.registry.GameRegistry#registerTileEntityWithAlternatives
    @Mod.EventHandler
    public void missingMapping(FMLMissingMappingsEvent event) {
        for (FMLMissingMappingsEvent.MissingMapping mapping : event.getAll()) {
            if (mapping.type == GameRegistry.Type.BLOCK) {
                if ("ironchestminecarts:minecart_chest_silver".equals(mapping.name)) {
                    mapping.remap(GameRegistry.findBlock("ironchestminecarts", "minecart_chest_steel"));
                }
            } else if (mapping.type == GameRegistry.Type.ITEM) {
                if ("ironchestminecarts:minecart_chest_silver".equals(mapping.name)) {
                    mapping.remap(GameRegistry.findItem("ironchestminecarts", "minecart_chest_steel"));
                }
            }
        }
    }
}
