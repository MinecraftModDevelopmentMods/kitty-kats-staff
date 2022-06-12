package cat.tophat.kittykatsstaff.common.items;

import net.minecraft.core.Registry;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.CatVariantTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.animal.CatVariant;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;

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
    public boolean isValidRepairItem(ItemStack repairableItem, ItemStack repairMaterial) {
        if (ForgeRegistries.ITEMS.tags().getTag(ItemTags.FISHES).contains(repairMaterial.getItem())) {
                return true;
        }
        return false;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(@Nonnull Level world, @Nonnull Player player, InteractionHand hand) {
    	ItemStack item = player.getItemInHand(hand);
    	
    	if (hand == InteractionHand.MAIN_HAND) {
        	//randomise the type of the cat used when spawning.
            RandomSource random = world.random;
            int min = 1;
            int max = 3;
            //int randomSkinValue = random.nextInt((max - min) + 1) + min;

            //The position the player is looking, this is used as the position the ocelot spawns.
            Vec3 vec3d = player.getEyePosition(1.0F);
            Vec3 vec3d1 = player.getViewVector(1.0F);
            Vec3 vec3d2 = vec3d.add(vec3d1.x * 30, vec3d1.y * 30, vec3d1.z * 30);
            BlockHitResult rayTraceResult = world.clip(new ClipContext(vec3d, vec3d2,
                    ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, player));

            double x = rayTraceResult.getBlockPos().getX();
            double y = rayTraceResult.getBlockPos().getY();
            double z = rayTraceResult.getBlockPos().getZ();
            if (!outOfUses(item)) {
            	
                if (!world.isClientSide) {
                    Cat cat = new Cat(EntityType.CAT, world);
                    cat.moveTo(x, y + 1, z, player.getYRot(), 0.0F);
                    cat.setTame(true);
                    cat.tame(player);
                    Registry.CAT_VARIANT.getTag(CatVariantTags.DEFAULT_SPAWNS).flatMap(holders -> holders.getRandomElement(random)).ifPresentOrElse(stuff -> cat.setCatVariant(stuff.value()), () -> cat.setCatVariant(CatVariant.ALL_BLACK));
                    world.addFreshEntity(cat);
                }

                world.playSound(null, x, y, z, SoundEvents.EGG_THROW, SoundSource.PLAYERS,
                        0.5F, 0.4F / (world.random.nextFloat() * 0.4F + 0.8F));

                if (!player.isCreative()) {
                    item.hurtAndBreak(1, player, (consumer) -> consumer.broadcastBreakEvent(hand));
                    player.getCooldowns().addCooldown(this, 1200);
                }
            } else {
                world.playSound(null, x, y, z, SoundEvents.ITEM_BREAK, SoundSource.PLAYERS,
                        0.5F, 0.4F / (world.random.nextFloat() * 0.4F + 0.8F));
            }
        }
        return new InteractionResultHolder<>(InteractionResult.SUCCESS, item);
    }
}