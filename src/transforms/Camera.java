package transforms;

/**
 * Trida pro nastaveni pohledove transformace
 *
 * PGRF 2012
 */
public class Camera {

    double azimuth, radius, zenith;

    boolean firstPerson; // true -> 1st person, 0 -> 3rd person

    Vec3D eye, eyeVector, pos;
    Vec3D up;
    Mat4 view;

    void computeMatrix() {
        eyeVector = new Vec3D((double) (Math.cos(azimuth) * Math.cos(zenith)),
                (double) (Math.sin(azimuth) * Math.cos(zenith)), (double) Math
                .sin(zenith));
        if (firstPerson) {
            eye = new Vec3D(pos);
            up = new Vec3D(
                    (double) (Math.cos(azimuth) * Math
                    .cos(zenith + Math.PI / 2)), (double) (Math
                    .sin(azimuth) * Math.cos(zenith + Math.PI / 2)),
                    (double) Math.sin(zenith + Math.PI / 2));
            view = new Mat4ViewRH(pos, eyeVector.mul(radius), up);
        } else {
            eye = pos.add(eyeVector.mul(-1 * radius));
            up = new Vec3D(
                    (double) (Math.cos(azimuth) * Math
                    .cos(zenith + Math.PI / 2)), (double) (Math
                    .sin(azimuth) * Math.cos(zenith + Math.PI / 2)),
                    (double) Math.sin(zenith + Math.PI / 2));
            view = new Mat4ViewRH(eye, eyeVector.mul(radius), up);
        }
    }

    public Camera() {
        azimuth = zenith = 0.0f;
        radius = 1.0f;
        pos = new Vec3D(0.0f, 0.0f, 0.0f);
        firstPerson = true;
        computeMatrix();
    }

    public void addAzimuth(double ang) {
        azimuth += ang;
        computeMatrix();
    }

    public void addRadius(double dist) {
        if (radius + dist < 0.1f) {
            return;
        }
        radius += dist;
        computeMatrix();
    }

    public void mulRadius(double scale) {
        if (radius * scale < 0.1f) {
            return;
        }
        radius *= scale;
        computeMatrix();
    }

    public void addZenith(double ang) {
        if (Math.abs(zenith + ang) <= Math.PI / 2) {
            zenith += ang;
            computeMatrix();
        }
    }

    public void setAzimuth(double ang) {
        azimuth = ang;
        computeMatrix();
    }

    public void setRadius(double dist) {
        radius = dist;
        computeMatrix();
    }

    public void setZenith(double ang) {
        zenith = ang;
        computeMatrix();
    }

    public void backward(double speed) {
        forward((-1) * speed);
    }

    public void forward(double speed) {
        pos = pos.add(new Vec3D(
                (double) (Math.cos(azimuth) * Math.cos(zenith)), (double) (Math
                .sin(azimuth) * Math.cos(zenith)), (double) Math
                .sin(zenith)).mul(speed));
        computeMatrix();
    }

    public void left(double speed) {
        right((-1) * speed);
    }

    public void right(double speed) {
        pos = pos.add(new Vec3D((double) Math.cos(azimuth - Math.PI / 2),
                (double) Math.sin(azimuth - Math.PI / 2), 0.0f).mul(speed));
        computeMatrix();

    }

    public void down(double speed) {
        pos.z -= speed;
        computeMatrix();
    }

    public void up(double speed) {
        pos.z += speed;
        computeMatrix();
    }

    public void move(Vec3D dir) {
        pos = pos.add(dir);
        computeMatrix();
    }

    public void mouseMoved(double dx, double dy, int height, int width) {
        addAzimuth((double) Math.PI * (dx)
                / width);
        addZenith((double) Math.PI * (dy)
                / width);
    }

    public void setPosition(Vec3D apos) {
        pos = new Vec3D(apos);
        computeMatrix();
    }

    public boolean getFirstPerson() {
        return firstPerson;
    }

    public void setFirstPerson(boolean fp) {
        firstPerson = fp;
        computeMatrix();
    }

    public Vec3D getEye() {
        return eye;
    }

    public Vec3D getEyeVector() {
        return eyeVector;
    }

    public Vec3D getPosition() {
        return pos;
    }

    public Mat4 getViewMatrix() {
        return view;
    }

    public Mat4 getLookAtMatrix() {

        Mat4 lookAt = new Mat4();
        Mat4 trans = new Mat4();
        Vec3D eye = pos;
        Vec3D cen = eyeVector;
        Vec3D up = this.up;
        
        Vec3D L = new Vec3D(cen.x-pos.x, cen.y - pos.y, cen.z - pos.z);
        L.normalized();
        Vec3D S = L.cross(up);
        S.normalized();
        Vec3D U = S.cross(L);
        //radek sloupec
        lookAt.mat[0][0] = S.x;
        lookAt.mat[1][0] = S.y;
        lookAt.mat[2][0] = S.z;
        lookAt.mat[2][0] = 0.0;
                
        
        lookAt.mat[0][1] = U.x;
        lookAt.mat[1][1] =U.y;
        lookAt.mat[2][1] =U.z;
        lookAt.mat[3][1] =0.0;
                
        
        lookAt.mat[0][2] = L.x;
        lookAt.mat[1][2] =  L.y;
        lookAt.mat[2][2] =L.z;
        lookAt.mat[3][2] = 0;

        
        lookAt.mat[0][3] = 0;
        lookAt.mat[1][3] = 0;
        lookAt.mat[2][3] = 0;
        lookAt.mat[3][3] = 1;   
        
        
        
        trans.mat[0][0] = 1;
        trans.mat[1][0] = 0;
        trans.mat[2][0] = 0;
        trans.mat[2][0] = -eye.x;
                
        
        trans.mat[0][1] = 0;
        trans.mat[1][1] =1;
        trans.mat[2][1] =0;
        trans.mat[3][1] =-eye.y;
                
        
        trans.mat[0][2] = 0;
        trans.mat[1][2] =  0;
        trans.mat[2][2] =1;
        trans.mat[3][2] = -eye.z;

        
        trans.mat[0][3] = 0;
        trans.mat[1][3] = 0;
        trans.mat[2][3] = 0;
        trans.mat[3][3] = 1;   
                        
        return lookAt.mul(trans).mul(getViewMatrix());
    }

    public double getAzimuth() {
        return azimuth;
    }

    public double getZenith() {
        return zenith;
    }
}
