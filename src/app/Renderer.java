/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import com.jogamp.opengl.util.gl2.GLUT;
import java.util.HashMap;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.gl2.GLUgl2;
import scene.Scene;
import shader.PhongShader;
import shader.Shader;
import shader.ShadowMapShader;
import transforms.Mat4;
import transforms.Vec3D;
import util.Camera;
import util.InputManager;
import util.Time;

/**
 *
 * @author Robin
 */
public class Renderer implements GLEventListener {

    long lastTime;
    int width, height; //velikost canvasu
    HashMap<String, Scene> scenes; //mapa scen
    GL2 gl;
    GLU glu;
    GLUT glut;
    InputManager inputManager;
    double spd;
    int[] colorBuffer = new int[1];
    int[] depthBuffer = new int[1];
    int[] frameBuffer = new int[1];
    int shadowWidth, shadowHeight;
    ShadowMapShader shadowmapshader;
    public Renderer(int width, int height) {
        this.width = width;
        this.height = height;
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        gl = drawable.getGL().getGL2();
        glu = new GLU();
        glut = new GLUT();
        //testovací scéna
        scenes = new HashMap<>(); //inicializace mapy scen
        scenes.put("Test", new Scene(gl)); //pøidání testovací scény
        scenes.get("Test").init(); //inicializování scény
        scenes.get("Test").loadModel("untitled.obj"); //naètení objektu
        scenes.get("Test").setCamera(new Camera(width, height)); //nastavení kamery

        shadowmapshader = new ShadowMapShader(gl);
        createShadowMap(500, 500);
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
    }
    long starTime;
    long passedTime;

    @Override
    public void display(GLAutoDrawable drawable) {
        starTime = Time.getTime(); // èas poèátku vykreslování
        passedTime = starTime - lastTime; // uplynulý èas
        lastTime = starTime;
        Time.setDelta(passedTime / Time.SECOND);
        spd = 10 * Time.getDelta();
        //uprava kamery
        inputManager.setCamera(scenes.get("Test").getCamera());
        inputManager.update(spd);
        ShadowMapShader.setBiasType(ShadowMapShader.BiasType.CONSTATSLOPESCALED);
        gl.glBindFramebuffer(GL2.GL_FRAMEBUFFER, frameBuffer[0]);
        gl.glCullFace(GL2.GL_FRONT);
        // natøít a vyèistit
        gl.glClearColor(0.8f, 0.8f, 0.8f, 1.0f);
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        // nastavení projekce
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrthof(-10, 10, -10, 10, -20, 20);
        glu.gluLookAt(1, 1, 1, 0, 0, 0, 0, 1, 0);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
        gl.glViewport(0, 0, shadowWidth, shadowHeight);
        shadowmapshader.setSubrutine(ShadowMapShader.Subroutine.FIRSTPASS);
        scenes.get("Test").display(shadowmapshader);
        setUpDepthMatrix(shadowmapshader);
        
        gl.glBindFramebuffer(GL2.GL_FRAMEBUFFER, 0);
        gl.glEnable(GL2.GL_DEPTH_TEST);
        gl.glCullFace(GL2.GL_BACK);
        gl.glClearColor(0.8f, 0.8f, 0.8f, 1.0f);
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glViewport(0, 0, width, height);
        glu.gluPerspective(45, width / (float) height, 1f, 5000.0f);
        glu.gluLookAt(inputManager.getCamera().getPosX(), inputManager.getCamera().getPosY(), inputManager.getCamera().getPosZ(),
                inputManager.getCamera().getCenX(), inputManager.getCamera().getCenY(), inputManager.getCamera().getCenZ(),
                inputManager.getCamera().getUpX(), inputManager.getCamera().getUpY(), inputManager.getCamera().getUpZ());
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
        shadowmapshader.setSubrutine(ShadowMapShader.Subroutine.SECONDPASS);
        shadowmapshader.setUniform("eyeposition", new Vec3D(inputManager.getCamera().getPosX(), inputManager.getCamera().getPosY(), inputManager.getCamera().getPosZ()));
        scenes.get("Test").display(shadowmapshader);

    }

    private void setUpDepthMatrix(Shader shader) {
        float modelview[] = new float[16];
        float projection[] = new float[16];
        gl.glGetFloatv(GL2.GL_MODELVIEW_MATRIX, modelview, 0);
        gl.glGetFloatv(GL2.GL_PROJECTION_MATRIX, projection, 0);

        // dìlí souøadnice 2
        float[] depthBiasMatrix = {0.5f, 0, 0, 0, 0, 0.5f, 0, 0, 0, 0, 0.5f, 0, 0.5f, 0.5f, 0.5f,
            1};

        Mat4 bias = new Mat4(depthBiasMatrix);
        Mat4 model = new Mat4(modelview);
        Mat4 proj = new Mat4(projection);
        Mat4 depthVMP = (model).mul(proj).mul(bias);
        shader.setUniform("biasMVP", depthVMP);
    }

    public void createShadowMap(int width, int height) {
        shadowWidth = width;
        shadowHeight = height;
        gl.glGenTextures(1, depthBuffer, 0);
        gl.glBindTexture(GL2.GL_TEXTURE_2D, depthBuffer[0]);
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_NEAREST);
        gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_NEAREST);

        gl.glTexParameterf(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_S, GL2.GL_CLAMP);
        gl.glTexParameterf(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_T, GL2.GL_CLAMP);

        gl.glTexImage2D(GL2.GL_TEXTURE_2D, 0, GL2.GL_DEPTH_COMPONENT, width, height,
                0, GL2.GL_DEPTH_COMPONENT, GL2.GL_FLOAT, null);

        gl.glActiveTexture(GL2.GL_TEXTURE0);
        gl.glBindTexture(GL2.GL_TEXTURE_2D, depthBuffer[0]);

        gl.glGenFramebuffers(1, frameBuffer, 0);
        gl.glBindFramebuffer(GL2.GL_FRAMEBUFFER, frameBuffer[0]);

        gl.glDrawBuffer(GL2.GL_NONE);
        gl.glReadBuffer(GL2.GL_NONE);

        gl.glFramebufferTexture2D(GL2.GL_FRAMEBUFFER, GL2.GL_DEPTH_ATTACHMENT, GL2.GL_TEXTURE_2D,
                depthBuffer[0], 0);

        if (gl.glCheckFramebufferStatus(GL2.GL_FRAMEBUFFER) != GL2.GL_FRAMEBUFFER_COMPLETE) {
            System.out.println("There is a problem with the FBO");
        }

        gl.glBindFramebuffer(GL2.GL_FRAMEBUFFER, 0);

    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        this.width = width;
        this.height = height;
        if (height <= 0) { // avoid a divide by zero error!
            height = 1;
        }
        final float h = width / (float) height;
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(60, h, 1, 50);

        if (inputManager.getCamera() != null) {
            inputManager.getCamera().setWidth(width);
            inputManager.getCamera().setHeight(height);
        }
    }

    public void setInputManager(InputManager inputManager) {
        this.inputManager = inputManager;
    }

}
