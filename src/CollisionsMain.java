public class CollisionsMain {

    public static void main (String[] args){

        Blocks blocks  = new Blocks(1,1000000000,0,1,30,90,250,500);

        new DisplayCollisions(1000, 500, blocks);
    }
}
