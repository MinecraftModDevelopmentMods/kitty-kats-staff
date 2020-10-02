package com.mcmoddev.kittykatsstaff.init;

import com.mcmoddev.kittykatsstaff.KittyKatsStaff;
import com.mcmoddev.kittykatsstaff.common.items.ItemKittyKatsStaff;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemRegistry {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, KittyKatsStaff.MODID);

    public static final RegistryObject<Item> KITTY_KATS_STAFF = ITEMS.register("staff_of_kittys", ItemKittyKatsStaff::new);
    public static final RegistryObject<Item> OBSIDIAN_ROD = ITEMS.register("obsidian_rod", () ->
            new Item (new Item.Properties().group(KittyKatsStaff.CREATIVE_TAB)));

}