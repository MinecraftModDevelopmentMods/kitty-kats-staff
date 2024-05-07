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
package witixin.kittykatsstaff.common.items;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.CatVariantTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.animal.CatVariant;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import javax.annotation.Nonnull;
import java.util.Optional;

public class ItemKittyKatsStaff extends Item {


    public ItemKittyKatsStaff(Item.Properties properties) {
        super(properties);
    }

    public boolean outOfUses(@Nonnull ItemStack stack) {
        return stack.getDamageValue() >= (stack.getMaxDamage() - 1);
    }

    /**
     * Return whether this item is repairable in an anvil.
     */
    @Override
    public boolean isValidRepairItem(@Nonnull ItemStack repairableItem, ItemStack repairMaterial) {
        final Optional<HolderSet.Named<Item>> fishTag = BuiltInRegistries.ITEM.getTag(ItemTags.FISHES);
        return fishTag.map(holders -> holders.contains(Holder.direct(repairMaterial.getItem()))).orElse(false);
    }

    @Nonnull
    @Override
    public InteractionResultHolder<ItemStack> use(@Nonnull Level level,
                                                  @Nonnull Player player, @Nonnull InteractionHand hand) {
    	ItemStack item = player.getItemInHand(hand);

    	if (hand == InteractionHand.MAIN_HAND) {

            //The position the player is looking, this is used as the position the ocelot spawns.
            Vec3 vec3d = player.getEyePosition(1.0F);
            Vec3 vec3d1 = player.getViewVector(1.0F);
            Vec3 vec3d2 = vec3d.add(vec3d1.x * 30, vec3d1.y * 30, vec3d1.z * 30);
            BlockHitResult rayTraceResult = level.clip(new ClipContext(vec3d, vec3d2,
                    ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player));

            double x = rayTraceResult.getBlockPos().getX();
            double y = rayTraceResult.getBlockPos().getY();
            double z = rayTraceResult.getBlockPos().getZ();
            if (!outOfUses(item)) {

                if (!level.isClientSide) {
                    Cat cat = EntityType.CAT.spawn((ServerLevel) level, BlockPos.containing(x, y + 1, z), MobSpawnType.MOB_SUMMONED);
                    cat.setYRot(player.getYRot());
                    cat.tame(player);
                    cat.setOrderedToSit(true);
                    cat.setPersistenceRequired();
                    BuiltInRegistries.CAT_VARIANT.getTag(CatVariantTags.DEFAULT_SPAWNS).flatMap(holders
                            -> holders.getRandomElement(level.random)).ifPresentOrElse(cat::setVariant,
                        () -> cat.setVariant(BuiltInRegistries.CAT_VARIANT.getHolderOrThrow(CatVariant.ALL_BLACK)));
                }

                level.playSound(null, x, y, z, SoundEvents.EGG_THROW, SoundSource.PLAYERS,
                        0.5F, 0.4F / (level.random.nextFloat() * 0.4F + 0.8F));

                if (!player.isCreative()) {
                    item.hurtAndBreak(1, player, LivingEntity.getSlotForHand(hand));
                    player.getCooldowns().addCooldown(this, 1200);
                }
                return InteractionResultHolder.success(item);
            } else {
                level.playSound(null, x, y, z, SoundEvents.ITEM_BREAK, SoundSource.PLAYERS,
                        0.5F, 0.4F / (level.random.nextFloat() * 0.4F + 0.8F));
                return InteractionResultHolder.pass(item);
            }
        }
        return new InteractionResultHolder<>(InteractionResult.PASS, item);
    }

    @Override
    public InteractionResult interactLivingEntity(final ItemStack itemStack, final Player player, final LivingEntity livingEntity, final InteractionHand hand){
        if (player.level().isClientSide) return InteractionResult.PASS;
        if (livingEntity instanceof Cat cat && player.getUUID().equals(cat.getOwnerUUID())) {
            cat.playSound(SoundEvents.CAT_PURR, 2.0f, 1.0f);
            cat.setLying(true);
            cat.spawnChildFromBreeding((ServerLevel) cat.level(), cat);
            player.getCooldowns().addCooldown(this, 20 * 20);
            return InteractionResult.CONSUME;
        }
        return super.interactLivingEntity(itemStack, player, livingEntity, hand);
    }
}
