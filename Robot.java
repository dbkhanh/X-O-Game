package Game;

public class Robot {
    private static final int MAX_DEPTH = 9; // Maximum depth to search in the Minimax algorithm

    public void makeMove(Board board) {
        int[] bestMove = minimax(board, MAX_DEPTH, true);
        int row = bestMove[0];
        int col = bestMove[1];
        board.makeMove(row, col, Cell.O_VALUE);
    }

    private int[] minimax(Board board, int depth, boolean maximizingPlayer) {
        String currentPlayer = board.getCurrentPlayer();
        int gameResult = board.checkWin(currentPlayer);
        if (gameResult == Board.WIN && board.getCurrentPlayer().equals(Cell.X_VALUE)) {
            return new int[] {-1, -1, -1}; // Human wins
        } else if (gameResult == Board.WIN && board.getCurrentPlayer().equals(Cell.O_VALUE)) {
            return new int[] {-1, -1, 1}; // Robot wins
        } else if (gameResult == Board.DRAW || depth == 0) {
            return new int[] {-1, -1, 0}; // Draw or maximum depth reached
        }

        int[] bestMove = new int[3];
        bestMove[2] = maximizingPlayer ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board.isEmpty(i, j)) {
                    if (maximizingPlayer) {
                        board.makeMove(i, j, Cell.O_VALUE);
                        int[] currentMove = minimax(board, depth - 1, false);
                        if (currentMove[2] > bestMove[2]) {
                            bestMove[0] = i;
                            bestMove[1] = j;
                            bestMove[2] = currentMove[2];
                        }
                    } else {
                        board.makeMove(i, j, Cell.X_VALUE);
                        int[] currentMove = minimax(board, depth - 1, true);
                        if (currentMove[2] < bestMove[2]) {
                            bestMove[0] = i;
                            bestMove[1] = j;
                            bestMove[2] = currentMove[2];
                        }
                    }
                    board.undoMove(i, j);
                }
            }
        }

        return bestMove;
    }
}
