#version 330
layout (location = 0) in vec3 inPosition;
layout (location = 1) in vec2 inTexture;
layout (location = 2) in vec3 inNormal;
uniform mat4 modelview;
uniform mat4 projection;
uniform mat4 MVP;
uniform mat4 biasMVP;
uniform float normalBias;
uniform int biasType;
out vec3 normal;
out vec3 position;
out vec4 shadowcoord;
out vec2 texture;
void main(){
texture = inTexture;
vec3 normalBiasPosition = inPosition;
if(biasType == 2){
normalBiasPosition += inNormal * 0.05;
}
normal = (modelview * vec4(inNormal,0)).xyz;
position = (modelview * vec4(inPosition,1)).xyz ;
shadowcoord = biasMVP * vec4(normalBiasPosition,1.0);
gl_Position = MVP * vec4(inPosition,1.0); 
}