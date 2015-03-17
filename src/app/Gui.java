/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import javax.media.opengl.awt.GLCanvas;
import javax.swing.JFrame;

/**
 *
 * @author Robin
 */
public class Gui extends JFrame {

    double width, height;
    GLCanvas canvas;

    public Gui(GLCanvas canvas) throws HeadlessException {
        this.canvas = canvas;
        init();
    }

    private void init() {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        dim.width = dim.width;
        dim.height = dim.height;
        setSize(dim);
        canvas.setSize(dim.width, dim.height);
        add(canvas);
        setLocationRelativeTo(null);
        setVisible(true);
    }

}
