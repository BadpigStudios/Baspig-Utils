package baspig.apis.utils.client;

import baspig.apis.utils.BaspigsRegistries;
import baspig.apis.utils.client.render.draw_entity.DrawEntityRender;
import baspig.apis.utils.client.render.draw_entity.edit.DrawEntityEditTool;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.debug.DebugRenderer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("unused")
public class Baspig_utilsClient implements ClientModInitializer {

    public static final String MOD_ID = "baspig_utils", MOD_NAME = "Baspig Utils Lib";
    public static final Enum<EnvType> Environment = EnvType.CLIENT;
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitializeClient() {
        /// Registers all the needed packets for data transfer
        /// Registers the Draw Entity
        EntityRendererRegistry.register(
                BaspigsRegistries.DRAW_ENTITY,
                DrawEntityRender::new);
        /// Registers the client side screen for the Draw Entity
        WorldRenderEvents.AFTER_ENTITIES.register(context -> {
            MinecraftClient client = MinecraftClient.getInstance();
            PlayerEntity player = client.player;
            if (player == null) return;

            if (!(player.getMainHandStack().getItem() instanceof DrawEntityEditTool)) return;

            HitResult hit = player.raycast(10.0D, context.tickCounter().getFixedDeltaTicks(), false);
            if (hit.getType() == HitResult.Type.BLOCK) {
                BlockHitResult blockHit = (BlockHitResult) hit;
                BlockPos pos = blockHit.getBlockPos();

                DebugRenderer.drawBox(
                        context.matrixStack(),
                        context.consumers(),
                        pos, pos,
                        1.0f, 1.0f, 1.0f, 0.5f
                );
            }
        });
    }
}
