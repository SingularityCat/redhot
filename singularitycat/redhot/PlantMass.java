package singularitycat.redhot;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemFood;

import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;


public class PlantMass extends ItemFood {

    public static final String name = "plantMass";
    
    private static final Potion nausea = Potion.REGISTRY.getObject(new ResourceLocation("nausea"));
    private static final Potion poison = Potion.REGISTRY.getObject(new ResourceLocation("poison"));
    
    public PlantMass() {
        super(2, 0.5f, true);
        setMaxStackSize(64);
        setCreativeTab(CreativeTabs.MISC);
        setUnlocalizedName(name);
    }
    
    public void onFoodEaten(ItemStack itemStack, World world, EntityPlayer player)
    {
        float irritation = RedHot.RNG.nextFloat();
        float toxicity = RedHot.RNG.nextFloat();
        
        /* Irritation causes nausea, Toxicity causes poison damage. */
        
        /* 40% overall chance */
        if(irritation > 0.6)
        {
            if(irritation < 0.9)
            {
                /* 8 seconds, 30% chance */
                player.addPotionEffect(new PotionEffect(nausea, 160, 0, false, false));
            }
            else
            {
                /* 16 seconds, 10% chance */
                player.addPotionEffect(new PotionEffect(nausea, 320, 0, false, false));
            }
        }
        
        /* 20% overall chance */
        if(toxicity > 0.8)
        {
            
            if(toxicity < 0.9)
            {
                /* Level 1 for 4 seconds, 10% */
                player.addPotionEffect(new PotionEffect(poison, 80, 0, false, false));
            }
            else if(toxicity < 0.95)
            {
                /* Level 2 for 4 seconds, 5% */
                player.addPotionEffect(new PotionEffect(poison, 80, 1, false, false));
            }
            else
            {
                /* Level 3 for 4 seconds, 5% */
                player.addPotionEffect(new PotionEffect(poison, 80, 2, false, false));
            }
        }
    }
}
