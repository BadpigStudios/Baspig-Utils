package baspig.apis.utils.client.render.layers;

import baspig.apis.utils.Baspig_utils;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gl.UniformType;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.util.Identifier;


public class CustomRenderLayers {

    private static final String pathToShaders = "core/rendertype_end_portal";

    public static final RenderPipeline.Snippet RENDERTYPE_END_PORTAL_SNIPPET = RenderPipeline.builder(RenderPipelines.MATRICES_SNIPPET, RenderPipelines.FOG_SNIPPET)
            .withVertexShader(Identifier.of(Baspig_utils.MOD_ID, pathToShaders))
            .withFragmentShader(Identifier.of(Baspig_utils.MOD_ID, pathToShaders))
            .withSampler("Sampler0")
            .withSampler("Sampler1")
            .withUniform("GameTime", UniformType.FLOAT)
            .withVertexFormat(VertexFormats.POSITION, VertexFormat.DrawMode.QUADS)
            .buildSnippet();

    private static final Identifier LAYER_1 = Identifier.of(Baspig_utils.MOD_ID, "textures/vertex/void_block_layer_3.png");

    public static final RenderLayer END_PORTAL = RenderLayer.of(
            "end_portal",
            1536,
            false,
            false,
            RenderPipelineCustom.END_PORTAL,
            RenderLayer.MultiPhaseParameters.builder()
                    .texture(
                            RenderPhase.Textures.create()
                                    .add(LAYER_1, false, true)
                                    .build()
                    )
                    .build(false)
    );

    public static class RenderPipelineCustom{

        public static final RenderPipeline END_PORTAL = RenderPipelines.register(
                RenderPipeline.builder(RENDERTYPE_END_PORTAL_SNIPPET).withLocation("pipeline/end_portal").withShaderDefine("PORTAL_LAYERS", 15).build()
        );
    }
}
