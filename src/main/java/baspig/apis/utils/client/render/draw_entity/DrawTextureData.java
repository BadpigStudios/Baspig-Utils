package baspig.apis.utils.client.render.draw_entity;

import net.minecraft.util.math.Direction;
import org.joml.Vector3f;

public class DrawTextureData {
    private Vector3f vector1, vector2;
    private Vector3f vector3, vector4;

    private byte alpha;

    private Direction direction;
    public DrawTextureData(Vector3f vector1, Vector3f vector2, Vector3f vector3, Vector3f vector4, byte alpha, Direction direction){
        this.vector1 = vector1;
        this.vector2 = vector2;
        this.vector3 = vector3;
        this.vector4 = vector4;
        this.alpha = alpha;
        this.direction = direction;
    }

    public Vector3f getFirstVertex() {
        return vector1;
    }

    public Vector3f getSecondVertex() {
        return vector2;
    }

    public Vector3f getThirdVertex() {
        return vector3;
    }

    public Vector3f getFourthVertex() {
        return vector4;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setVector1(Vector3f vector1) {
        this.vector1 = vector1;
    }

    public void setVector2(Vector3f vector2) {
        this.vector2 = vector2;
    }

    public void setVector3(Vector3f vector3) {
        this.vector3 = vector3;
    }

    public void setVector4(Vector3f vector4) {
        this.vector4 = vector4;
    }

    public byte getAlpha() {
        return alpha;
    }

    public void setAlpha(byte alpha) {
        this.alpha = alpha;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }
}
