package singularitycat.redhot;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;

public class ImpactedStone extends Block {

	public ImpactedStone(int id) {
		super(id, Material.ground);

		setResistance(500.0f);
		setHardness(4.0f);
		setLightValue(0.0f);
		setStepSound(soundStoneFootstep);

		setCreativeTab(CreativeTabs.tabBlock);
		setUnlocalizedName("impactedStone");
		setTextureName("redhot:impactedStone");
	}

}
