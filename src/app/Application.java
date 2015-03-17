/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import com.jogamp.opengl.util.FPSAnimator;
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLProfile;
import javax.media.opengl.awt.GLCanvas;
import util.InputManager;

/**
 *
 * @author Robin
 */
public class Application {

    /**
     * @param args
     */
    public static void main(String[] args) {
        try {
            //width, height
            int width = 512;
            int height = 384;
            // setup OpenGL Version 2
            GLProfile profile = GLProfile.get(GLProfile.GL2);
            GLCapabilities capabilities = new GLCapabilities(profile);
            // The canvas is the widget that's drawn in the JFrame
            final GLCanvas canvas = new javax.media.opengl.awt.GLCanvas(capabilities);
            final FPSAnimator animator = new FPSAnimator(canvas, 60, true);

            //frame
            Gui gui = new Gui(canvas);
           //Renderer
            Renderer renderer = new Renderer(width, height);
            //input manager
            InputManager inputManager = new InputManager();
            renderer.setInputManager(inputManager);
            //canvas
            canvas.setFocusable(true);
            canvas.requestFocus();
            //listenery
            canvas.addGLEventListener(renderer);
            canvas.addKeyListener(inputManager);
            canvas.addMouseListener(inputManager);
            canvas.addMouseMotionListener(inputManager);
            //canvas na frame
            gui.add(canvas);
            // shutdown the program on windows close event
            // final Animator animator = new Animator(canvas);
            gui.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    new Thread() {
                        @Override
                        public void run() {
                            if (animator.isStarted()) {
                                animator.stop();
                            }
                            System.exit(0);
                        }
                    }.start();
                }
            });
            animator.start(); // start the animation loop
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
