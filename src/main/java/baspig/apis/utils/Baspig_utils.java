package baspig.apis.utils;

import baspig.apis.utils.events.block.BlockEvents;
import baspig.apis.utils.events.entity.EntityEvents;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Baspig_utils implements ModInitializer {

    public static final String MOD_ID = "baspig_utils";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    @Override
    public void onInitialize() {

        BlockEvents.DestroyEvent();
        EntityEvents.BlockEventsRegistry();

        LOGGER.info("Baspig Library at your service");



    }

}