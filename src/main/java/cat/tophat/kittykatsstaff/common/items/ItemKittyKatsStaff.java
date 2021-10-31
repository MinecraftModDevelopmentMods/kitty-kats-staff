package cat.tophat.kittykatsstaff.common.items;

import java.util.Random;
import java.util.function.Supplier;

import javax.annotation.Nonnull;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.CatEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.Tag;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Lazy;

public class ItemKittyKatsStaff extends Item {

    private final Lazy<Tag<Item>> allowedItems;

    public ItemKittyKatsStaff(Item.Properties properties, Supplier<Tag<Item>> tagSupplier) {
        super(properties);
        this.allowedItems = Lazy.of(tagSupplier);
    }

    public boolean outOfUses(@Nonnull ItemStack stack) {
        return stack.getDamageValue() >= (stack.getMaxDamage() - 1);
    }

    /**
     * Return whether this item is repairable in an anvil.
     */
    @Override
    public boolean isValidRepairItem(ItemStack repairableItem, ItemStack repairMaterial) {
        for (int i = 0; i < allowedItems.get().getValues().size(); i++) {
            if (allowedItems.get().contains(repairMaterial.getItem())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public ActionResult<ItemStack> use(@Nonnull World world, @Nonnull PlayerEntity player, Hand hand) {
    	ItemStack item = player.getItemInHand(hand);
    	
    	if (hand == Hand.MAIN_HAND) {
        	//randomise the type of the cat used when spawning.
            Random random = new Random();
            int min = 1;
            int max = 3;
            int randomSkinValue = random.nextInt((max - min) + 1) + min;

            //The position the player is looking, this is used as the position the ocelot spawns.
            Vec3d vec3d = player.getEyePosition(1.0F);
            Vec3d vec3d1 = player.getViewVector(1.0F);
            Vec3d vec3d2 = vec3d.add(vec3d1.x * 30, vec3d1.y * 30, vec3d1.z * 30);
            BlockRayTraceResult rayTraceResult = world.clip(new RayTraceContext(vec3d, vec3d2,
                    RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, player));

            double x = rayTraceResult.getBlockPos().getX();
            double y = rayTraceResult.getBlockPos().getY();
            double z = rayTraceResult.getBlockPos().getZ();
            if (!outOfUses(item)) {
            	
                if (!world.isClientSide) {
                    CatEntity cat = new CatEntity(EntityType.CAT, world);
                    cat.moveTo(x, y + 1, z, player.yRot, 0.0F);
                    cat.setTame(true);
                    cat.tame(player);
                    cat.setCatType(randomSkinValue);
                    world.addFreshEntity(cat);
                }

                world.playSound(null, x, y, z, SoundEvents.EGG_THROW, SoundCategory.PLAYERS,
                        0.5F, 0.4F / (world.random.nextFloat() * 0.4F + 0.8F));

                if (!player.isCreative()) {
                    item.hurtAndBreak(1, player, (consumer) -> consumer.broadcastBreakEvent(hand));
                    player.getCooldowns().addCooldown(this, 1200);
                }
            } else {
                world.playSound(null, x, y, z, SoundEvents.ITEM_BREAK, SoundCategory.PLAYERS,
                        0.5F, 0.4F / (world.random.nextFloat() * 0.4F + 0.8F));
            }
        }
        return new ActionResult<>(ActionResultType.SUCCESS, item);
    }
}