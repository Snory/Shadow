/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import shader.Shader;

/**
 *
 * @author Robin
 */
public class InputManager implements KeyListener, MouseListener, MouseMotionListener {

    Camera camera;
    Shader shader;
    private double ox, oy;
    private int dx, dy;

    public void update(double spd) {

        camera.update(spd, dx, dy);
        // zmìna mezi starou pozicí myši a novým
        dy = 0;
        dx = 0;

    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public Camera getCamera() {
        return camera;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                camera.setFwd(true);
                break;
            case KeyEvent.VK_D:
                camera.setRgt(true);
                break;
            case KeyEvent.VK_S:
                camera.setBck(true);
                break;
            case KeyEvent.VK_A:
                camera.setLft(true);
                break;
            case KeyEvent.VK_E:
                camera.setDn(true);
                break;
            case KeyEvent.VK_Q:
                camera.setUp(true);
                break;
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                camera.setFwd(false);
                break;
            case KeyEvent.VK_D:
                camera.setRgt(false);
                break;
            case KeyEvent.VK_S:
                camera.setBck(false);
                break;
            case KeyEvent.VK_A:
                camera.setLft(false);
                break;
            case KeyEvent.VK_E:
                camera.setDn(false);
                break;
            case KeyEvent.VK_Q:
                camera.setUp(false);
                break;
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        dx = 0;
        dy = 0;
        ox = e.getX();
        oy = e.getY();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        dx += e.getX() - ox;
        dy += oy - e.getY();
        ox = e.getX();
        oy = e.getY();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

}
