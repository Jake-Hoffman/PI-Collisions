public class CollisionsMain {

    public static void main (String[] args){

        Blocks blocks  = new Blocks(2,2,0,40,30,30,100,500);
        new DisplayCollisions(1000, 500, blocks);
    }
}
