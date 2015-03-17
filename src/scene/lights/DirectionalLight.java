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
public class DirectionalLight {
    BaseLight base;
    Vec3D direction;

    public DirectionalLight() {
    }

    public DirectionalLight(BaseLight base, Vec3D direction) {
        this.base = base;
        this.direction = direction;
    }

    public BaseLight getBase() {
        return base;
    }

    public void setBase(BaseLight base) {
        this.base = base;
    }

    public Vec3D getDirection() {
        return direction;
    }

    public void setDirection(Vec3D direction) {
        this.direction = direction;
    }
    
    
}
