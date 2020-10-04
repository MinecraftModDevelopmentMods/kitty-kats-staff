package cat.tophat.kittykatsstaff;

import cat.tophat.kittykatsstaff.init.ItemRegistry;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(KittyKatsStaff.MODID)
public class KittyKatsStaff {

    public static final String MODID = "kittykatsstaff";
    public static ItemGroup CREATIVE_TAB = new ItemGroup(KittyKatsStaff.MODID) {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ItemRegistry.KITTY_KATS_STAFF.get());
        }
    };

    public KittyKatsStaff() {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ItemRegistry.ITEMS.register(modEventBus);
    }
}
