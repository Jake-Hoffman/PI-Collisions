public class CollisionsMain {

    public static void main (String[] args){
        //Max Value: 1000000000000000000
        Blocks blocks  = new Blocks(1,10000,200,700);

        new DisplayCollisions(1000, 500, blocks);
    }
}
