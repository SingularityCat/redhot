package singularitycat.redhot;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class GlowstoneBricks extends Block {

    public static final String name = "glowstoneBricks";
    
    public GlowstoneBricks() {
        super(Material.GROUND);

        setHarvestLevel("pickaxe", 0);
        
        setResistance(100.0f);
        setHardness(3.5f);
        setLightLevel(1.0f);
        setSoundType(SoundType.STONE);
        
        setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
        setUnlocalizedName(name);
    }
}
