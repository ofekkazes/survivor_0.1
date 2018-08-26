#version 130

in vec2 v_texCoord0;

uniform vec3 ambientIntensity;
uniform sampler2D u_sampler2D;

void main()
{
	gl_FragColor =  texture2D(u_sampler2D, v_texCoord0.xy) * vec4(ambientIntensity, 1);
}