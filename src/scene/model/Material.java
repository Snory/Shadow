/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scene.model;

import transforms.Vec3D;

/**
 *
 * @author Robin
 */
public class Material {
private Vec3D ks;
private Vec3D ke;
private Vec3D kd;
private Vec3D ka;
private float shininess;

    public Material() {
    }

    public Material(Vec3D ks, Vec3D ke, Vec3D kd, Vec3D ka, float shininess) {
        this.ks = ks;
        this.ke = ke;
        this.kd = kd;
        this.ka = ka;
        this.shininess = shininess;
    }
    
    public Material(Vec3D ambientANDdiffuse){
        this(new Vec3D(1, 1, 1), new Vec3D(0, 0, 0), ambientANDdiffuse, ambientANDdiffuse, 50.0f);
    }

    public Vec3D getKs() {
        return ks;
    }

    public void setKs(Vec3D ks) {
        this.ks = ks;
    }

    public Vec3D getKe() {
        return ke;
    }

    public void setKe(Vec3D ke) {
        this.ke = ke;
    }

    public Vec3D getKd() {
        return kd;
    }

    public void setKd(Vec3D kd) {
        this.kd = kd;
    }

    public Vec3D getKa() {
        return ka;
    }

    public void setKa(Vec3D ka) {
        this.ka = ka;
    }

    public float getShininess() {
        return shininess;
    }

    public void setShininess(float shininess) {
        this.shininess = shininess;
    }
    
    



}
