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

    private double originalPos1;
    private double originalPos2;

    private double INTERVAL = 1.00/70;
    private int counter = 0;

    private double angle;
    private double timeSlope;
    private int numCollisions;

    private double startTime;
    private double tBounds;

    public Blocks(double mass1, double mass2, double velocity1, double velocity2, int size1, int size2,
                  double position1, double position2) {

        this.mass1 = mass1;
        this.mass2 = mass2;

        this.velocity1 = velocity1; //Pixels Per Second
        this.velocity2 = velocity2; //Pixels Per Second

        this.size1 = size1;
        this.size2 = size2;

        this.position1 = position1;
        this.position2 = position2;

        this.originalPos1 = position1;
        this.originalPos2 = position2;

        this.angle = Math.atan(Math.sqrt(mass1/mass2));
        System.out.println("Angle: " + angle);

        this.timeSlope = velocity1/velocity2;
        this.numCollisions = (int)(Math.PI/angle);

        if ((Math.PI/angle) - (int)(Math.PI/angle) == 0) {
            this.numCollisions--;
        }

        this.tBounds = getInitialTime();
    }


    public void step(){

        time = System.currentTimeMillis() / 1000.0;

        double animationDuration = 5;

        double t = (((animationDuration / 2) - (time - startTime)) / (animationDuration / 2)) * tBounds;

        double[] positions = getPositions(t);
        position1 = positions[0];
        position2 = positions[1];
    }

    private double getInitialTime() {

        double t = 0;

        while (getPositions(t)[1] - (originalPos2) < 1) {
            System.out.println(getPositions(t)[1]);
            t++;
        }

        System.out.println("Initial time: " + t);

        startTime = System.currentTimeMillis()/1000.0;

        return t;
    }

    private double[] getPositions(double t) {

        double[][] positions = CollisionMath.multiplyMatrices(new double[][]{{timeFunction(t), t}}, CollisionMath.getRotationMatrix(-getRotationAngle(t)));

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

    private double timeFunction(double t) {
        return timeSlope * t - timeSlope * originalPos2 + originalPos1;
    }

    private double angleAtTime(double t) {

        double tempAngle = Math.atan(timeFunction(t)/ t);

        if (t == 0) {
            return Math.PI/2;
        }

        if (tempAngle < 0) {
            return Math.PI + tempAngle;
        } else {
            return tempAngle;
        }
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
                "Vel 2: " + velocity2 + "\n" + "Pos 1: " + position1 + "\n" + "Pos 2: " + position2 + "\n");
    }
}
