#version 330
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
uniform vec3 eyePosition;
vec3 calculateLightIntensity(){
vec3 ambient =  mat.ka *  base.color * base.intensity;
vec3 n = normalize(normal);
vec3 s = normalize(drlight.direction);
vec3 diffuse =  mat.kd * drlight.base.color * drlight.base.intensity * max(dot(n,s),0.0);
vec3 r = normalize(reflect(-s,n));
vec3 v = normalize(eyePosition - position);
vec3 specular = vec3(0,0,0);
if(dot(s,n) > 0.0){
specular = (drlight.base.color * drlight.base.intensity) * mat.ks * pow(max(dot(r,v),0.0), mat.shininess);
}

return mat.ke + ambient + diffuse + specular;
}

void main()
{
vec4 color = vec4(calculateLightIntensity(),1);
gl_FragColor = color;
}