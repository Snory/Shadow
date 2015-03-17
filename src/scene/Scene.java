/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scene;

import scene.model.Model;
import scene.lights.BaseLight;
import scene.lights.DirectionalLight;
import com.jogamp.opengl.util.gl2.GLUT;
import java.util.HashMap;
import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;
import shader.PhongShader;
import util.WaveFrontParser;
import shader.Shader;
import transforms.Mat4;
import transforms.Vec3D;
import util.Camera;

/**
 *
 * @author Robin
 */
public class Scene {

    Camera camera;
    GL2 gl;
    float modelview[] = new float[16];
    float projection[] = new float[16];
    HashMap<String, Model> models;
    WaveFrontParser parser;
    BaseLight ambientLight;
    DirectionalLight drLight;

    public Scene(GL2 gl) {
        this.gl = gl;
    }

    public void init() {
        models = new HashMap<>();
        parser = new WaveFrontParser(models, gl);
    }

    public void display(Shader shader) {
        shader.bind();
        gl.glGetFloatv(GL2.GL_PROJECTION_MATRIX, projection, 0);
        gl.glGetFloatv(GL2.GL_MODELVIEW_MATRIX, modelview, 0);

        for (Model m : models.values()) {

            shader.updateUniform(new Mat4(modelview), new Mat4(projection), m.getM());
            m.bind(shader.getProgram());
            m.draw(GL2.GL_TRIANGLES);
        }
    }

    public void loadModel(String fileName) {
        parser.loadOBJ(fileName);
    }

    public void addLight(DirectionalLight light) {

    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public Camera getCamera() {
        return camera;
    }

    public void setAmbientLight(BaseLight ambientLight) {
        this.ambientLight = ambientLight;
    }

}
