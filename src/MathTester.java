public class MathTester {

    public static void main(String[] args) {
        Blocks blocks  = new Blocks(2,2,0,40,30,30,100,500);

        for ( double c : blocks.collisionsList) {
            System.out.println(c);
        }

        blocks.step();
        double[] i = blocks.findIntersection(1.0/0, 0, 0, 5);
        System.out.println("x: " + i[0] + "\ny: " + i[1]);
    }



}
