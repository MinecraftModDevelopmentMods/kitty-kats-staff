/*
 * Kitty Kat's Staff - https://github.com/Witixin1512/kitty-kats-staff
 * Copyright (C) 2016-2023 <KiriCattus>
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation;
 * Specifically version 2.1 of the License.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301
 * USA
 * https://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
 */
package witixin.kittykatsstaff;

import witixin.kittykatsstaff.init.ItemRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(KittyKatsStaff.MOD_ID)
public class KittyKatsStaff {

    public static final String MOD_ID = "kittykatsstaff";

    public static CreativeModeTab KITTY_TAB;

    public KittyKatsStaff() {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(KittyKatsStaff::registerCustomTab);
        ItemRegistry.ITEMS.register(modEventBus);
    }

    public static void registerCustomTab(final CreativeModeTabEvent.Register event) {

        KITTY_TAB = event.registerCreativeModeTab(
            new ResourceLocation(MOD_ID, "creative_tab"),
            (configurator) -> configurator
                .icon(() -> ItemRegistry.KITTY_KATS_STAFF.get().getDefaultInstance())
                .title(Component.translatable("itemGroup.kittykatsstaff"))
                .displayItems((features, output) -> {
                    output.accept(ItemRegistry.KITTY_KATS_STAFF.get());
                    output.accept(ItemRegistry.OBSIDIAN_ROD.get());
                })
                .build());
    }
}
