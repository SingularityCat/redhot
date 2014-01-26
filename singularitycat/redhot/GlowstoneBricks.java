package singularitycat.redhot;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;

public class GlowstoneBricks extends Block {

	public GlowstoneBricks(int id) {
		super(id, Material.ground);

		setResistance(250.0f);
		setHardness(3.5f);
		setLightValue(1.0f);
		setStepSound(soundStoneFootstep);

		setCreativeTab(CreativeTabs.tabBlock);
		setUnlocalizedName("glowstoneBricks");
		setTextureName("redhot:glowstoneBricks");
	}

}
