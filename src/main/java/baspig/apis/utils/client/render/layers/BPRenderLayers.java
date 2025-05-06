package baspig.apis.utils.client.render.layers;

import baspig.apis.utils.Baspig_utils;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.gl.UniformType;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.util.Identifier;


public class BPRenderLayers {

    static final String pathToShaders = "core/drawer_baspig_utils";

    private static final RenderPipeline.Snippet RENDERTYPE_BASPIG_UTILS_SNIPPET = RenderPipeline.builder(RenderPipelines.MATRICES_SNIPPET, RenderPipelines.FOG_SNIPPET)
            .withVertexShader(Identifier.of(Baspig_utils.MOD_ID, pathToShaders))
            .withFragmentShader(Identifier.of(Baspig_utils.MOD_ID, pathToShaders))
            .withSampler("Sampler0")
            .withUniform("GameTime", UniformType.FLOAT)
            .withVertexFormat(VertexFormats.POSITION, VertexFormat.DrawMode.QUADS)
            .buildSnippet();

    public static final RenderLayer BASPIG_UTILS = RenderLayer.of(
            "drawer_baspig_utils",
            1536,
            false,
            false,
            RenderPipelineCustom.END_PORTAL,
            RenderLayer.MultiPhaseParameters.builder()
                    .build(false)
    );

    static class RenderPipelineCustom{

        static final RenderPipeline END_PORTAL = RenderPipelines.register(
                RenderPipeline.builder(RENDERTYPE_BASPIG_UTILS_SNIPPET).withLocation("pipeline/baspig_utils").withShaderDefine("PORTAL_LAYERS", 15).build()
        );
    }

    public static void reg(){

    }
}