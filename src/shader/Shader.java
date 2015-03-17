/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import javax.media.opengl.GL2;
import scene.model.Material;
import transforms.Mat4;
import transforms.Vec3D;
import util.Util;

/**
 *
 * @author Robin
 */
public class Shader {

    private int program;
    private int shader;
    private HashMap<String, Integer> uniforms;
    private GL2 gl;
    public enum Subroutine  {DEFAULT, FIRSTPASS, SECONDPASS};
    Subroutine subrutine;
   

    public Shader(GL2 gl) {
        this.gl = gl;
        init();
    }

    private void init() {
        program = gl.glCreateProgram();
        uniforms = new HashMap<>();
        if (program == 0) {
            System.err.println("Nemohl jsem inicializovat shader");
            System.exit(1);
        }
    }

    public void bind() {
        gl.glUseProgram(program);
    }

    public void addVertexShader(String[] text) {
        addProgram(text, GL2.GL_VERTEX_SHADER);

    }

    public void addFragmentShader(String[] text) {
        addProgram(text, GL2.GL_FRAGMENT_SHADER);
    }

    private void addProgram(String[] text, int type) {
        shader = gl.glCreateShader(type);
        gl.glShaderSource(shader, text.length, text, (int[]) null, 0);
        gl.glCompileShader(shader);
        int[] compiled = new int[1];
        gl.glGetShaderiv(shader, GL2.GL_COMPILE_STATUS, compiled, 0);
        if (compiled[0] == 0) {
            System.err.println("V shaderu se vyskytuje chyba");
        }
        gl.glAttachShader(program, shader);
    }

    public void compileShader() {
        gl.glLinkProgram(program);
        int[] compiled = new int[1];
        gl.glGetProgramiv(program, GL2.GL_LINK_STATUS, compiled, 0);
        if (compiled[0] == 0) {
            System.err.println("Program se nepodaøilo vytvoøit");
            gl.glDetachShader(program, shader);
        }
    }

    public void addUniform(String uniform) {
        int uniformloc = gl.glGetUniformLocation(program, uniform);
        uniforms.put(uniform, uniformloc);
    }

    public void setUniform(String uniformName, int value) {
        gl.glUniform1i(uniforms.get(uniformName), value);
    }

    public void setUniform(String uniformName, float value) {
        gl.glUniform1f(uniforms.get(uniformName), value);
    }

    public void setUniform(String uniformName, Vec3D value) {
        gl.glUniform3f(uniforms.get(uniformName), (float) value.x,
                (float) value.y, (float) value.z);
    }

    public void setUniform(String uniformName, Mat4 value) {
        gl.glUniformMatrix4fv(uniforms.get(uniformName), 1, false,
                Util.convert(value), 0);
    }

    public void updateUniform(Mat4 modelview, Mat4 projection) {

    }

    public void updateUniform(Mat4 modelview, Mat4 projection, Material material) {

    }

    public int getProgram() {
        return program;
    }

    public String[] loadShader(String fileName) {
        ArrayList<String> shaderSource = new ArrayList<>();
        BufferedReader shaderReader = null;
        try {
            shaderReader = new BufferedReader(new FileReader("./res/shaders/" + fileName));
            String line;
            while ((line = shaderReader.readLine()) != null) {
                shaderSource.add(new String(line + "\n"));
            }

            shaderReader.close();
        } catch (Exception e) {
            System.out.println("Soubor s názvem" + " " + fileName + " " + " neexistuje!");

        }

        // nemùžu pøetypovat Object[] z toArray, tak proto tohle pole
        String[] result = new String[shaderSource.size()];
        return shaderSource.toArray(result);

    }

    public void setSubrutine(Subroutine subrutine) {
        this.subrutine = subrutine;
    }

}
