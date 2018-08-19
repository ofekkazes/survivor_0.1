#version 130

in vec2 textCoord0;

out vec4 fragColor;

uniform vec3 baseColor;
uniform sampler2D sampler;

void main()
{
    vec4 color = vec4(baseColor, 1);

	vec4 textureColor = texture(sampler, textCoord0.xy);
	
    if(textureColor != vec4(0,0,0,0))
        color *= textureColor;

    fragColor = color; 
}