#version 150

#moj_import <minecraft:fog.glsl>
#moj_import <minecraft:matrix.glsl>

uniform sampler2D Sampler0; // Tu textura (imagen, interfaz, video, etc.)
uniform float FogStart;
uniform float FogEnd;
uniform vec4 FogColor;

in vec4 texProj0;
in float vertexDistance;

out vec4 fragColor;

void main() {
    // Proyecta la textura sobre la geometría
    vec2 uv = texProj0.xy / texProj0.w;

    // Alternativamente puedes invertir ejes si la imagen está al revés:
    uv.y = 1.0 - uv.y;

    // Color base desde la textura
    vec4 texColor = texture(Sampler0, uv);

    // Puedes añadir efectos visuales, por ejemplo: bajarle la opacidad
    texColor *= 0.95;

    // Resultado con fog
    fragColor = linear_fog(texColor, vertexDistance, FogStart, FogEnd, FogColor);
}
