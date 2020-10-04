package cat.tophat.kittykatsstaff;

import cat.tophat.kittykatsstaff.api.KKSItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;

@Mod(
        name = KittyKatsStaff.NAME,
        modid = KittyKatsStaff.MODID,
        version = KittyKatsStaff.VERSION,
        updateJSON = KittyKatsStaff.UPDATE_JSON,
        acceptedMinecraftVersions = KittyKatsStaff.MC_VERSION
)
public class KittyKatsStaff {

    public static final String NAME = "Kitty Kat's Staff";
    public static final String MODID = "kittykatsstaff";
    public static final String VERSION = "1.0.1";
    public static final String UPDATE_JSON = "https://tophat.cat/kitty-kats-staff/update.json";
    public static final String MC_VERSION = "[1.12, 1.12.2]";
    public static final Logger LOGGER = LogManager.getLogger(NAME);

    public static final CreativeTabs CREATIVE_TAB = new CreativeTabs(MODID) {
        @Override
        @Nonnull
        public ItemStack createIcon() {
            return new ItemStack(KKSItems.STAFF_OF_KITTYS);
        }
    };

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        LOGGER.info("Oooo what's this shiny stick do?");
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        LOGGER.info("shiny stick: *summons cats* Meow!");
        //TODO in 1.13 fish no longer use metadata so we can oredict all the fish rather than just two.
        OreDictionary.registerOre("foodFish", Items.FISH);
        OreDictionary.registerOre("foodFish", Items.COOKED_FISH);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        LOGGER.info("We are ready kat! Lets go play! Meow!");
    }
}