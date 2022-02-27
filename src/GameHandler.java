import javax.swing.JButton;

public class GameHandler {
    
    public static int[] board = new int[9];
    private final static int[] scores = {-1, 1, 0};
    
    public static int turn = 0;

    public static void initBoard() {
        for (int i = 0; i < board.length; i++) {
            board[i] = 0;
        }
        turn = 0;
    }

    public static void botMove() {
        int bestScore = -Integer.MAX_VALUE;
        int move = -1;
        for(int i = 0; i < board.length; i++) {
            if(isEmpty(i)) {
                setBoardValue(2, i);
                int score = minimax(0, false);
                setBoardValue(0, i);
                if(score > bestScore) {
                    bestScore = score;
                    move = i;
                }
            }
        }
        setBoardValue(2, move);
    }

    public static int minimax(int depth, boolean isMax) {
        int result = checkWinner();
        if(result != 0) {
            return scores[result-1];
        }

        if(isMax) {
            int bestScore = -Integer.MAX_VALUE;
            for(int i = 0; i < board.length; i++) {
                if(isEmpty(i)) {
                    setBoardValue(2, i);
                    int score = minimax(depth+1, false);
                    setBoardValue(0, i);
                    bestScore = Math.max(score, bestScore);
                }
            }
            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;
            for(int i = 0; i < board.length; i++) {
                if(isEmpty(i)) {
                    setBoardValue(1, i);
                    int score = minimax(depth+1, true);
                    setBoardValue(0, i);
                    bestScore = Math.min(score, bestScore);
                }
            }
            return bestScore;
        }
    }

    public static boolean isEmpty(int index) {
        return board[index] == 0;
    }

    public static boolean isBoardFilled() {
        for(int i = 0; i < board.length; i++) {
            if(board[i] == 0) return false;
        }
        return true;
    }

    public static int[] getBoard() {
        return board;
    }

    public static int[] setBoardValue(int value, int index) {
        board[index] = value;
        turn += (value == 0) ? -1 : 1;
        return board;
    }

    public static int checkWinner() {
        int winner = 0;
        if(
            (board[0] == 1 && board[1] == 1 && board[2] == 1) ||
            (board[3] == 1 && board[4] == 1 && board[5] == 1) ||
            (board[6] == 1 && board[7] == 1 && board[8] == 1) ||
            
            (board[0] == 1 && board[3] == 1 && board[6] == 1) ||
            (board[1] == 1 && board[4] == 1 && board[7] == 1) ||
            (board[2] == 1 && board[5] == 1 && board[8] == 1) ||
            
            (board[0] == 1 && board[4] == 1 && board[8] == 1) ||
            (board[2] == 1 && board[4] == 1 && board[6] == 1) ) 
		{
			winner = 1;
		} else if(
            (board[0] == 2 && board[1] == 2 && board[2] == 2) ||
            (board[3] == 2 && board[4] == 2 && board[5] == 2) ||
            (board[6] == 2 && board[7] == 2 && board[8] == 2) ||
            
            (board[0] == 2 && board[3] == 2 && board[6] == 2) ||
            (board[1] == 2 && board[4] == 2 && board[7] == 2) ||
            (board[2] == 2 && board[5] == 2 && board[8] == 2) ||
            
            (board[0] == 2 && board[4] == 2 && board[8] == 2) ||
            (board[2] == 2 && board[4] == 2 && board[6] == 2) ) 
		{
			winner = 2;
		} else if(turn == 9) {
            winner = 3;
        } else {
			winner = 0;
		}
        return winner;
	}

    public static void fillSquare (JButton[] box) {
        for(int i = 0; i < box.length; i++) {
            String piece = (board[i] == 1) ? "X": board[i] == 2 ? "O":"";
            box[i].setText(piece);
        }
    }
}
