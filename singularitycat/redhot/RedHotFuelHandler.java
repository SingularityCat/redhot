package singularitycat.redhot;

import net.minecraft.item.ItemStack;
import cpw.mods.fml.common.IFuelHandler;

public class RedHotFuelHandler implements IFuelHandler {

	@Override
	public int getBurnTime(ItemStack fuel) {
		int burnTime = 0;
		
		if(fuel.itemID == RedHot.plantMass.itemID)
		{
			/* Enough to smelt 1 item. */
			burnTime = 200;
		}
		
		return burnTime;
	}
}
