package singularitycat.redhot;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.oredict.OreDictionary;

@Mod(modid=RedHot.modid, name=RedHot.name, version=RedHot.version)
public class RedHot {
    public static final String modid="redhot";
    public static final String name="Red Hot";
    public static final String version="0.0.8";

    public static final Random RNG = new Random(); 
    public static Configuration config;
    public static Logger logger;

    @Instance(modid)
    public static RedHot instance;

    /* Blocks */
    public static Block glowstoneBricks;
    public static Block impactedStone;
    
    /* ItemBlocks */
    public static ItemBlock glowstoneBricksItem;
    public static ItemBlock impactedStoneItem;
    
    /* Items */
    public static Item plantMass;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = LogManager.getLogger(modid);
        
        config = new Configuration(event.getSuggestedConfigurationFile());
        config.load();
        
        glowstoneBricks = new GlowstoneBricks();
        glowstoneBricks.setRegistryName(modid, GlowstoneBricks.name);
        glowstoneBricksItem = new ItemBlock(glowstoneBricks);
        glowstoneBricksItem.setRegistryName(glowstoneBricks.getRegistryName());
        GameRegistry.register(glowstoneBricks);
        GameRegistry.register(glowstoneBricksItem);

        impactedStone = new ImpactedStone();
        impactedStone.setRegistryName(modid, ImpactedStone.name);
        impactedStoneItem = new ItemBlock(impactedStone);
        impactedStoneItem.setRegistryName(impactedStone.getRegistryName());
        GameRegistry.register(impactedStone);
        GameRegistry.register(impactedStoneItem);
        
        plantMass = new PlantMass();
        plantMass.setRegistryName(modid, PlantMass.name);
        GameRegistry.register(plantMass);
        
        logger.info("Registering plant mass fuel handler");
        GameRegistry.registerFuelHandler(new RedHotFuelHandler());
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        initGlowstoneBricks();
        initImpactedStone();
        initPlantMass();
        initVanillaRecipes();
        
