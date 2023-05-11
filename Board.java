package Game;

import javax.swing.*;
import java.awt.*;
public class Board extends JPanel {
    public void paint(Graphics g){
        int w = getWidth()/3;
        int h = getHeight()/3;
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setColor(Color.BLUE);
        graphics2D.fillRect(0,0, w, h);
    }
}
