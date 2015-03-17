/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import scene.model.Model;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.media.opengl.GL2;
import transforms.Vec2D;
import transforms.Vec3D;
import util.Util;

/**
 *
 * @author Robin
 */
public class WaveFrontParser {

    int numberOfObject;
    List<Vec3D> positionCoordinates;
    List<Vec3D> normalCoordinates;
    List<Vec2D> textureCoordinates;
    List<Index> indices;
    HashMap<String, Model> models;
    GL2 gl;
    boolean first;

    public WaveFrontParser(HashMap<String, Model> models, GL2 gl) {
        this.models = models;
        this.gl = gl;
    }

    public void loadOBJ(String fileName) {
        first = false;
        parse(fileName);
    }

    public void parse(String fileName) {
        int positionIndex = 0;
        int normalIndex = 0;
        int textureIndex = 0;
        BufferedReader objReader = null;
        try {
            objReader = new BufferedReader(new FileReader("./res/models/" + fileName));
            String line;
            while ((line = objReader.readLine()) != null) {
                String[] tokens = line.split(" ");
                tokens = Util.removeEmptyString(tokens);
                if (tokens.length == 0 || tokens[0].equals("#")) {
                    continue; // vyskoèí z ifu, ale ne z while
                } else if (tokens[0].equals("o")) {
                    if (first) {
                        createVBandIB(positionCoordinates, textureCoordinates, normalCoordinates, indices);
                    }
                    if (positionCoordinates != null) {
                        positionIndex += positionCoordinates.size();
                        textureIndex += textureCoordinates.size();
                        normalIndex += normalCoordinates.size();
                    }
                    positionCoordinates = new ArrayList<>();
                    textureCoordinates = new ArrayList<>();
                    normalCoordinates = new ArrayList<>();
                    indices = new ArrayList<>();
                } // vrcholy
                else if (tokens[0].equals("v")) {
                    first = true;
                    positionCoordinates.add(new Vec3D(Float.parseFloat(tokens[1]), Float.parseFloat(tokens[2]), Float.parseFloat(tokens[3])));
                } // souøadnice do textury
                else if (tokens[0].equals("vt")) {
                    textureCoordinates.add(new Vec2D(Float.parseFloat(tokens[1]), Float.parseFloat(tokens[2])));
                } // normály
                else if (tokens[0].equals("vn")) {
                    normalCoordinates.add(new Vec3D(Float.parseFloat(tokens[1]), Float.parseFloat(tokens[2]), Float.parseFloat(tokens[3])));
                } else if (tokens[0].equals("f")) {
                    String[] index1 = tokens[1].split("/");
                    String[] index2 = tokens[2].split("/");
                    String[] index3 = tokens[3].split("/");
                    indices.add(new Index(Integer.parseInt(index1[0]) - positionIndex - 1, Integer.parseInt(index1[2]) - normalIndex - 1, Integer.parseInt(index1[1]) - textureIndex - 1));
                    indices.add(new Index(Integer.parseInt(index2[0]) - positionIndex - 1, Integer.parseInt(index2[2]) - normalIndex - 1, Integer.parseInt(index2[1]) - textureIndex - 1));
                    indices.add(new Index(Integer.parseInt(index3[0]) - positionIndex - 1, Integer.parseInt(index3[2]) - normalIndex - 1, Integer.parseInt(index3[1]) - textureIndex - 1));
                }
                // if podmínka
            } // while podmínka
            objReader.close();
            createVBandIB(positionCoordinates, textureCoordinates, normalCoordinates, indices);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    int count = 0;

    public void createVBandIB(List<Vec3D> position, List<Vec2D> texture, List<Vec3D> normal, List<Index> indices) {
        List<Float> vb = new ArrayList<>();
        List<Integer> ib = new ArrayList<>();
        for (int i = 0; i < indices.size(); i++) {
            vb.add((float) position.get(indices.get(i).position).x);
            vb.add((float) position.get(indices.get(i).position).y);
            vb.add((float) position.get(indices.get(i).position).z);
            vb.add((float) texture.get(indices.get(i).texture).x);
            vb.add((float) texture.get(indices.get(i).texture).y);
            vb.add((float) normal.get(indices.get(i).normal).x);
            vb.add((float) normal.get(indices.get(i).normal).y);
            vb.add((float) normal.get(indices.get(i).normal).z);
            ib.add(i);
        }
        Model m = new Model(gl);
        m.setIndices(ib);
        m.setVertices(vb);
        models.put("Test" + count, m);
        count++;
    }

    public class Index {
        int position;
        int normal;
        int texture;
        public Index() {
        }
        public Index(int position, int normal, int texture) {
            this.position = position;
            this.normal = normal;
            this.texture = texture;
        }
    }
}
