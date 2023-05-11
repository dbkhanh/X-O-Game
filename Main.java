package Game;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args){
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();

        Board board = new Board();
        JFrame jFrame = new JFrame("Tic-Tac-Toe");
        jFrame.setSize(600, 600);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setResizable(false);
        jFrame.add(board);

        int x = (int) (dimension.getWidth()/2 - jFrame.getWidth()/2);
        int y = (int) (dimension.getHeight()/2 - jFrame.getHeight()/2);

        jFrame.setLocation(x, y);
        jFrame.setVisible(true);
    }
}
