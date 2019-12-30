public class CollisionsMain {

    public static void main (String[] args){

        Blocks blocks  = new Blocks(2,2,0,10,30,30,300,400);
        new DisplayCollisions(1000, 500, blocks);
    }
}
