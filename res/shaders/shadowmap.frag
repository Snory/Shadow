#version 330
uniform int subrutine;
uniform sampler2D shadowmap;
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
in vec3 normal;
in vec3 position;
in vec4 shadowcoord;
in vec2 texture;
uniform vec3 eyeposition;

vec3 calculateDiffSpec(){
vec3 n = normalize(normal);
vec3 s = normalize(drlight.direction);
vec3 diffuse =  mat.kd * drlight.base.color * drlight.base.intensity * max(dot(n,s),0.0);
vec3 r = normalize(reflect(-s,n));
vec3 v = normalize(eyeposition - position);
vec3 specular = vec3(0,0,0);
if(dot(s,n) > 0.0){
specular = (drlight.base.color * drlight.base.intensity) * mat.ks * pow(max(dot(r,v),0.0), mat.shininess);
}

return mat.ke +  diffuse + specular;
}

void main(){

if(subrutine == 1){
float z =  gl_FragCoord.z ;
gl_FragColor = vec4(z, z, z, 1.0);

}else if(subrutine ==2){
float shadow = 1;
if(texture(shadowmap,shadowcoord.xy).z < shadowcoord.z ){
shadow = 0;
}
vec3 ambient =  mat.ka *  base.color * base.intensity;
vec3 diffspec = calculateDiffSpec();
gl_FragColor = vec4(ambient + diffspec * shadow, 1);

}else if(subrutine ==0){
vec3 ambient =  mat.ka *  base.color * base.intensity;
vec3 diffspec = calculateDiffSpec();
gl_FragColor = vec4(ambient + diffspec, 1);
}

} 