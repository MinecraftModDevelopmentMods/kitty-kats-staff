package com.mcmoddev.kittykatsstaff.init;

import com.mcmoddev.kittykatsstaff.KittyKatsStaff;
import com.mcmoddev.kittykatsstaff.api.KKSItems;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(modid = KittyKatsStaff.MODID, value = Side.CLIENT)
public class Rendering {

    @SubscribeEvent
    public static void registerItemModels(ModelRegistryEvent event) {
        //itemModels(KKSItems.STAFF_OF_KITTYS, 0);
        itemModels(KKSItems.OBSIDIAN_ROD, 0);
        ModelLoaderRegistry.registerLoader(new SeparateGuiModelLoader());
    }

    private static void itemModels(Item item, int meta) {
        ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }
}