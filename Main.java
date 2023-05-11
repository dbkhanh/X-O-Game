package Game;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args){
        JPanel jPanel = new JPanel();
        BoxLayout boxLayout = new BoxLayout(jPanel, BoxLayout.Y_AXIS);
        jPanel.setLayout(boxLayout);

        Board board = new Board();
        board.setPreferredSize(new Dimension(300, 300));

        JPanel bottomPanel = new JPanel();
        bottomPanel.setPreferredSize(new Dimension(300, 50));
        bottomPanel.setBackground(Color.YELLOW);

        jPanel.add(board);
        jPanel.add(bottomPanel);
        //Box layout, Flow layout, Border layout
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();


        JFrame jFrame = new JFrame("Tic-Tac-Toe");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setResizable(true);
        jFrame.add(jPanel);

        int x = (int) (dimension.getWidth()/2 - jFrame.getWidth()/2);
        int y = (int) (dimension.getHeight()/2 - jFrame.getHeight()/2);

        jFrame.setLocation(x, y);

        jFrame.pack();
        jFrame.setVisible(true);
    }
}
