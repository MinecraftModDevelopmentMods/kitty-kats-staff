package dev.tophatcat.kittykatsstaff.init;

import dev.tophatcat.kittykatsstaff.KittyKatsStaff;
import dev.tophatcat.kittykatsstaff.common.items.ItemKittyKatsStaff;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemRegistry {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS,
            KittyKatsStaff.MOD_ID);

    public static final RegistryObject<Item> KITTY_KATS_STAFF = ITEMS.register("staff_of_kitties",
            () -> new ItemKittyKatsStaff(new Item.Properties()
                    .tab(KittyKatsStaff.CREATIVE_TAB)
                    .stacksTo(1)
                    .durability(20)));
    public static final RegistryObject<Item> OBSIDIAN_ROD = ITEMS.register("obsidian_rod", () ->
            new Item(new Item.Properties().tab(KittyKatsStaff.CREATIVE_TAB)));

}