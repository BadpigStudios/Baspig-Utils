package baspig.apis.utils.client.render.draw_entity;

import baspig.apis.utils.util.BPString;
import baspig.apis.utils.util.pos.PosUtils;
import baspig.apis.utils.util.vertex.StringRenderLayerHandler;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.render.Frustum;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.state.EntityHitbox;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.util.List;

public class DrawEntityRender<T extends Entity> extends EntityRenderer<T, DrawEntityRender.DrawEntityRenderState> {

    public int alpha;
    private Entity entity;

    public DrawEntityRender(EntityRendererFactory.Context context) {
        super(context);
        this.shadowRadius = 0.0f;
        this.shadowOpacity = 0.0f;
    }

    @Override
    public boolean shouldRender(T entity, Frustum frustum, double x, double y, double z) {
        return true;
    }

    @Override
    public void updateRenderState(T entity, DrawEntityRenderState state, float tickProgress) {
        this.entity = entity;
    }

    @Override
    public void render(DrawEntityRenderState state, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        if (!(entity instanceof DrawEntity drawEntity)) return;

        String dataString = drawEntity.getStringData();
        if (dataString == null || dataString.isEmpty()) return;

        DrawTextureData data = new DrawTextureData(
                BPString.getVector3fFromStringArgument(dataString, (short) 0),
                BPString.getVector3fFromStringArgument(dataString, (short) 1),
                BPString.getVector3fFromStringArgument(dataString, (short) 2),
                BPString.getVector3fFromStringArgument(dataString, (short) 3),
                Byte.parseByte(BPString.getStringArgument(dataString, (short) 4)),
                Direction.valueOf(BPString.getStringArgument(dataString, (short) 6).toUpperCase())
        );

        MatrixStack.Entry entry = matrices.peek();
        Matrix4f positionMatrix = entry.getPositionMatrix();

        String[] identifierData = BPString.getStringArgument(dataString, (short) 5).split(":");

        VertexConsumer buffer = vertexConsumers.getBuffer(
                StringRenderLayerHandler.getRenderLayer(Identifier.of(identifierData[0], identifierData[1]))
        );

        renderShader(buffer, positionMatrix, entry, data);
    }

    @Override
    protected void appendHitboxes(T entity, ImmutableList.Builder<EntityHitbox> builder, float tickProgress) {
        super.appendHitboxes(entity, builder, tickProgress);
    }

    @Override
    public DrawEntityRenderState createRenderState() {
        return new DrawEntityRenderState();
    }

    public static class DrawEntityRenderState extends EntityRenderState {}

    private void renderShader(VertexConsumer buffer, Matrix4f positionMatrix, MatrixStack.Entry entry, DrawTextureData data){

        Vector3f first_vertex, second_vertex, third_vertex, fourth_vertex;

        List<Vector3f> fList = PosUtils.fixVectorsWithDirection(data.getFirstVertex(), data.getSecondVertex(), data.getThirdVertex(), data.getFourthVertex(), data.getDirection());

        first_vertex = fList.getFirst();
        second_vertex = fList.get(1);
        third_vertex = fList.get(2);
        fourth_vertex = fList.get(3);



        /// Here all the vertex for the texture points are created
        buffer.vertex(positionMatrix, fourth_vertex.x, fourth_vertex.y - 0.5f, fourth_vertex.z)
                .color(0, 0, 0, alpha)
                .texture(1.0f, 0.0f)
                .overlay(OverlayTexture.DEFAULT_UV)
                .light(15728880)
                .normal(entry, 0, 0, 0);

        buffer.vertex(positionMatrix, third_vertex.x, third_vertex.y + 0.5f, third_vertex.z)
                .color(0, 0, 0, alpha)
                .texture(0.0f, 0.0f)
                .overlay(OverlayTexture.DEFAULT_UV)
                .light(15728880)
                .normal(entry, 0, 0, 0);

        buffer.vertex(positionMatrix, second_vertex.x, second_vertex.y + 0.5f, second_vertex.z)
                .color(0, 0, 0, alpha)
                .texture(1.0f, 0.0f)
                .overlay(OverlayTexture.DEFAULT_UV)
                .light(15728880)
                .normal(entry, 0, 0, 0);

        buffer.vertex(positionMatrix, first_vertex.x, first_vertex.y - 0.5f, first_vertex.z)
                .color(0, 0, 0, alpha)
                .texture(0.0f, 0.0f)
                .overlay(OverlayTexture.DEFAULT_UV)
                .light(15728880)
                .normal(entry, 0, 0, 0);

    }
}
