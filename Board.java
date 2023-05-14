package Game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class Board extends JPanel {
    private static final int N = 3;
    private static final int M = 3;

    public static final int DRAW = 0;
    public static final int WIN = 1;
    public static final int NORMAL = 2;
    private Image imgX;
    private Image imgO;
    private Cell[][] matrix = new Cell[N][M];
    private String currentPlayer = Cell.EMPTY_VALUE;
    private EndGameListener endGameListener;
    private static final String AI_PLAYER = "AI";

    public void makeAIMove(String currentPlayer) {
        // Generate a random move for the AI player
        Random random = new Random();
        int row, col;

        do {
            row = random.nextInt(N);
            col = random.nextInt(M);
        } while (!matrix[row][col].getValue().equals(Cell.EMPTY_VALUE));

        // Set the AI player's move on the board
        matrix[row][col].setValue(currentPlayer);
        repaint();

        // Check for game result
        int result = checkWin(currentPlayer);
        if (result == Board.NORMAL) {
            currentPlayer = (currentPlayer.equals(Cell.X_VALUE)) ? Cell.O_VALUE : Cell.X_VALUE;
            setCurrentPlayer(currentPlayer);
        } else {
            if (result == Board.WIN) {
                JOptionPane.showMessageDialog(null, currentPlayer + " WINS");
            } else if (result == Board.DRAW) {
                JOptionPane.showMessageDialog(null, "DRAW");
            }
            currentPlayer = Cell.EMPTY_VALUE;
            this.setCurrentPlayer(currentPlayer);
        }

        // Change the current player if it was the bot's first move
        if (currentPlayer.equals(AI_PLAYER)) {
            currentPlayer = (currentPlayer.equals(Cell.X_VALUE)) ? Cell.O_VALUE : Cell.X_VALUE;
            setCurrentPlayer(currentPlayer);
        }
    }
    public Board(String player){
        this();
        this.currentPlayer = player;
    }
    public Board(){
        this.initMatrix();
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int x = e.getX();
                int y = e.getY();

                if(currentPlayer.equals(Cell.EMPTY_VALUE)){
                    return;
                }

                //Find coordinates of mouse clicker on the board, then draw X-O depending on the turn
                for(int i = 0; i < N; i++){
                    for(int j = 0; j < M; j++){
                        Cell cell = matrix[i][j];

                        int cXStart = cell.getX();
                        int cYStart = cell.getY();

                        int cXEnd = cXStart + cell.getW();
                        int cYEnd = cYStart + cell.getH();

                        if(x >= cXStart && x <= cXEnd && y>=cYStart && y <= cYEnd){
                            if(cell.getValue().equals(Cell.EMPTY_VALUE)){
                                cell.setValue(currentPlayer);
                                repaint();
                                int result = checkWin(currentPlayer);
                                if(endGameListener != null){
                                    endGameListener.end(currentPlayer, result);
                                }
                                if(result == NORMAL){
                                    currentPlayer = currentPlayer.equals(Cell.O_VALUE)? Cell.X_VALUE: Cell.O_VALUE;
                                    if(currentPlayer.equals(AI_PLAYER)){
                                        makeAIMove(currentPlayer);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        });

        try {
            imgX = ImageIO.read(getClass().getResource("x.png"));
            imgO = ImageIO.read(getClass().getResource("o.png"));
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void initMatrix(){
        for(int i = 0; i < N; i++){
            for(int j = 0; j < M; j++){
                Cell cell = new Cell();
                matrix[i][j] = cell;
            }
        }
    }
    public void paint(Graphics g){
        int w = getWidth()/3;
        int h = getHeight()/3;
        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setColor(new Color(220, 237, 255));

        int k = 0;
        for(int i = 0; i < N; i++){
            for(int j = 0; j < M; j++){
                int x = j * w;
                int y = i * h;

                //Save matrix
                Cell cell = matrix[i][j];
                cell.setY(y);
                cell.setX(x);
                cell.setW(w);
                cell.setH(h);

                Color color = k%2 == 0? new Color(220, 237, 255): new Color(130, 90, 91);
                graphics2D.setColor(color);
                graphics2D.fillRect(x,y,w,h);

                if(cell.getValue().equals(Cell.X_VALUE)){
                    Image img = imgX;
                    graphics2D.drawImage(img,x,y,w,h, this);
                }else if(cell.getValue().equals(Cell.O_VALUE)){
                    Image img = imgO;
                    graphics2D.drawImage(img,x,y,w,h, this);
                }
                k++;
            }
        }
    }

    public void setEndGameListener(EndGameListener endGameListener){
        this.endGameListener = endGameListener;
    }
    public void setCurrentPlayer(String currentPlayer){
        this.currentPlayer = currentPlayer;
    }

    public void reset(){
        this.initMatrix();
        this.setCurrentPlayer(Cell.EMPTY_VALUE);
        repaint();
    }

    //0: Draw (None wins), 1: currentPlayer wins, 2: The game is not finished
    public int checkWin(String player){
        //IF WIN
        if(this.matrix[0][0].getValue().equals(player) && this.matrix[1][1].getValue().equals(player)
            && this.matrix[2][2].getValue().equals(player)){
            return WIN;
        }

        if(this.matrix[0][2].getValue().equals(player) && this.matrix[1][1].getValue().equals(player)
                && this.matrix[2][0].getValue().equals(player)){
            return WIN;
        }

        for(int i = 0; i < N; i++){
            if(this.matrix[i][0].getValue().equals(player)&&this.matrix[i][1].getValue().equals(player)
                && this.matrix[i][2].getValue().equals(player)){
                return WIN;
            }
        }

        for(int i = 0; i < M; i++){
            if(this.matrix[0][i].getValue().equals(player)&&this.matrix[1][i].getValue().equals(player)
                    && this.matrix[2][i].getValue().equals(player)){
                return WIN;
            }
        }
        if(isFull()) return DRAW;

        return NORMAL;
    }

    private boolean isFull(){
        int number = N*M;
        int k = 0;
        for(int i = 0; i< N; i++){
            for(int j = 0; j < M; j++){
                if(!this.matrix[i][j].getValue().equals(Cell.EMPTY_VALUE)){
                    k++;
                }
            }
        }
        if(k == number){
            return true;
        }
        return false;
    }

    public String getCurrentPlayer(){
        return currentPlayer;
    }
}
