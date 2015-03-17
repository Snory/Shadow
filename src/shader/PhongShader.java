/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shader;

import javax.media.opengl.GL2;
import scene.lights.BaseLight;
import scene.lights.DirectionalLight;
import scene.model.Material;
import transforms.Mat4;
import transforms.Vec3D;

/**
 *
 * @author Robin
 */
public class PhongShader extends Shader {

    public static BaseLight ambientLight = new BaseLight(new Vec3D(1, 1, 1), 0.5f);
    public static DirectionalLight drLight = new DirectionalLight(new BaseLight(new Vec3D(1, 1, 1), 1.0f), new Vec3D(10, 0, 0));

    public PhongShader(GL2 gl) {
        super(gl);
        addFragmentShader(loadShader("phong.frag"));
        addVertexShader(loadShader("phong.vert"));
        compileShader();
        addUniform("modelview");
        addUniform("projection");
        addUniform("MVP");
        addUniform("mat.ke");
        addUniform("mat.ka");
        addUniform("mat.kd");
        addUniform("mat.ks");
        addUniform("mat.shininess");
        addUniform("base.color");
        addUniform("base.intensity");
        addUniform("drlight.base.color");
        addUniform("drlight.base.intensity");
        addUniform("drlight.direction");
        addUniform("eyePosition");
    }

    @Override
    public void updateUniform(Mat4 modelview, Mat4 projection, Material material) {
        setUniform("modelview", modelview);
        setUniform("projection", projection);
        setUniform("MVP", modelview.mul(projection));
        setUniform("base", ambientLight);
        setUniform("mat", material);
        setUniform("drlight", drLight);
    }

    public void setUniform(String uniformName, Material material) {
        setUniform(uniformName + ".ke", material.getKe());
        setUniform(uniformName + ".ka", material.getKa());
        setUniform(uniformName + ".kd", material.getKd());
        setUniform(uniformName + ".ks", material.getKs());
        setUniform(uniformName + ".shininess", material.getShininess());

    }

    public void setUniform(String uniformName, BaseLight light) {
        setUniform(uniformName + ".intensity", light.getIntensity());
        setUniform(uniformName + ".color", light.getColor());
    }

    public void setUniform(String uniformName, DirectionalLight light) {
        setUniform(uniformName + ".base", light.getBase());
        setUniform(uniformName + ".direction", light.getDirection());
    }

    public static void setAmbientLight(BaseLight ambientLight) {
        PhongShader.ambientLight = ambientLight;
    }

    public static void setDrLight(DirectionalLight drLight) {
        PhongShader.drLight = drLight;
    }

}
