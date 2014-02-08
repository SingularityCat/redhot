package singularitycat.redhot;

import java.util.logging.Logger;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import cpw.mods.fml.common.FMLLog;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

import cpw.mods.fml.common.registry.GameRegistry;

import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;

@Mod(modid=RedHot.modid, name=RedHot.name, version=RedHot.version)
@NetworkMod(clientSideRequired=true, serverSideRequired=false)
public class RedHot {
    public static final String modid="redhot";
    public static final String name="Red Hot";
    public static final String version="0.0.6";

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
        logger = Logger.getLogger(modid);
        logger.setParent(FMLLog.getLogger());
        
        config = new Configuration(event.getSuggestedConfigurationFile());
        config.load();
        
        glowstoneBricks = new GlowstoneBricks(config.getBlock("glowstoneBricks", 500).getInt());
        impactedStone = new ImpactedStone(config.getBlock("impactedStone", 501).getInt());
    
        plantMass = new PlantMass(config.getItem("plantMass", 5000).getInt());
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
    	MinecraftForge.setBlockHarvestLevel(glowstoneBricks, "pickaxe", 0);
    	GameRegistry.registerBlock(glowstoneBricks, "redhot_glowstoneBricks");

    	logger.info("Adding glowstone bricks <-> glowstone recipes");
        GameRegistry.addShapedRecipe(
            new ItemStack(glowstoneBricks, 16),
            "GG",
            "GG",
            'G', new ItemStack(Block.glowStone)
        );

        GameRegistry.addShapedRecipe(
            new ItemStack(Block.glowStone, 1),
            "GG",
            "GG",
            'G', new ItemStack(glowstoneBricks)
        );
    }
    
    public void initImpactedStone()
    {    	
    	MinecraftForge.setBlockHarvestLevel(impactedStone, "pickaxe", 0);
    	GameRegistry.registerBlock(impactedStone, "redhot_impactedStone");

    	logger.info("Adding impacted stone <-> cobblestone recipes");
        GameRegistry.addShapedRecipe(
            new ItemStack(impactedStone, 4),
            "CC",
            "CC",
            'C', new ItemStack(Block.cobblestone)
        );

        GameRegistry.addShapedRecipe(
            new ItemStack(Block.cobblestone, 4),
            "II",
            "II",
            'I', new ItemStack(impactedStone)
        );
    }

    public void initPlantMass()
    {
    	ItemStack plantMassStack = new ItemStack(plantMass);
    	
    	ItemStack biomass[] = {
    		new ItemStack(Block.waterlily, 1, OreDictionary.WILDCARD_VALUE),
    		new ItemStack(Block.leaves, 1, OreDictionary.WILDCARD_VALUE),
    		new ItemStack(Block.sapling, 1, OreDictionary.WILDCARD_VALUE),
    		new ItemStack(Block.vine, 1, OreDictionary.WILDCARD_VALUE),
    		new ItemStack(Item.melonSeeds, 1, OreDictionary.WILDCARD_VALUE),
    		new ItemStack(Item.pumpkinSeeds, 1, OreDictionary.WILDCARD_VALUE),
    		new ItemStack(Item.seeds, 1, OreDictionary.WILDCARD_VALUE)
    	};
    	
    	GameRegistry.registerItem(plantMass, "redhot_plantMass");
    	
    	logger.info("Registering plant mass fuel handler");
        GameRegistry.registerFuelHandler(new RedHotFuelHandler());
        
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
        	new ItemStack(Block.dirt, 4),
        	"PP",
        	"PP",
        	'P', plantMassStack
        );
    }
    
    public void initVanillaRecipes()
    {
        ItemStack coalBlockStack = new ItemStack(Block.coalBlock, 1);
        ItemStack leatherStack = new ItemStack(Item.leather, 1);
        ItemStack ingotIronStack = new ItemStack(Item.ingotIron, 1);
        ItemStack ingotGoldStack = new ItemStack(Item.ingotGold, 1);
        ItemStack diamondStack = new ItemStack(Item.diamond, 1);
        ItemStack clothStack = new ItemStack(Block.cloth, 1, OreDictionary.WILDCARD_VALUE);


        /* Redstone / Glowstone recipes */
        logger.info("Adding redstone recipe");
        GameRegistry.addShapelessRecipe(
                new ItemStack(Item.redstone, 2),        /* Output */
                new ItemStack(Item.glowstone, 1),
                new ItemStack(Item.dyePowder, 1, 1)
            );
        logger.info("Adding glowstone recipe");     
        GameRegistry.addShapelessRecipe(
                 new ItemStack(Item.glowstone, 2),      /* Output */
                 new ItemStack(Item.redstone, 1),
                 new ItemStack(Item.dyePowder, 1, 11)
            );

        /* Lava bucket recipes */
        logger.info("Adding lava bucket recipes");      
        GameRegistry.addShapelessRecipe(
                new ItemStack(Item.bucketLava, 1),      /* Output */
                new ItemStack(Item.bucketEmpty, 1),
                new ItemStack(Block.cobblestone, 1),
                new ItemStack(Item.redstone, 1),
                new ItemStack(Item.redstone, 1)
            );

        GameRegistry.addShapelessRecipe(
                new ItemStack(Item.bucketLava, 1),      /* Output */
                new ItemStack(Item.bucketEmpty, 1),
                new ItemStack(Block.stone, 1),
                new ItemStack(Item.redstone, 1),
                new ItemStack(Item.redstone, 1)
            );

        /* Obsidian recipe (negates need for diamond pickaxe!) */
        logger.info("Adding obsidian recipe");
        GameRegistry.addShapelessRecipe(
            new ItemStack(Block.obsidian, 1),           /* Output */
            new ItemStack(Item.bucketLava.setContainerItem(Item.bucketEmpty), 1),
            new ItemStack(Item.bucketWater.setContainerItem(Item.bucketEmpty), 1)
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
        	new ItemStack(Item.saddle, 1),
            "LLL",
            "LIL",
            "I I",
            'L', leatherStack,
            'I', ingotIronStack
        );

        /* Horse armour recipes */
        logger.info("Adding iron horse armour recipe");
        GameRegistry.addRecipe(
        	new ItemStack(Item.horseArmorIron, 1),
            "I  ",
            "ICI",
            "III",
            'C', clothStack,
            'I', ingotIronStack
        );

        logger.info("Adding gold horse armour recipe");
        GameRegistry.addRecipe(
        	new ItemStack(Item.horseArmorGold, 1),
            "G  ",
            "GCG",
            "GGG",
            'C', clothStack,
            'G', ingotGoldStack
        );

        logger.info("Adding diamond horse armour recipe");
        GameRegistry.addRecipe(
        	new ItemStack(Item.horseArmorDiamond, 1),
            "D  ",
            "DCD",
            "DDD",
            'C', clothStack,
            'D', diamondStack
        );

        if(config.get("recipes", "RottenFleshToLeather", true).getBoolean(true))
        {
        	logger.info("Adding rotten flesh -> leather smelting recipe");
        	GameRegistry.addSmelting(Item.rottenFlesh.itemID, leatherStack, 0.2f);
        }
    }
}