        /* Register client side models. */
        if(event.getSide() == Side.CLIENT) {
            RenderItem ritem;
            ritem = Minecraft.getMinecraft().getRenderItem();
            ritem.getItemModelMesher().register(plantMass, 0, new ModelResourceLocation(modid + ":" + PlantMass.name, "inventory"));
            ritem = Minecraft.getMinecraft().getRenderItem();
            ritem.getItemModelMesher().register(Item.getItemFromBlock(glowstoneBricks), 0, new ModelResourceLocation(modid + ":" + GlowstoneBricks.name, "inventory"));
            ritem = Minecraft.getMinecraft().getRenderItem();
            ritem.getItemModelMesher().register(Item.getItemFromBlock(impactedStone), 0, new ModelResourceLocation(modid + ":" + ImpactedStone.name, "inventory"));
        }
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
        config.save();
    }

    public void initGlowstoneBricks()
    {
        logger.info("Adding glowstone bricks <-> glowstone recipes");
        GameRegistry.addShapedRecipe(
            new ItemStack(glowstoneBricks, 16),
            "GG",
            "GG",
            'G', new ItemStack(Blocks.GLOWSTONE)
        );

        GameRegistry.addShapedRecipe(
            new ItemStack(Blocks.GLOWSTONE, 1),
            "GG",
            "GG",
            'G', new ItemStack(glowstoneBricks)
        );
    }
    
    public void initImpactedStone()
    {
        logger.info("Adding impacted stone <-> cobblestone recipes");
        GameRegistry.addShapedRecipe(
            new ItemStack(impactedStone, 4),
            "CC",
            "CC",
            'C', new ItemStack(Blocks.COBBLESTONE)
        );

        GameRegistry.addShapedRecipe(
            new ItemStack(Blocks.COBBLESTONE, 4),
            "II",
            "II",
            'I', new ItemStack(impactedStone)
        );
    }

    public void initPlantMass()
    {
        ItemStack plantMassStack = new ItemStack(plantMass);
        
        ItemStack biomass[] = {
            new ItemStack(Blocks.WATERLILY, 1, OreDictionary.WILDCARD_VALUE),
            new ItemStack(Blocks.LEAVES, 1, OreDictionary.WILDCARD_VALUE),
            new ItemStack(Blocks.SAPLING, 1, OreDictionary.WILDCARD_VALUE),
            new ItemStack(Blocks.VINE, 1, OreDictionary.WILDCARD_VALUE),
            new ItemStack(Items.MELON_SEEDS, 1, OreDictionary.WILDCARD_VALUE),
            new ItemStack(Items.PUMPKIN_SEEDS, 1, OreDictionary.WILDCARD_VALUE),
            new ItemStack(Items.WHEAT_SEEDS, 1, OreDictionary.WILDCARD_VALUE)
        };
        
        logger.info("Adding plant mass recipes.");
        for(ItemStack i : biomass)
        {
            for(ItemStack j : biomass)
            {
                GameRegistry.addShapelessRecipe(plantMassStack, i, j);
            }
        }
        
        logger.info("Adding plant mass -> dirt recipe");
        GameRegistry.addShapedRecipe(
            new ItemStack(Blocks.DIRT, 4),
            "PP",
            "PP",
            'P', plantMassStack
        );
    }
    
    public void initVanillaRecipes()
    {
        ItemStack coalBlockStack = new ItemStack(Blocks.COAL_BLOCK, 1);
        ItemStack leatherStack = new ItemStack(Items.LEATHER, 1);
        ItemStack ingotIronStack = new ItemStack(Items.IRON_INGOT, 1);
        ItemStack ingotGoldStack = new ItemStack(Items.GOLD_INGOT, 1);
        ItemStack diamondStack = new ItemStack(Items.DIAMOND, 1);
        ItemStack clothStack = new ItemStack(Blocks.WOOL, 1, OreDictionary.WILDCARD_VALUE);

        /* Redstone / Glowstone recipes */
        logger.info("Adding redstone recipe");
        GameRegistry.addShapelessRecipe(
                new ItemStack(Items.REDSTONE, 2),        /* Output */
                new ItemStack(Items.GLOWSTONE_DUST, 1),
                new ItemStack(Items.DYE, 1, 1)
            );
        logger.info("Adding glowstone recipe");     
        GameRegistry.addShapelessRecipe(
                 new ItemStack(Items.GLOWSTONE_DUST, 2),      /* Output */
                 new ItemStack(Items.REDSTONE, 1),
                 new ItemStack(Items.DYE, 1, 11)
            );

        /* Lava bucket recipes */
        logger.info("Adding lava bucket recipes");      
        GameRegistry.addShapelessRecipe(
                new ItemStack(Items.LAVA_BUCKET, 1),      /* Output */
                new ItemStack(Items.BUCKET, 1),
                new ItemStack(Blocks.COBBLESTONE, 1),
                new ItemStack(Items.REDSTONE, 1),
                new ItemStack(Items.REDSTONE, 1)
            );

        GameRegistry.addShapelessRecipe(
                new ItemStack(Items.LAVA_BUCKET, 1),      /* Output */
                new ItemStack(Items.BUCKET, 1),
                new ItemStack(Blocks.STONE, 1),
                new ItemStack(Items.REDSTONE, 1),
                new ItemStack(Items.REDSTONE, 1)
            );

        /* Obsidian recipe (negates need for diamond pickaxe!) */
        logger.info("Adding obsidian recipe");
        GameRegistry.addShapelessRecipe(
            new ItemStack(Blocks.OBSIDIAN, 1),           /* Output */
            new ItemStack(Items.LAVA_BUCKET.setContainerItem(Items.BUCKET), 1),
            new ItemStack(Items.WATER_BUCKET.setContainerItem(Items.BUCKET), 1)
            );

        /* Diamond recipe */
        logger.info("Adding coal blocks -> diamond recipe");
        GameRegistry.addShapelessRecipe(
            diamondStack,
            coalBlockStack, coalBlockStack, coalBlockStack, 
            coalBlockStack, coalBlockStack, coalBlockStack, 
            coalBlockStack, coalBlockStack, coalBlockStack
        );

        /* Saddle recipe */
        logger.info("Adding saddle recipe");
        GameRegistry.addRecipe(
            new ItemStack(Items.SADDLE, 1),
            "LLL",
            "LIL",
            "I I",
            'L', leatherStack,
            'I', ingotIronStack
        );

        /* Horse armour recipes */
        logger.info("Adding iron horse armour recipe");
        GameRegistry.addRecipe(
            new ItemStack(Items.IRON_HORSE_ARMOR, 1),
            "I  ",
            "ICI",
            "III",
            'C', clothStack,
            'I', ingotIronStack
        );

        logger.info("Adding gold horse armour recipe");
        GameRegistry.addRecipe(
            new ItemStack(Items.GOLDEN_HORSE_ARMOR, 1),
            "G  ",
            "GCG",
            "GGG",
            'C', clothStack,
            'G', ingotGoldStack
        );

        logger.info("Adding diamond horse armour recipe");
        GameRegistry.addRecipe(
            new ItemStack(Items.DIAMOND_HORSE_ARMOR, 1),
            "D  ",
            "DCD",
            "DDD",
            'C', clothStack,
            'D', diamondStack
        );

        if(config.get("recipes", "RottenFleshToLeather", true).getBoolean(true))
        {
            logger.info("Adding rotten flesh -> leather smelting recipe");
            GameRegistry.addSmelting(Items.ROTTEN_FLESH, leatherStack, 0.2f);
        }
    }
}
