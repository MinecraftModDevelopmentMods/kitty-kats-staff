package cat.tophat.kittykatsstaff.common.items;

import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;

public class ItemKittyKatsStaff extends Item {

    public ItemKittyKatsStaff() {
        int numberOfCharges = 19;
        setMaxStackSize(1);
        setMaxDamage(numberOfCharges + 1);
    }

    public boolean outOfUses(@Nonnull ItemStack stack) {
        return stack.getItemDamage() >= (stack.getMaxDamage() - 1);
    }

    private List<ItemStack> allowedItems = null;

    /**
     * Return whether this item is repairable in an anvil.
     */
    @Override
    public boolean getIsRepairable(ItemStack repairableItem, ItemStack repairMaterial) {
        //Repair with cooked or raw fish.
        if (allowedItems == null) {
            allowedItems = OreDictionary.getOres("foodFish");
        }

        for (int i = 0; i < allowedItems.size(); i++) {
            if (allowedItems.get(i).getTranslationKey().equals(repairMaterial.getTranslationKey())) {
                return true;
            }
        }
        return false;
    }

    public ActionResult<ItemStack> onItemRightClick(@Nonnull World world, @Nonnull EntityPlayer player, EnumHand hand) {
        ItemStack item = player.getHeldItem(hand);
        //randomise the skin of the ocelot used when spawning ocelots.
        Random random = new Random();
        int min = 1;
        int max = 3;
        int randomSkinValue = random.nextInt((max - min) + 1) + min;

        //The position the player is looking, this is used as the position the ocelot spawns.
        double x = player.rayTrace(30, 1).getBlockPos().getX();
        double y = player.rayTrace(30, 1).getBlockPos().getY();
        double z = player.rayTrace(30, 1).getBlockPos().getZ();

        if (hand == EnumHand.MAIN_HAND) {
            if (!outOfUses(item)) {

                if (!world.isRemote) {
                    //TODO In 1.14 change ocelots to cats, they have been split into there own entity and ocelots can
                    // no longer be tamed but gain trust for the player who feeds them fish.
                    EntityOcelot ocelot = new EntityOcelot(world);
                    ocelot.setLocationAndAngles(x, y + 1, z, player.rotationYaw, 0.0F);
                    ocelot.setTamed(true);
                    ocelot.setTamedBy(player);
                    ocelot.setTameSkin(randomSkinValue);
                    world.spawnEntity(ocelot);
                }

                world.playSound(null, x, y, z, SoundEvents.ENTITY_EGG_THROW, SoundCategory.PLAYERS,
                        0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

                if (!player.capabilities.isCreativeMode) {
                    item.damageItem(1, player);
                    player.getCooldownTracker().setCooldown(this, 1200);
                }
            } else {
                world.playSound(null, x, y, z, SoundEvents.ENTITY_ITEM_BREAK, SoundCategory.PLAYERS,
                        0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
            }
        }

        player.addStat(StatList.getObjectUseStats(this));
        return new ActionResult<>(EnumActionResult.SUCCESS, item);
    }
}