package cat.tophat.kittykatsstaff;

import cat.tophat.kittykatsstaff.init.ItemRegistry;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(KittyKatsStaff.MODID)
public class KittyKatsStaff {

    public static final String MODID = "kittykatsstaff";
    public static final CreativeModeTab CREATIVE_TAB = new CreativeModeTab(KittyKatsStaff.MODID) {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ItemRegistry.KITTY_KATS_STAFF.get());
        }
    };

    public KittyKatsStaff() {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ItemRegistry.ITEMS.register(modEventBus);
    }
}
