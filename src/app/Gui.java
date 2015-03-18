/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import javax.media.opengl.awt.GLCanvas;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;

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
        setSize(dim);
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(800, 600));
        add(canvas, BorderLayout.WEST);
        add(createControlPanel(dim), BorderLayout.EAST);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel createControlPanel(Dimension dim) {
        Dimension controlpanelDim = new Dimension(200, dim.height);
        JPanel controlPanel = new JPanel();
        controlPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        controlPanel.setPreferredSize(controlpanelDim);

        return controlPanel;
    }

}
