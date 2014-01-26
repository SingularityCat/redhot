package singularitycat.redhot;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemFood;

import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class PlantMass extends ItemFood {

	public PlantMass(int id) {
		super(id, 2, 0.5f, true);
		setMaxStackSize(64);
		setCreativeTab(CreativeTabs.tabMisc);
		setUnlocalizedName("plantMass");
		setTextureName("redhot:plantMass");
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
				player.addPotionEffect(new PotionEffect(Potion.confusion.id, 160, 0, false));
			}
			else
			{
				/* 16 seconds, 10% chance */
				player.addPotionEffect(new PotionEffect(Potion.confusion.id, 320, 0, false));
			}
		}
		
		/* 20% overall chance */
		if(toxicity > 0.8)
		{
			
			if(toxicity < 0.9)
			{
				/* Level 1 for 4 seconds, 10% */
				player.addPotionEffect(new PotionEffect(Potion.poison.id, 80, 0, false));
			}
			else if(toxicity < 0.95)
			{
				/* Level 2 for 4 seconds, 5% */
				player.addPotionEffect(new PotionEffect(Potion.poison.id, 80, 1, false));
			}
			else
			{
				/* Level 3 for 4 seconds, 5% */
				player.addPotionEffect(new PotionEffect(Potion.poison.id, 80, 2, false));
			}
		}
	}
}
