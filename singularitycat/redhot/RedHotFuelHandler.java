package singularitycat.redhot;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.IFuelHandler;

public class RedHotFuelHandler implements IFuelHandler {

    @Override
    public int getBurnTime(ItemStack fuel) {
        int burnTime = 0;
        
        if(fuel.getItem() == RedHot.plantMass)
        {
            /* Enough to smelt 1 item. */
            burnTime = 200;
        }
        
        return burnTime;
    }
}
