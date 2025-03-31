package baspig.apis.utils.client.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderLayer;
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
                texture, x, y, 1,1, width, height, width + 1,height + 1 );
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
}
