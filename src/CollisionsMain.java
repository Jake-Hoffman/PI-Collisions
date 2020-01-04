public class CollisionsMain {

    public static void main (String[] args){

        Blocks blocks  = new Blocks(1,10000000000000000.0,400,600);

        new DisplayCollisions(1000, 500, blocks);
    }
}
