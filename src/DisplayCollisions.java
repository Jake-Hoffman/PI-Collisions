import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

public class DisplayCollisions extends JComponent {

    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private double height = screenSize.getHeight();
    private double width = screenSize.getWidth();

    private int windHeigth;
    private int windWidth;

    BufferedImage bi;
    Graphics bg;

    Blocks blocks;

    public DisplayCollisions(int width, int height, Blocks blocks) {
        bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        bg = bi.getGraphics();
        this.blocks = blocks;

        windHeigth = height;
        windWidth = width;

        createAndShowGUI();
    }

    public void paintBlocks(Graphics g) {
        int bottom = windHeigth - 50;
        int size1 = blocks.getSize1();
        int size2 = blocks.getSize2();

        g.setColor(Color.CYAN);
        g.fillRect((int)blocks.getPosition1() - size1, bottom - size1, size1, size1);
        g.fillRect((int)blocks.getPosition2(), bottom - size2, size2, size2);
    }

    public void paintComponent(Graphics g) {
        blocks.step();

        bg.setColor(Color.BLACK);
        bg.fillRect(0, 0, (int)width, (int)height);
        bg.setColor(Color.WHITE);
        bg.fillRect(0, windHeigth - 50, windWidth, 50);
        bg.fillRect(0, 0, 50, windHeigth);

        paintBlocks(bg);
        bg.drawString(blocks.getCounter() + "", 100, 100);
        bg.drawString(blocks.getPosition1() + "", 100, 113);
        bg.drawString(blocks.getPosition2() + "", 100, 126);
        bg.drawString(String.format("%4.2f", blocks.getTime()), 100, 139);

        g.drawImage(bi, 0, 0, null);


        repaint();
    }

    public Dimension getPreferredSize() {

        try {
            return new Dimension(1000, 500);
        } catch (NullPointerException e) {
            return new Dimension(1000,1000);
        }
    }

    public void createAndShowGUI(){

        JFrame f = new JFrame("Collisions");

        f.addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        f.add(this);
        f.pack();
        f.setVisible(true);
        f.setFocusable(true);

    }
}
