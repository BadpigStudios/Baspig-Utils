package baspig.apis.utils.client.render;

import baspig.apis.utils.util.screen.ScreenSize;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

/**
 * This class is for Render custom images on the client screen menu and more
 * @author Baspig_
 */
@SuppressWarnings("unused")
@Environment(EnvType.CLIENT)
public class GuiTexture {


    /**This method renderers an opaque background texture in the main menu
     * ¡It doesn't override any texture!
     * @param context You must provide the DrawContext in the caller method
     * @param modId this is the mod-id for the texture path
     * @param texturePath This the texture file path. E.g: "gui/especial_image_file"
     */
    public static void renderBackgroundOpaque(DrawContext context, String modId,String texturePath){
        MinecraftClient client = MinecraftClient.getInstance();
        Identifier texture = Identifier.of(modId, "textures/"+ texturePath + ".png");

        int x = 0;
        int y = 0;
        int width = client.getWindow().getScaledWidth();
        int height = client.getWindow().getScaledHeight();

        context.drawTexture((id) -> RenderLayer.getGuiOpaqueTexturedBackground(texture),
                texture, x, y, 1,1, width, height, width + 1,height + 1 );
    }

    /**This method renderers an opaque background texture in the main menu
     * ¡It doesn't override any texture!
     * @param context You must provide the DrawContext in the caller method
     * @param modId this is the mod-id for the texture path
     * @param texturePath This the texture file path. E.g: "gui/especial_image_file"
     */
    public static void renderBackgroundOpaque(DrawContext context, String modId,String texturePath, short x, short y, short Max_x, short Max_y){
        Identifier texture = Identifier.of(modId, "textures/"+ texturePath + ".png");
        context.drawTexture((id) -> RenderLayer.getGuiOpaqueTexturedBackground(texture),
                texture, x, y, 1,1, Max_x, Max_y, Max_x + 1,Max_y + 1 );
    }

    /**This method renderers a background texture in the main menu
     * ¡It doesn't override any texture!
     * @param context You must provide the DrawContext in the caller method
     * @param modId this is the mod-id for the texture path
     * @param texturePath This the texture file path. E.g: "gui/especial_image_file"
     */
    public static void renderBackground(DrawContext context, String modId,String texturePath){
        Identifier texture = Identifier.of(modId, "textures/"+ texturePath + ".png");
        MinecraftClient client = MinecraftClient.getInstance();

        int x = 0;
        int y = 0;
        int width = client.getWindow().getScaledWidth();
        int height = client.getWindow().getScaledHeight();

        context.drawTexture((id) -> RenderLayer.getGuiTextured(texture),
                texture, x, y, 1,1, width, height, width + 1,height + 1);
    }

    /**This method renderers a background texture in the main menu
     * ¡It doesn't override any texture!
     * @param context You must provide the DrawContext in the caller method
     * @param modId this is the mod-id for the texture path
     * @param texturePath This the texture file path. E.g: "gui/especial_image_file"
     * @param x is the x start point texture size, 0 = left top screen border
     * @param y is the y start point texture size, 0 = left top screen border
     * @param Max_x is the x max point texture size
     * @param Max_y is the y max point texture size
     */
    public static void renderBackground(DrawContext context, String modId,String texturePath, short x, short y, short Max_x, short Max_y){
        Identifier texture = Identifier.of(modId, "textures/"+ texturePath + ".png");
        context.drawTexture((id) -> RenderLayer.getGuiTextured(texture),
                texture, x, y, 1,1, Max_x, Max_y, Max_x + 1,Max_y + 1 );
    }

    /**This method renderers a background texture in the main menu
     * ¡It doesn't override any texture!
     * @param context You must provide the DrawContext in the caller method
     * @param modId this is the mod-id for the texture path
     * @param texturePath This the texture file path. E.g: "gui/especial_image_file"
     * @param x is the x start point texture size, 0 = left top screen border
     * @param y is the y start point texture size, 0 = left top screen border
     * @param Max_x is the x max point texture size
     * @param Max_y is the y max point texture size
     * @param customRenderLayer It's which vanilla render layer you want to use for the texture rendering
     */
    public static void renderBackground(DrawContext context, String modId,String texturePath, short x, short y, short Max_x, short Max_y, RenderLayer customRenderLayer){
        Identifier texture = Identifier.of(modId, "textures/"+ texturePath + ".png");
        context.drawTexture((id) -> customRenderLayer,
                texture, x, y, 1,1, Max_x, Max_y, Max_x + 1,Max_y + 1 );
    }

    /**
     * This creates a gradient in the screen
     *
     * @param context DrawContext given in UI render methods
     * @param screenSize The Info about the screen size and gradient sizes
     * @param primaryColor The primary color in the gradient
     * @param secondaryColor The secondary color in the gradient
     */
    public static void renderGradient(DrawContext context, ScreenSize screenSize, int primaryColor, int secondaryColor){
        context.fillGradient(screenSize.getMin_X(), screenSize.getMin_Y(), screenSize.getMax_X(), screenSize.getMax_Y(), primaryColor, secondaryColor);
    }

    /**
     * This creates a minecraft text in the screen
     *
     * @param context DrawContext given in UI render methods
     * @param screenSize The Info about the screen size and gradient sizes
     * @param text The text to render into the screen
     * @param color The color in the text
     * @param shadow If the text has shadow
     */
    public static void renderGradient(DrawContext context, ScreenSize screenSize, Text text, int color, boolean shadow){
        context.drawText(MinecraftClient.getInstance().textRenderer, text, screenSize.getMin_X(), screenSize.getMin_Y(), color, shadow);
    }
}
