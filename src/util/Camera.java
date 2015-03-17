/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

/**
 *
 * @author Robin
 */
public class Camera {

    float posX = 0f, posY = 5f, posZ = 10f;
    float eyeX = 0f, eyeY = 0f, eyeZ = 0f;
    float cenX = 0f, cenY = 0f, cenZ = 0f;
    float upX = 0f, upY = 0f, upZ = 0f;
    float azimut = 0f;
    float zenit = 0f;
    int width, height;
    boolean fwd = false,bck = false,lft = false, rgt = false, up = false, dn = false;
    
    public Camera(int width, int height) {
        this.width = width;
        this.height = height;
    }
    
    
    public void update(double spd, double dx, double dy) {
        //nastavení zenithu a azimuthu
        zenit += (180 * dy / (float) height);
        if (zenit > 90) {
            zenit = 90;
        }
        if (zenit < -90) {
            zenit = -90;
        }
        azimut += (180 * dx / (float) width);
        azimut = azimut % 360;

        // vypoèty eye vectoru a up vectoru..
        eyeX = (float) (Math.sin(azimut * Math.PI / 180) * Math.cos(zenit * Math.PI / 180));
        eyeY = (float) (Math.sin(zenit * Math.PI / 180));
        eyeZ = -(float) (Math.cos(azimut * Math.PI / 180) * Math.cos(zenit * Math.PI / 180));

        upX = (float) (Math.sin(azimut * Math.PI / 180) * Math.cos(zenit * Math.PI / 180 + Math.PI
                / 2));
        upY = (float) (Math.sin(zenit * Math.PI / 180 + Math.PI / 2));
        upZ = -(float) (Math.cos(azimut * Math.PI / 180) * Math.cos(zenit * Math.PI / 180 + Math.PI
                / 2));

        if (fwd) {
            posX += (float) spd * eyeX;
            posY += (float) spd * eyeY;
            posZ += (float) spd * eyeZ;
           
        }
        if (bck) {
            posX += (float) -spd * eyeX;
            posY += (float) -spd * eyeY;
            posZ += (float) -spd * eyeZ;
        }
        if (lft) {
            posX += (float) spd * (Math.sin(azimut * Math.PI / 180 - Math.PI / 2));
            posZ -= (float) spd * (Math.cos(azimut * Math.PI / 180 - Math.PI / 2));
        }
        if (rgt) {
            posX -= (float) spd * (Math.sin(azimut * Math.PI / 180 - Math.PI / 2));
            posZ += (float) spd * (Math.cos(azimut * Math.PI / 180 - Math.PI / 2));
        }
        if (up) {
            posY += spd * 1;
        }
        if (dn) {
            posY -= spd * 1;
        }

        // vypoèet kam koukáme
        cenX = posX + eyeX;
        cenY = posY + eyeY;
        cenZ = posZ + eyeZ;
    }

    public boolean isFwd() {
        return fwd;
    }

    public void setFwd(boolean fwd) {
        this.fwd = fwd;
    }

    public boolean isBck() {
        return bck;
    }

    public void setBck(boolean bck) {
        this.bck = bck;
    }

    public boolean isLft() {
        return lft;
    }

    public void setLft(boolean lft) {
        this.lft = lft;
    }

    public boolean isRgt() {
        return rgt;
    }

    public void setRgt(boolean rgt) {
        this.rgt = rgt;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isDn() {
        return dn;
    }

    public void setDn(boolean dn) {
        this.dn = dn;
    }

    public float getPosX() {
        return posX;
    }

    public void setPosX(float posX) {
        this.posX = posX;
    }

    public float getPosY() {
        return posY;
    }

    public void setPosY(float posY) {
        this.posY = posY;
    }

    public float getPosZ() {
        return posZ;
    }

    public void setPosZ(float posZ) {
        this.posZ = posZ;
    }

    public float getEyeX() {
        return eyeX;
    }

    public void setEyeX(float eyeX) {
        this.eyeX = eyeX;
    }

    public float getEyeY() {
        return eyeY;
    }

    public void setEyeY(float eyeY) {
        this.eyeY = eyeY;
    }

    public float getEyeZ() {
        return eyeZ;
    }

    public void setEyeZ(float eyeZ) {
        this.eyeZ = eyeZ;
    }

    public float getCenX() {
        return cenX;
    }

    public void setCenX(float cenX) {
        this.cenX = cenX;
    }

    public float getCenY() {
        return cenY;
    }

    public void setCenY(float cenY) {
        this.cenY = cenY;
    }

    public float getCenZ() {
        return cenZ;
    }

    public void setCenZ(float cenZ) {
        this.cenZ = cenZ;
    }

    public float getUpX() {
        return upX;
    }

    public void setUpX(float upX) {
        this.upX = upX;
    }

    public float getUpY() {
        return upY;
    }

    public void setUpY(float upY) {
        this.upY = upY;
    }

    public float getUpZ() {
        return upZ;
    }

    public void setUpZ(float upZ) {
        this.upZ = upZ;
    }

    public float getAzimut() {
        return azimut;
    }

    public void setAzimut(float azimut) {
        this.azimut = azimut;
    }

    public float getZenit() {
        return zenit;
    }

    public void setZenit(float zenit) {
        this.zenit = zenit;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
    
}
