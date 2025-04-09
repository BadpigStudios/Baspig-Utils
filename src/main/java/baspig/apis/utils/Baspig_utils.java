package baspig.apis.utils;

import baspig.apis.utils.advanced.audio.IsRegAudio;
import baspig.apis.utils.events.block.BlockEvents;
import baspig.apis.utils.events.entity.EntityEvents;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("unused")
public class Baspig_utils implements ModInitializer {

    public static final String MOD_ID = "baspig_utils", MOD_NAME = "Baspig Utils Lib";
    public static final Enum<EnvType> Environment = EnvType.SERVER;
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    protected final ModID id = new ModID(MOD_ID);

    @Override
    public void onInitialize() {
        ClassCaller();
        LOGGER.info(MOD_NAME + " at your service");
    }

    protected void ClassCaller(){
        BlockEvents.BlockEventRegister(id);
        EntityEvents.RegisterEntityEvents(id);
        new IsRegAudio(id);
    }

//    private void test(){
//        BlockEvents blockEvents = new BlockEvents(MOD_ID, "amethyst");
//        blockEvents.addEntityOnBreak(Blocks.DIAMOND_BLOCK, EntityType.BLAZE, ExtraItemTags.NO_TOOL_LEVEL);
//        blockEvents.playSoundOnBreak(Blocks.DIAMOND_BLOCK, SoundEvents.UI_BUTTON_CLICK.value(), SoundCategory.BLOCKS,ExtraItemTags.NO_TOOL_LEVEL, 100, 1);
//        blockEvents.playSoundOnBreak(Blocks.SPAWNER, SoundEvents.ENTITY_ENDERMITE_HURT, SoundCategory.BLOCKS,ExtraItemTags.NO_TOOL_LEVEL, 100, 1);
//
//        EntityEvents entityEvents = new EntityEvents(MOD_ID, "entitier");
//
//        entityEvents.spawnItemOnEntityDeath(EntityType.CREEPER, Items.VINE);
//        entityEvents.spawnItemOnEntityDeath(EntityType.SPIDER, Items.VINE);
//
//        entityEvents.spawnEntityOnDeath(EntityType.SPIDER, EntityType.SPIDER, 100, 2);
//    }
}