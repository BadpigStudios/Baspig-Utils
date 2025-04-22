package baspig.apis.utils.util.vertex;

import baspig.apis.utils.Baspig_utils;
import baspig.apis.utils.client.render.layers.CustomRenderLayers;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public final class StringRenderLayerHandler {
    private static final String minecraft = "minecraft";

    /**
     * Map to save all the <b>Render Layers</b> from this class or other mods
     */
    public static final Map<Identifier, RenderLayer> renderLayersMap = new HashMap<>();

    public StringRenderLayerHandler(){
        renderLayersMap.putIfAbsent(idMc("end_portal"), RenderLayer.getEndPortal());
        renderLayersMap.putIfAbsent(idMc("mojang_logo"), RenderLayer.getMojangLogo());
        renderLayersMap.putIfAbsent(id(Baspig_utils.MOD_ID, "drawer"), CustomRenderLayers.END_PORTAL);
    }

    public static void putNewRenderLayer(Identifier identifier, RenderLayer renderLayer){
        renderLayersMap.put(identifier, renderLayer);
    }

    public static RenderLayer getRenderLayer(Identifier identifier){
        return renderLayersMap.getOrDefault(identifier, RenderLayer.getEndPortal());
    }

    private static Identifier id(String namespace, String renderLayer){
        return Identifier.of(namespace, renderLayer);
    }

    private static Identifier idMc(String renderLayer){
        return Identifier.of(minecraft, renderLayer);
    }
}
