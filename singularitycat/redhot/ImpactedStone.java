package singularitycat.redhot;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;

public class ImpactedStone extends Block {

	public ImpactedStone() {
		super(Material.ground);

		setHarvestLevel("pickaxe", 0);
		
		setResistance(500.0f);
		setHardness(4.0f);
		setLightLevel(0.0f);
		setStepSound(soundTypeStone);

		setCreativeTab(CreativeTabs.tabBlock);
	}

}
