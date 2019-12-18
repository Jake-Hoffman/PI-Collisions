public class Blocks {

    private double time;

    private double mass1;
    private double mass2;

    private double velocity1;
    private double velocity2;

    private int size1;
    private int size2;

    private double position1;
    private double position2;

    private double cPos1O;
    private double cPos2O;
    private double cPos1;
    private double cPos2;

    private double nextCollisionTime;

    private double INTERVAL = 1.00/70;
    private int counter = 0;

    private double angle;
    private int numCollisions;
    public double[] collisionsList;

    private double startTime;

    public Blocks(double mass1, double mass2, double velocity1, double velocity2, int size1, int size2,
                  double position1, double position2) {

        this.mass1 = mass1;
        this.mass2 = mass2;
        this.velocity1 = velocity1; //Pixels Per Second
        this.velocity2 = velocity2; //Pixels Per Second
        this.size1 = size1;
        this.size2 = size2;
        this.cPos1O = position1 * Math.sqrt(mass1);
        this.cPos2O = position2 * Math.sqrt(mass1);
        this.position1 = position1;
        this.position2 = position2;
        this.angle = Math.atan(Math.sqrt(mass1/mass2));
        this.numCollisions = (int)(Math.PI/angle);

        if ((Math.PI/angle) - (int)(Math.PI/angle) == 0) {
            this.numCollisions--;
        }

        this.collisionsList = new double[numCollisions + 1];
        this.startTime = System.currentTimeMillis()/1000.0;

        getCollisionsList();
    }


    public void step(){

        //System.out.println(toString());

/*
        double now = System.currentTimeMillis() / 1000.0;
        double interval = now - time;

        while (interval < INTERVAL) {
            interval = (System.currentTimeMillis() / 1000.0) - now;
        }
*/
        //setPositions(INTERVAL);
        //checkCollision();

        time = System.currentTimeMillis() / 1000.0;


        getPositions((time - startTime)*velocity2);
    }

    private void setPositions(double interval) {

        System.out.println(interval);
        double tempPos1 = position1 + (interval * velocity1);
        double tempVel1 = velocity1;
        boolean countIt = false;

        if (tempPos1 < 50){
            countIt = true;
            tempPos1 = 100 - tempPos1;
            tempVel1 = -tempVel1;
        }

        double tempPos2 = position2 + (interval * velocity2);

        System.out.println("TempP1: " + tempPos1);
        System.out.println("TempP2: " + tempPos2);

        if (tempPos1 + size1 > tempPos2){

            double collTime = (position2 - (position1 + size1))/(velocity1 - velocity2);

            if (collTime == interval) {
                calcCollision();
            } else {
                System.out.println(toString());
                System.out.println("CollTime: " + collTime);
                setPositions(collTime);
                calcCollision();
                counter++;
                setPositions(interval - collTime);
            }



        } else {
            if (countIt){
                counter++;
            }
            position1 = tempPos1;
            velocity1 = tempVel1;
            position2 = tempPos2;
        }

    }

    private void checkCollision() {

        if (position1 + size1 > position2){

        }
    }

    private void calcCollision(){


        double newVel1 = (((mass1 - mass2)/(mass1 + mass2)) * velocity1) + (((2 * mass2)/(mass1 + mass2)) * velocity2);
        double newVel2 = (((2 * mass1)/(mass1 + mass2)) * velocity1) + (((mass2 - mass1)/(mass1 + mass2)) * velocity2);


        velocity1 = newVel1;
        velocity2 = newVel2;

        System.out.println(toString());
    }

    public void getPositions2(double time) {

    }

    public void getPositions(double time) {

        //time += cPos1O;
        int xx = 0;
        while (cPos2O - time  < collisionsList[xx]){
            xx++;
        }

        System.out.println(time);
        System.out.println(xx);

        double[] p1 = new double[]{0, 0};
        double[] p2 = new double[]{cPos1O, time};
        double[] p3;

        if (xx % 2 == 1) {
            xx++;
        }

        double[][] m = multiplyMatrices(getRotationMatrix(angle * xx), new double[][]{{0}, {1}});
        double slope = (m[0][0]/m[1][0]);


        p3 = findIntersection(-1.0 / slope, slope, 0, -slope * cPos1O + time);
        System.out.println("x: " + p3[0] + "\ny: " + p3[1]);

        cPos1 = pointsDistance(p2, p3);
        cPos2 = pointsDistance(p1, p3);

        position1 = cPos1 / Math.sqrt(mass1);
        position2 = cPos2 / Math.sqrt(mass2);

    }

    public static double pointsDistance(double[] p1, double[] p2) {
        return Math.sqrt((p1[0] - p2[0])*(p1[0] - p2[0]) + (p1[1] + p2[1])*(p1[1] + p2[1]));
    }

    private double[] collisionPosition(int collisionNum) {

        double[][] m1 = multiplyMatrices(getRotationMatrix(angle * collisionNum), new double[][]{{0}, {1}});

        double x = cPos1O;
        double y = -(m1[1][0]/m1[0][0]) * x;

        return new double[]{x, y};
    }

    private void getCollisionsList() {

        int xx;

        for (xx = 0; xx < numCollisions; xx++) {
            collisionsList[xx] = collisionPosition(xx + 1)[1];
        }

        collisionsList[xx] = -Double.MAX_VALUE / velocity2 - 10;
    }

    public double[] findIntersection(double m1, double m2, double b1, double b2) {

        if (m1 == m2) {
            throw (new IllegalArgumentException("Parallel slopes"));
        }

        if ( Double.isInfinite(m1) && !Double.isInfinite(m2)) {
            return new double[]{b1, b1 * m2 + b2};
        } else if ( Double.isInfinite(m2) && !Double.isInfinite(m1)) {
            return new double[]{b2, b2 * m1 + b1};
        } else if ( Double.isInfinite(m1) && Double.isInfinite(m2)) {
            throw (new IllegalArgumentException("Two Infinite slopes"));
        }

        double x = (b2 - b1)/(m1 - m2);
        double y = x * m1 + b1;

        return new double[]{x, y};
    }

    private static double[][] getRotationMatrix(double angleRad) {
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

    public int getSize1() {
        return size1;
    }

    public int getSize2() {
        return size2;
    }

    public int getCounter() {
        return counter;
    }

    public double getTime() {
        return time - startTime;
    }

    public String toString() {
        return ("Time: " + time + "\n" + "Mass 1: " + mass1 + "\n" + "Mass 2: " + mass2 + "\n" + "Vel 1: " + velocity1 + "\n" +
                "Vel 2: " + velocity2 + "\n" + "Pos 1: " + position1 + "\n" + "Pos 2: " + position2 + "\n" + "TTNC: " + nextCollisionTime + "\n");
    }
}
