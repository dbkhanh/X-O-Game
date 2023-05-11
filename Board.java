package Game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Board extends JPanel {
    private static final int N = 3;
    private static final int M = 3;
    private Image imgX;
    private Image imgO;

    public Board(){
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                int x = e.getX();
                int y = e.getY();
            }
        });

        try {
            imgX = ImageIO.read(getClass().getResource("x.png"));
            imgO = ImageIO.read(getClass().getResource("o.png"));
        }catch(Exception e) {
            e.printStackTrace();
        }
    }
    public void paint(Graphics g){
        int w = getWidth()/3;
        int h = getHeight()/3;
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setColor(Color.BLUE);

        int k = 0;
        for(int i = 0; i < N; i++){
            for(int j = 0; j < M; j++){
                int x = i * w;
                int y = j * h;

                Color color = k%2 == 0? Color.BLUE: Color.RED;
                graphics2D.setColor(color);
                graphics2D.fillRect(x,y,w,h);

                Image img = k%2 == 0? imgX: imgO;
                graphics2D.drawImage(img,x,y,w-1,h-1, this);
                k++;
            }
        }
    }
}
