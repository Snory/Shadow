/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scene.lights;

import transforms.Vec3D;

/**
 *
 * @author Robin
 */
public class BaseLight {
    Vec3D color;
    float intensity;

    public BaseLight(Vec3D color, float intensity) {
        this.color = color;
        this.intensity = intensity;
    }

    public Vec3D getColor() {
        return color;
    }

    public void setColor(Vec3D color) {
        this.color = color;
    }

    public float getIntensity() {
        return intensity;
    }

    public void setIntensity(float intensity) {
        this.intensity = intensity;
    }
    
    
    
    
}
