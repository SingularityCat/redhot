package singularitycat.redhot;

//import java.util.logging.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;

import cpw.mods.fml.common.FMLLog;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

import cpw.mods.fml.common.registry.GameRegistry;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.oredict.OreDictionary;

@Mod(modid=RedHot.modid, name=RedHot.name, version=RedHot.version)
public class RedHot {
    public static final String modid="redhot";
    public static final String name="Red Hot";
    public static final String version="0.0.7";

    public static final Random RNG = new Random(); 
    public static Configuration config;
    public static Logger logger;

    @Instance(modid)
    public static RedHot instance;

    @SidedProxy(clientSide="singularitycat.redhot.ClientProxy", serverSide="singularitycat.redhot.CommonProxy")
    public static CommonProxy proxy;
    
    /* Blocks */
    public static Block glowstoneBricks;
    public static Block impactedStone;
    
    /* Items */
    public static Item plantMass;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    	logger = LogManager.getLogger(modid);
        
        config = new Configuration(event.getSuggestedConfigurationFile());
        config.load();
        
        glowstoneBricks = new GlowstoneBricks();
        glowstoneBricks.setBlockName("glowstoneBricks");
        glowstoneBricks.setBlockTextureName(modid + ":" + "glowstoneBricks");
    	GameRegistry.registerBlock(glowstoneBricks, "glowstoneBricks");

        impactedStone = new ImpactedStone();
        impactedStone.setBlockName("impactedStone");
        impactedStone.setBlockTextureName(modid + ":" + "impactedStone");
    	GameRegistry.registerBlock(impactedStone, "impactedStone");
    	
        plantMass = new PlantMass();
        plantMass.setUnlocalizedName("plantMass");
        plantMass.setTextureName(modid + ":" + "plantMass");
    	GameRegistry.registerItem(plantMass, "plantMass");
    	
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
            'G', new ItemStack(Blocks.glowstone)
        );

        GameRegistry.addShapedRecipe(
            new ItemStack(Blocks.glowstone, 1),
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
            'C', new ItemStack(Blocks.cobblestone)
        );

        GameRegistry.addShapedRecipe(
            new ItemStack(Blocks.cobblestone, 4),
            "II",
            "II",
            'I', new ItemStack(impactedStone)
        );
    }

    public void initPlantMass()
    {
    	ItemStack plantMassStack = new ItemStack(plantMass);
    	
    	ItemStack biomass[] = {
    		new ItemStack(Blocks.waterlily, 1, OreDictionary.WILDCARD_VALUE),
    		new ItemStack(Blocks.leaves, 1, OreDictionary.WILDCARD_VALUE),
    		new ItemStack(Blocks.sapling, 1, OreDictionary.WILDCARD_VALUE),
    		new ItemStack(Blocks.vine, 1, OreDictionary.WILDCARD_VALUE),
    		new ItemStack(Items.melon_seeds, 1, OreDictionary.WILDCARD_VALUE),
    		new ItemStack(Items.pumpkin_seeds, 1, OreDictionary.WILDCARD_VALUE),
    		new ItemStack(Items.wheat_seeds, 1, OreDictionary.WILDCARD_VALUE)
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
        	new ItemStack(Blocks.dirt, 4),
        	"PP",
        	"PP",
        	'P', plantMassStack
        );
    }
    
    public void initVanillaRecipes()
    {
        ItemStack coalBlockStack = new ItemStack(Blocks.coal_block, 1);
        ItemStack leatherStack = new ItemStack(Items.leather, 1);
        ItemStack ingotIronStack = new ItemStack(Items.iron_ingot, 1);
        ItemStack ingotGoldStack = new ItemStack(Items.gold_ingot, 1);
        ItemStack diamondStack = new ItemStack(Items.diamond, 1);
        ItemStack clothStack = new ItemStack(Blocks.wool, 1, OreDictionary.WILDCARD_VALUE);

        /* Redstone / Glowstone recipes */
        logger.info("Adding redstone recipe");
        GameRegistry.addShapelessRecipe(
                new ItemStack(Items.redstone, 2),        /* Output */
                new ItemStack(Items.glowstone_dust, 1),
                new ItemStack(Items.dye, 1, 1)
            );
        logger.info("Adding glowstone recipe");     
        GameRegistry.addShapelessRecipe(
                 new ItemStack(Items.glowstone_dust, 2),      /* Output */
                 new ItemStack(Items.redstone, 1),
                 new ItemStack(Items.dye, 1, 11)
            );

        /* Lava bucket recipes */
        logger.info("Adding lava bucket recipes");      
        GameRegistry.addShapelessRecipe(
                new ItemStack(Items.lava_bucket, 1),      /* Output */
                new ItemStack(Items.bucket, 1),
                new ItemStack(Blocks.cobblestone, 1),
                new ItemStack(Items.redstone, 1),
                new ItemStack(Items.redstone, 1)
            );

        GameRegistry.addShapelessRecipe(
                new ItemStack(Items.lava_bucket, 1),      /* Output */
                new ItemStack(Items.bucket, 1),
                new ItemStack(Blocks.stone, 1),
                new ItemStack(Items.redstone, 1),
                new ItemStack(Items.redstone, 1)
            );

        /* Obsidian recipe (negates need for diamond pickaxe!) */
        logger.info("Adding obsidian recipe");
        GameRegistry.addShapelessRecipe(
            new ItemStack(Blocks.obsidian, 1),           /* Output */
            new ItemStack(Items.lava_bucket.setContainerItem(Items.bucket), 1),
            new ItemStack(Items.water_bucket.setContainerItem(Items.bucket), 1)
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
        	new ItemStack(Items.saddle, 1),
            "LLL",
            "LIL",
            "I I",
            'L', leatherStack,
            'I', ingotIronStack
        );

        /* Horse armour recipes */
        logger.info("Adding iron horse armour recipe");
        GameRegistry.addRecipe(
        	new ItemStack(Items.iron_horse_armor, 1),
            "I  ",
            "ICI",
            "III",
            'C', clothStack,
            'I', ingotIronStack
        );

        logger.info("Adding gold horse armour recipe");
        GameRegistry.addRecipe(
        	new ItemStack(Items.golden_horse_armor, 1),
            "G  ",
            "GCG",
            "GGG",
            'C', clothStack,
            'G', ingotGoldStack
        );

        logger.info("Adding diamond horse armour recipe");
        GameRegistry.addRecipe(
        	new ItemStack(Items.diamond_horse_armor, 1),
            "D  ",
            "DCD",
            "DDD",
            'C', clothStack,
            'D', diamondStack
        );

        if(config.get("recipes", "RottenFleshToLeather", true).getBoolean(true))
        {
        	logger.info("Adding rotten flesh -> leather smelting recipe");
        	GameRegistry.addSmelting(Items.rotten_flesh, leatherStack, 0.2f);
        }
    }
}
