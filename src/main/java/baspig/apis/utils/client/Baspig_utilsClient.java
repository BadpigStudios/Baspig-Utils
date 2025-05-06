package baspig.apis.utils.client;

import baspig.apis.utils.client.render.layers.BPRenderLayers;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@SuppressWarnings("unused")
public final class Baspig_utilsClient implements ClientModInitializer {

    public static final String MOD_ID = "baspig_utils", MOD_NAME = "Baspig Utils Lib";
    public static final Enum<EnvType> Environment = EnvType.CLIENT;
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitializeClient() {
        BPRenderLayers.reg();
    }
}
