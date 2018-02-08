package mastef_chief.deviceai;

import mastef_chief.deviceai.init.ModItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class DeviceAITab extends CreativeTabs {


    public DeviceAITab(String label){
        super("deviceAITab");
        //this.setBackgroundImageName("");
    }

    @Override
    public ItemStack getTabIconItem() {
        return new ItemStack(ModItems.AI_ITEM);
    }
}
