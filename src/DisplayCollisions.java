import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

@SuppressWarnings("serial")
public class DisplayCollisions extends JComponent {

    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private double height = screenSize.getHeight();
    private double width = screenSize.getWidth();

    private int windHeight;
    private int windWidth;

    private BufferedImage bi;
    private Graphics bg;

    private Blocks blocks;

    public DisplayCollisions(int width, int height, Blocks blocks) {

        bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        bg = bi.getGraphics();
        this.blocks = blocks;

        windHeight = height;
        windWidth = width;

        createAndShowGUI();
    }

    private void paintBlocks() {

        int bottom = windHeight - 50;
        int size1 = 30;
        int size2 = 90;

        bg.setColor(new Color(40, 180, 180));
        int wallAndFirstBlockWidth = 50 + size1;
        bg.fillRect((int)blocks.getPosition1() - size1 + wallAndFirstBlockWidth, bottom - size1, size1, size1);
        bg.fillRect((int)blocks.getPosition2() + wallAndFirstBlockWidth, bottom - size2, size2, size2);
    }

    private void paintBackground() {

        bg.setColor(new Color(40, 40, 40));
        bg.fillRect(0, 0, (int)width, (int)height);

        bg.setColor(new Color(20, 20, 20));
        bg.fillRect(0, windHeight - 50, windWidth, 50);
        bg.fillRect(0, 0, 50, windHeight);

        bg.setColor(new Color(223, 223, 223));
        bg.drawString(blocks.getNumCollisions() + "", 100, 100);
    }

    public void paintComponent(Graphics g) {

        blocks.step();

        paintBackground();
        paintBlocks();
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
