package baspig.apis.utils;

import baspig.apis.utils.advanced.audio.IsRegAudio;
import baspig.apis.utils.events.block.BlockEvents;
import baspig.apis.utils.events.block.BlockSettingClasses;
import baspig.apis.utils.events.entity.EntityEvents;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.Random;

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
        BlockEvents blockEvents = new BlockEvents(MOD_ID);
    }

    protected void ClassCaller(){
        BlockEvents.BlockEventRegister(id);
        EntityEvents.EntityEventsRegistry(id);
        new IsRegAudio(id);
    }

    private void test(){
        RegisterExecuter.initializeAll(MOD_ID);

        Random random = new Random();
        final String a = String.valueOf(random.nextDouble());

        BlockSettingClasses.GeneralSettingsMap.put(MOD_ID + a, BlockSettingClasses.ExplodeHashMap);
        BlockSettingClasses.GeneralSettingsMap.put(MOD_ID + a, BlockSettingClasses.SpawnEntityHashMap);
    }
}