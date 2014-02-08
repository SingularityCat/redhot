package singularitycat.redhot;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class GlowstoneBricks extends Block {

	public GlowstoneBricks() {
		super(Material.ground);

		setHarvestLevel("pickaxe", 0);
		
		setResistance(100.0f);
		setHardness(3.5f);
		setLightLevel(1.0f);
		setStepSound(soundTypeStone);

		setCreativeTab(CreativeTabs.tabBlock);
	}
}
