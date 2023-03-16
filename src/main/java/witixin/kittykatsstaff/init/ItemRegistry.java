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
package witixin.kittykatsstaff.init;

import witixin.kittykatsstaff.KittyKatsStaff;
import witixin.kittykatsstaff.common.items.ItemKittyKatsStaff;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ItemRegistry {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS,
            KittyKatsStaff.MOD_ID);

    public static final RegistryObject<Item> KITTY_KATS_STAFF = ITEMS.register("staff_of_kitties",
            () -> new ItemKittyKatsStaff(new Item.Properties()
                    .stacksTo(1)
                    .durability(20)));
    public static final RegistryObject<Item> OBSIDIAN_ROD = ITEMS.register("obsidian_rod", () ->
            new Item(new Item.Properties()));



}
