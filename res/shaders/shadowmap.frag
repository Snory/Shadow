#version 330
uniform int subrutine;
uniform float constantBias;
uniform sampler2DShadow shadowmap;
struct Material{
vec3 ke;
vec3 ka;
vec3 kd;
vec3 ks;
float shininess;
};
uniform Material mat;
struct BaseLight{
vec3 color;
float intensity;
};
uniform BaseLight base;

struct DirectionalLight{
BaseLight base;
vec3 direction;
};
uniform DirectionalLight drlight;

struct Attentuation{
float linear;
float constant;
float exponential;
};

struct PointLight{
BaseLight base;
Attentuation att;
vec3 position;
};

in vec3 normal;
in vec3 position;
in vec4 shadowcoord;
in vec2 texture;
uniform vec3 eyeposition;
uniform int biasType;


vec2 poisson[4] = vec2[]
(
    vec2( -0.94201624, -0.39906216 ),
  vec2( 0.94558609, -0.76890725 ),
  vec2( -0.094184101, -0.92938870 ),
  vec2( 0.34495938, 0.29387760 )
);


vec3 calculateDiffSpec(){
vec3 n = normalize(normal);
vec3 s = normalize(drlight.direction);
vec3 diffuse =  mat.kd * drlight.base.color * drlight.base.intensity * max(dot(s,n),0.0);
vec3 r = normalize(reflect(-s,n));
vec3 v = normalize(eyeposition - position);
vec3 specular = vec3(0,0,0);
if(dot(s,n) > 0.0){
specular = (drlight.base.color * drlight.base.intensity) * mat.ks * pow(max(dot(r,v),0.0), mat.shininess);
}
return  diffuse + specular;
}

float PCFgrid(vec4 shadowcoord, float bias){
float shadowfactor = 0;
float offsetXY = 1.0/500;
for(int y = -1; y <= 1; y++){
for(int x = -1; x <= 1; x++){
    vec2 offset = vec2( x * offsetXY, y * offsetXY);
    vec3 shadowCoordBiased = vec3(shadowcoord.xy + offset, shadowcoord.z - bias);
    shadowfactor += texture(shadowmap,shadowCoordBiased);
}
}
shadowfactor = (shadowfactor / 9);
return shadowfactor;
}

float PCFpoisson(vec4 shadowcoord, float bias){
float shadowfactor = 0;
for(int i = 0; i<4; i++){
vec2 offset = poisson[i]/200;
vec3 shadowCoordBiased = vec3(shadowcoord.xy + offset, shadowcoord.z - bias);
shadowfactor += texture(shadowmap, shadowCoordBiased);
}
return shadowfactor/4;
}


void main(){

if(subrutine == 1){
float z =  gl_FragCoord.z ;
gl_FragColor = vec4(z, z, z, 1.0);

}else if(subrutine ==2){
float shadow = 0;
vec4 shadowcoordWdiv = shadowcoord / shadowcoord.w;
float bias = 0;
if(biasType == 0){
bias = 0.01888;
}else if (biasType ==1){
float cosalpha = clamp(dot(normal,drlight.direction),0,1);
float offsetN = sqrt(1 - cosalpha * cosalpha);
float offsetL = offsetN / cosalpha;
bias =  0.05 * offsetL;
}
if(shadowcoordWdiv.w > 0.0){
shadow = PCFgrid(shadowcoordWdiv, bias);
}
vec3 ambient =  mat.ka *  base.color * base.intensity;
vec3 diffspec = calculateDiffSpec();
gl_FragColor = vec4(mat.ke + ambient + diffspec * shadow, 1);

}else if(subrutine ==0){
vec3 ambient =  mat.ka *  base.color * base.intensity;
vec3 diffspec = calculateDiffSpec();
gl_FragColor = vec4(mat.ke + ambient + diffspec, 1);
}

} 