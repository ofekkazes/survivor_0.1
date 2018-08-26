#version 130

attribute vec3 a_position;
attribute vec2 a_texCoord0;

out vec2 v_texCoord0;

uniform mat4 projTrans;

void main()
{
	v_texCoord0 = a_texCoord0;
	gl_Position = projTrans * vec4(a_position, 1.0);
}