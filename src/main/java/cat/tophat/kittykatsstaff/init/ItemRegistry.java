package cat.tophat.kittykatsstaff.init;

import cat.tophat.kittykatsstaff.KittyKatsStaff;
import cat.tophat.kittykatsstaff.common.items.ItemKittyKatsStaff;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nonnull;

@Mod.EventBusSubscriber(modid = KittyKatsStaff.MODID)
public class ItemRegistry {

    @SubscribeEvent
    public static void onItemRegister(@Nonnull RegistryEvent.Register<Item> event) {
        event.getRegistry().registerAll(
                nameItem(new ItemKittyKatsStaff(), "staff_of_kittys"),
                nameItem(new Item(), "obsidian_rod")
        );
    }

    private static Item nameItem(@Nonnull Item item, String name) {
        item
                .setRegistryName(name)
                .setTranslationKey(KittyKatsStaff.MODID + "." + name)
                .setCreativeTab(KittyKatsStaff.CREATIVE_TAB);
        return item;
    }
}