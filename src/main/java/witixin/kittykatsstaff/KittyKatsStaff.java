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

import net.minecraft.core.registries.Registries;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import witixin.kittykatsstaff.init.ItemRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(KittyKatsStaff.MOD_ID)
public class KittyKatsStaff {

    public static final String MOD_ID = "kittykatsstaff";

    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(
        Registries.CREATIVE_MODE_TAB, MOD_ID);
    public static final RegistryObject<CreativeModeTab> KITTY_TAB = CREATIVE_TABS.register("creative_tab", () ->
        CreativeModeTab.builder()
            .icon(() -> ItemRegistry.KITTY_KATS_STAFF.get().getDefaultInstance())
            .title(Component.translatable("itemGroup.kittykatsstaff"))
            .displayItems((parameters, output) -> {
                output.accept(ItemRegistry.KITTY_KATS_STAFF.get());
                output.accept(ItemRegistry.OBSIDIAN_ROD.get());
            }).build());

    public KittyKatsStaff() {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        CREATIVE_TABS.register(modEventBus);
        ItemRegistry.ITEMS.register(modEventBus);
    }
}
