package baspig.apis.utils.register.easifier;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;

import java.util.Map;

class RegisterEntityRenderer {

    private static final Map<EntityType<?>, EntityRendererFactory<?>> RENDERER_FACTORIES =
            new Object2ObjectOpenHashMap<>();

    /**
     * Register a single entity renderer
     * @param entityType The type of entity to register
     * @param rendererFactory The factory for creating the renderer
     */
    public static <T extends Entity> void register(
            EntityType<T> entityType,
            EntityRendererFactory<T> rendererFactory
    ) {
        RENDERER_FACTORIES.put(entityType, rendererFactory);
        EntityRendererRegistry.register(entityType, rendererFactory);
    }

    /**
     * Batch register multiple entity renderers
     * @param rendererMap Map of entity types to their renderer factories
     */
    @SuppressWarnings("unchecked")
    private static void registerAll(Map<EntityType<?>, EntityRendererFactory<?>> rendererMap) {
        rendererMap.forEach((entityType, rendererFactory) ->
                EntityRendererRegistry.register(entityType,
                        (EntityRendererFactory<Entity>) rendererFactory)
        );
    }

    /**
     * Get a previously registered renderer factory
     * @param entityType The entity type to retrieve renderer for
     * @return The renderer factory, or null if not found
     */
    public static EntityRendererFactory<?> getRenderer(EntityType<?> entityType) {
        return RENDERER_FACTORIES.get(entityType);
    }

}