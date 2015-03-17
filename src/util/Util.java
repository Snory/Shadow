/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL2;

import transforms.Mat4;
import transforms.Vec3D;

/**
 *
 * @author Robin
 */
public class Util {

    public static final int SIZEOF_BYTE = 1;
    public static final int SIZEOF_SHORT = 2;
    public static final int SIZEOF_CHAR = 2;
    public static final int SIZEOF_INT = 4;
    public static final int SIZEOF_FLOAT = 4;
    public static final int SIZEOF_LONG = 8;
    public static final int SIZEOF_DOUBLE = 8;

    public static float[] toFloatArray(List<Float> list) {
    	
    	float[] floatArray = new float[list.size()];
        for (int i = 0; i < list.size(); i++) {
            floatArray[i] = list.get(i).floatValue();
        }
        return floatArray;
    }

    public static int[] toIntArray(List<Integer> list) {
        int[] intArray = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            intArray[i] = list.get(i).intValue();
        }

        return intArray;
    }

    /**
     * metoda mi pøevede matici 4x4 na floatové pole (kvuli poslání do shaderu )
     *
     * @param mat
     * @return
     */
    public static float[] convert(Mat4 mat) {
        float[] result = new float[16];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                result[i * 4 + j] = ((float) mat.mat[i][j]);
            }
        }
        return result;
    }

    /**
     * metoda mi pøevede vektor  na floatové pole (kvuli poslání do shaderu )
     *
     * @param mat
     * @return
     */
    public static float[] convert(Vec3D vec) {
        float[] result = new float[3];
        result[0] = (float) vec.x;
        result[1] = (float) vec.y;
        result[2] = (float) vec.z;
        return result;
    }

    public static String[] removeEmptyString(String[] data) {
        ArrayList<String> result = new ArrayList<>();

        for (int i = 0; i < data.length; i++) {
            if (!data[i].equals("")) {
                result.add(data[i]);
            }
        }

        String[] res = new String[result.size()];
        return result.toArray(res);
    }

}
