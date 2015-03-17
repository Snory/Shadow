/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scene.model;

import com.jogamp.common.nio.Buffers;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;
import javax.media.opengl.GL2;
import transforms.Vec3D;
import util.Util;

/**
 *
 * @author Robin
 */
public class Model {

    List<Float> vertices;
    List<Integer> indices;
    int[] vertexBuffer = new int[1], indexBuffer = new int[1];
    GL2 gl;
    private List<Attribute> attributes;
    int size;
    int attSize;
    Material m;
    public Model(GL2 gl) {
        this.gl = gl;
        init();
    }

    public void init() {
        gl.glGenBuffers(1, vertexBuffer, 0);
        gl.glGenBuffers(1, indexBuffer, 0);
        attributes = new ArrayList<>();
        m = new Material(new Vec3D(1, 0, 0));
        addAttribute("inPosition", 3);
        addAttribute("inTexture", 2);
        addAttribute("inNormal", 3);
        
        attributes.get(0).setLocation(0);
        attributes.get(1).setLocation(1);
        attributes.get(2).setLocation(2);
    }

    public void addFloatVertexData(float[] vertexBufferData) {
        size = vertexBufferData.length / 6;
        gl.glBindBuffer(GL2.GL_ARRAY_BUFFER, vertexBuffer[0]);
        FloatBuffer vertexBufferBuffer = Buffers.newDirectFloatBuffer(vertexBufferData);
        // pøepoèítat na byty (float ma 4)
        gl.glBufferData(GL2.GL_ARRAY_BUFFER, vertexBufferData.length * Util.SIZEOF_FLOAT,
                vertexBufferBuffer, GL2.GL_STATIC_DRAW); // staticke
        // vykreslovani(nemìní
        // se
        // data)
    }

    public void addIntegerIndexData(int[] indexBufferData) {
        size = indexBufferData.length;
        gl.glBindBuffer(GL2.GL_ELEMENT_ARRAY_BUFFER, indexBuffer[0]);
        IntBuffer vertexBufferBuffer = Buffers.newDirectIntBuffer(indexBufferData);
        // pøepoèítat na byty (short ma 2)
        gl.glBufferData(GL2.GL_ELEMENT_ARRAY_BUFFER, indexBufferData.length * Util.SIZEOF_INT,
                vertexBufferBuffer, GL2.GL_STATIC_DRAW);// staticke
        // vykreslovani(nemìní
        // se
        // data)
    }

    public void bind(int program) {
        int stride = 0;
        gl.glBindBuffer(GL2.GL_ARRAY_BUFFER, vertexBuffer[0]);
        for (int i = 0; i < attributes.size(); i++) {
            gl.glVertexAttribPointer(attributes.get(i).getLocation(), 3, GL2.GL_FLOAT, false, attSize * Util.SIZEOF_FLOAT,
                    stride);
            stride += attributes.get(i).getSize() * Util.SIZEOF_FLOAT;
            gl.glEnableVertexAttribArray(i);
        }
        gl.glBindBuffer(GL2.GL_ELEMENT_ARRAY_BUFFER, indexBuffer[0]);
    }

    public void addAttribute(String name, int size) {
        Attribute attribute = new Attribute(name, size);
        attributes.add(attribute);
        attSize += attribute.getSize();
    }

    public void draw(int type) {
        gl.glDrawElements(type, size, GL2.GL_UNSIGNED_INT, 0);
        gl.glDisableVertexAttribArray(0);
        gl.glDisableVertexAttribArray(1);
        gl.glDisableVertexAttribArray(2);
    }

    public void setIndices(List<Integer> indices) {
        this.indices = indices;
        addIntegerIndexData(Util.toIntArray(indices));
    }

    public void setVertices(List<Float> vertices) {
        this.vertices = vertices;
        addFloatVertexData(Util.toFloatArray(vertices));
    }

    public class Attribute {

        String name;
        int size;
        int location;

        public Attribute(String name, int size) {
            this.name = name;
            this.size = size;
        }

        public String getName() {
            return name;
        }

        public int getSize() {
            return size;
        }

        public void setLocation(int location) {
            this.location = location;
        }

        public int getLocation() {
            return location;
        }
        
    }

    public Material getM() {
        return m;
    }

    public void setM(Material m) {
        this.m = m;
    }
    
    

}
