package singularitycat.redhot;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class ImpactedStone extends Block {

    public static final String name = "impactedStone";
    
    public ImpactedStone() {
        super(Material.GROUND);

        setHarvestLevel("pickaxe", 0);
        
        setResistance(500.0f);
        setHardness(4.0f);
        setLightLevel(0.0f);
        setSoundType(SoundType.STONE);

        setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
        setUnlocalizedName(name);
    }

}
