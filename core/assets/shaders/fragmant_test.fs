#ifdef GL_ES
	precision mediump float;
#endif

varying vec4 v_color;
varying vec2 v_texCoords0;
uniform sampler2D u_texture;
uniform mat4 u_projTrans;

void main() {
	vec3 color = texture2D(u_texture, v_texCoords0).rgb;
	float gray = (color.r + color.g + color.b) / 3.0;
	float grayscale = vec3(gray);
	
	gl_FragColor = vec4(grayscale, 1.0);
}