package cat.tophat.kittykatsstaff.init;

import cat.tophat.kittykatsstaff.KittyKatsStaff;
import cat.tophat.kittykatsstaff.common.items.ItemKittyKatsStaff;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemRegistry {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS,
            KittyKatsStaff.MODID);

    public static final RegistryObject<Item> KITTY_KATS_STAFF = ITEMS.register("staff_of_kittys",
            () -> new ItemKittyKatsStaff(new Item.Properties()
                    .tab(KittyKatsStaff.CREATIVE_TAB)
                    .stacksTo(1)
                    .durability(20)));
    public static final RegistryObject<Item> OBSIDIAN_ROD = ITEMS.register("obsidian_rod", () ->
            new Item(new Item.Properties().tab(KittyKatsStaff.CREATIVE_TAB)));

}