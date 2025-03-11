package baspig.apis.utils;

import baspig.apis.utils.register.GroupTabRegister;
import net.fabricmc.api.ModInitializer;
import net.minecraft.item.Items;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


public class Baspig_utils implements ModInitializer {

    public static final String MOD_ID = "baspig_utils";

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    @Override
    public void onInitialize() {
        LOGGER.info("Baspig Library at your service");

        GroupTabRegister.create(MOD_ID,
                "example",
                Items.GOLD_BLOCK,
                List.of(
                        Items.ACACIA_PLANKS.getDefaultStack(),
                        Items.DIAMOND.getDefaultStack()
                ),
                (byte) 1);

    }
}