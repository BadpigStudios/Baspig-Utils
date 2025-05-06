package baspig.apis.utils;

import baspig.apis.utils.advanced.audio.IsRegAudio;
import baspig.apis.utils.events.block.BlockEvents;
import baspig.apis.utils.events.entity.EntityEvents;
import baspig.apis.utils.register.trimPattern.TrimPattern;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("unused")
public class Baspig_utils implements ModInitializer {

    public static final String MOD_ID = "baspig_utils", MOD_NAME = "Baspig Utils Lib";
    public static final Enum<EnvType> Environment = EnvType.SERVER;
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    protected final ModID id = new ModID(MOD_ID);

    public static final Item BCE = new TrimPattern.ItemRegistry().create(new Item.Settings(), Identifier.of(MOD_ID, "custom_item"));

    @Override
    public void onInitialize() {
        ClassCaller();
        LOGGER.info(MOD_NAME + " at your service");
    }

    @SuppressWarnings("all")
    protected void ClassCaller(){
        BlockEvents.BlockEventRegister(id);
        EntityEvents.RegisterEntityEvents(id);
        new IsRegAudio(id);
        BaspigsRegistries.reg();

        TrimPattern.addTrimPattern(Identifier.of(MOD_ID, "custom_pattern"), BCE);
    }
}