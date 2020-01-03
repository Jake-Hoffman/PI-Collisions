public class CollisionMath {

    public CollisionMath () {
    }

    public static double pointsDistance(double[] p1, double[] p2) {
        return Math.sqrt((p1[0] - p2[0])*(p1[0] - p2[0]) + (p1[1] + p2[1])*(p1[1] + p2[1]));
    }

    public static double[] findIntersection(double m1, double m2, double b1, double b2) {

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
}
