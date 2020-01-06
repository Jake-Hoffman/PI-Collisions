public class Blocks {

    private double mass1;
    private double mass2;

    private double position1;
    private double position2;

    private double originalPos1;
    private double originalPos2;

    private long numCollisions = 0;

    private double angle;

    private double startTime;
    private double tBounds;

    public Blocks(double mass1, double mass2, double position1, double position2) {

        this.mass1 = mass1;
        this.mass2 = mass2;

        this.position1 = position1;
        this.position2 = position2;

        originalPos1 = position1;
        originalPos2 = position2;

        angle = Math.atan(Math.sqrt(mass1/mass2));

        startTime = System.currentTimeMillis()/1000.0;
        tBounds = originalPos2 * Math.sqrt(mass2);
    }


    public void step(){

        double time = System.currentTimeMillis() / 1000.0;

        double animationDuration = 5;
        double t = (((animationDuration / 2) - (time - startTime)) / (animationDuration / 2)) * tBounds;

        numCollisions = (long)(angleAtTime(t)/angle);

        double[] positions = getPositions(t);
        position1 = positions[0];
        position2 = positions[1];
    }

    private double[] getPositions(double t) {

        double[][] positions = multiplyMatrices(new double[][]{{originalPos1, t}}, getRotationMatrix(-getRotationAngle(t)));

        if ((angleAtTime(t) + angle) % (2 * angle) < angle) {
            positions[0][0] = Math.abs(positions[0][0]);
        }

        positions[0][0] = positions[0][0] / Math.sqrt(mass1);
        positions[0][1] = positions[0][1] / Math.sqrt(mass2);

        return positions[0];
    }

    private double getRotationAngle(double t) {
        return (int)((angleAtTime(t) + angle) / (2 * angle)) * (2 * angle);
    }

    private double angleAtTime(double t) {

        double tempAngle = Math.atan(originalPos1/ t);

        if (t == 0) {
            return Math.PI/2;
        }

        if (tempAngle < 0) {
            return Math.PI + tempAngle;
        } else {
            return tempAngle;
        }
    }

    public static double[][] getRotationMatrix(double angleRad) {
        return new double[][]{{Math.cos(angleRad), -Math.sin(angleRad)}, {Math.sin(angleRad), Math.cos(angleRad)}};
    }

    public static double[][] multiplyMatrices (double[][] m1, double[][] m2) {
        int r1 = m1.length;
        int c2 = m2[0].length;

        double[][] m3 = new double[r1][c2];

        for(int xx = 0; xx < r1; xx++) {
            for (int yy = 0; yy < c2; yy++) {
                for (int zz = 0; zz < m1[0].length; zz++) {
                    m3[xx][yy] += m1[xx][zz] * m2[zz][yy];
                }
            }
        }

        return m3;
    }

    public double getPosition1() {
        return position1;
    }

    public double getPosition2() {
        return position2;
    }

    public long getNumCollisions() {
        return numCollisions;
    }

    public String toString() {
        return ("Mass 1: " + mass1 + "\n" + "Mass 2: " + mass2 + "\n" + "Vel 1: " + "\n" + "Pos 1: " + position1 + "\n" + "Pos 2: " + position2 + "\n");
    }
}
