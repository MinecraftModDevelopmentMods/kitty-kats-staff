/*
 * Kitty Kat's Staff - https://github.com/Witixin1512/kitty-kats-staff
 * Copyright (C) 2016-2024 <KiriCattus>
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
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import witixin.kittykatsstaff.init.ItemRegistry;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;

@Mod(KittyKatsStaff.MOD_ID)
public class KittyKatsStaff {

    public static final String MOD_ID = "kittykatsstaff";

    public static final DeferredRegister<CreativeModeTab> CREATIVE_TABS = DeferredRegister.create(
        Registries.CREATIVE_MODE_TAB, MOD_ID);
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> KITTY_TAB = CREATIVE_TABS.register("creative_tab", () ->
        CreativeModeTab.builder()
            .icon(() -> ItemRegistry.KITTY_KATS_STAFF.get().getDefaultInstance())
            .title(Component.translatable("itemGroup.kittykatsstaff"))
            .displayItems((parameters, output) -> {
                output.accept(ItemRegistry.KITTY_KATS_STAFF.get());
                output.accept(ItemRegistry.OBSIDIAN_ROD.get());
            }).build());


    public KittyKatsStaff(IEventBus modBus) {
        CREATIVE_TABS.register(modBus);
        ItemRegistry.ITEMS.register(modBus);
    }
}
