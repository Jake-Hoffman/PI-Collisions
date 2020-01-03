public class MathTester {

    public static void main(String[] args) {
        Blocks blocks  = new Blocks(2,2,0,40,30,30,100,500);

        double[][] answer = CollisionMath.multiplyMatrices(new double[][]{{1, 300}}, CollisionMath.getRotationMatrix(Math.PI / 2));

        while (true) {
            blocks.step();

            System.out.println();
            System.out.println("Position 1:" + blocks.getPosition1());
            System.out.println("Position 2:" + blocks.getPosition2());
        }
    }



}
