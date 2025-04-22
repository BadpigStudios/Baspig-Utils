package baspig.apis.utils.util.screen;

import baspig.apis.utils.util.GeneralUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;

/**
 * This class is only to handle numbers, no special or complex stuff
 * @author Baspig_
 */
public class ScreenSize {

    public short screenWidth, screenHeight;

    protected VertexConsumer vertexConsumer;

    protected RenderLayer renderLayer;

    int Min_X;
    int Min_Y;
    int Max_X;
    int Max_Y;

    /**
     * It's a helper class, it doesn't do anything special
     *
     * @param screenWidth Size of the width of the screen
     * @param screenHeight Size of the height of the screen
     * @param offsetX Offset X of the screen, by default centered to 0
     * @param offsetY Offset Y of the screen, by default centered to 0
     */
    public ScreenSize(int screenWidth, int screenHeight, short offsetX, short offsetY){

        renderLayer = null;
        vertexConsumer = null;

        offsetX = GeneralUtils.switchToPositive(offsetX);
        offsetY = GeneralUtils.switchToPositive(offsetY);

        Min_X = offsetX;
        Min_Y = offsetY;
        Max_X = screenWidth + offsetX;
        Max_Y = screenHeight + offsetY;
    }

    /**
     * It's a helper class, it doesn't do anything special
     *
     * @param minX The start X point of the screen total size
     * @param minY The start Y point of the screen total size
     * @param maxX The max X point of the screen total size
     * @param maxY The max Y point of the screen total size
     */
    public ScreenSize(int minX, int minY, int maxX, int maxY){

        renderLayer = null;
        vertexConsumer = null;

        Min_X = GeneralUtils.switchToPositive(minX);
        Min_Y = GeneralUtils.switchToPositive(minY);
        Max_X = GeneralUtils.switchToPositive(maxX);
        Max_Y = GeneralUtils.switchToPositive(maxY);

        screenWidth = (short) (maxX - minX);
        screenHeight = (short) (maxY - minY);
    }

    /**
     * It's a helper class, it doesn't do anything special
     *
     * @param minX The start X point of the screen total size
     * @param minY The start Y point of the screen total size
     * @param maxX The max X point of the screen total size
     * @param maxY The max Y point of the screen total size
     */
    public ScreenSize(RenderLayer RenderLayer, int minX, int minY, int maxX, int maxY){
        renderLayer = RenderLayer;
        vertexConsumer = null;
        Min_X = GeneralUtils.switchToPositive(minX);
        Min_Y = GeneralUtils.switchToPositive(minY);
        Max_X = GeneralUtils.switchToPositive(maxX);
        Max_Y = GeneralUtils.switchToPositive(maxY);

        screenWidth = (short) (maxX - minX);
        screenHeight = (short) (maxY - minY);
    }

    /**
     * It's a helper class, it doesn't do anything special
     *
     * @param minX The start X point of the screen total size
     * @param minY The start Y point of the screen total size
     * @param maxX The max X point of the screen total size
     * @param maxY The max Y point of the screen total size
     */
    public ScreenSize(VertexConsumer VertexConsumer, int minX, int minY, int maxX, int maxY){
        renderLayer = null;
        vertexConsumer = VertexConsumer;
        Min_X = GeneralUtils.switchToPositive(minX);
        Min_Y = GeneralUtils.switchToPositive(minY);
        Max_X = GeneralUtils.switchToPositive(maxX);
        Max_Y = GeneralUtils.switchToPositive(maxY);

        screenWidth = (short) (maxX - minX);
        screenHeight = (short) (maxY - minY);
    }

    public int getMax_X() {
        return Max_X;
    }

    public int getMax_Y() {
        return Max_Y;
    }

    public int getMin_X() {
        return Min_X;
    }

    public int getMin_Y() {
        return Min_Y;
    }

    public RenderLayer getRenderLayer() {
        return renderLayer;
    }

    public VertexConsumer getVertexConsumer() {
        return vertexConsumer;
    }

    private void reSetSizes(int screenWidth, int screenHeight, byte offsetX, byte offsetY){
        offsetX = GeneralUtils.switchToPositive(offsetX);
        offsetY = GeneralUtils.switchToPositive(offsetY);

        Min_X = offsetX;
        Min_Y = offsetY;
        Max_X = screenWidth + offsetX;
        Max_Y = screenHeight + offsetY;
    }

    public void setSizeWith(byte ratioWidth, byte ratioHeight, byte screenHeight){
        int width = 500, height = 500;

        screenHeight = (byte) (screenHeight / ratioWidth);
        width = screenHeight * ratioWidth;
        height = screenHeight * ratioHeight;

        reSetSizes(width, height, (byte) MinecraftClient.getInstance().getWindow().getWidth(), (byte) MinecraftClient.getInstance().getWindow().getHeight());
    }

    public void setSizeWith(ScreenRatios screenRatio, byte screenHeight){
        int width = 500, height = 500;
        switch (screenRatio){
            case SR_16_9 -> {
                screenHeight = (byte) (screenHeight / 9);
                width = screenHeight * 16;
                height = screenHeight * 9;
            }
            case SR_3_2 -> {
                screenHeight = (byte) (screenHeight / 3);
                width = screenHeight * 3;
                height = screenHeight * 2;
            }
            case SR_4_3 -> {
                screenHeight = (byte) (screenHeight / 4);
                width = screenHeight * 4;
                height = screenHeight * 3;
            }
            case SR_1_1 -> {
                width = screenHeight; //ignore unuseful warning
                height = screenHeight;
            }
            case SR_21_9 -> {
                screenHeight = (byte) (screenHeight / 21);
                width = screenHeight * 21;
                height = screenHeight * 9;
            }
        }
        reSetSizes(width, height, (byte) MinecraftClient.getInstance().getWindow().getWidth(), (byte) MinecraftClient.getInstance().getWindow().getHeight());
    }
}
