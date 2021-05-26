package tictactoe;


public class AIHard extends AI {

    public AIHard(Field field, char charToPut) {
        super(field, charToPut);
    }

    // Hard AI implements minimax algorithm to make a move
    @Override
    public void turn() {
        Integer bestScore = Integer.MIN_VALUE;
        int[] bestMove = new int[2];

        for (int i = 0; i < field.gameField.length; i++) {
            for (int j = 0; j < field.gameField[0].length; j++) {
                if (field.gameField[i][j] == ' ') {//search for empty cell
                    field.gameField[i][j] = charToPut;//make a move
                    field.count++;//increase count
                    int score = miniMax(field, false);//start minimax in recursion
                    field.gameField[i][j] = ' ';//undo the move
                    field.count--;//return count
                    //for the best score store the coordinates of the move
                    if (score > bestScore) {
                        bestScore = score;
                        bestMove[0] = i;
                        bestMove[1] = j;
                    }
                }
            }
        }
        //make a move
        field.putCharacter(bestMove[0], bestMove[1], charToPut);
        System.out.println("Making move level \"hard\"");
    }

    //The minimax algorithm
    public int miniMax(Field field, boolean isMaximizing) {

        //check if any side is winning
        if (ifWinning(field)) {
            return 1;
        } else if (ifLosing(field)) {
            return -1;
        } else if (field.count == 9) {
            return 0;
        }
        //minimax for maximizing player(self)
        if (isMaximizing) {
            int bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < field.gameField.length; i++) {
                for (int j = 0; j < field.gameField[0].length; j++) {
                    if (field.gameField[i][j] == ' ') {
                        field.gameField[i][j] = charToPut;
                        field.count++;
                        int score = miniMax(field, false);
                        field.gameField[i][j] = ' ';
                        field.count--;
                        bestScore = Math.max(score, bestScore);
                    }
                }
            }
            return bestScore;
        } else { //else minimax for minimising player (opponent)
            int bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < field.gameField.length; i++) {
                for (int j = 0; j < field.gameField[0].length; j++) {
                    if (field.gameField[i][j] == ' ') {
                        field.gameField[i][j] = changeChar();
                        field.count++;
                        int score = miniMax(field, true);
                        field.gameField[i][j] = ' ';
                        field.count--;
                        bestScore = Math.min(score, bestScore);
                    }
                }
            }
            return bestScore;
        }

    }
    //check it won
    public boolean ifWinning(Field field) {
        String result = "" + charToPut + charToPut + charToPut;
        return field.getRows().contains(result)
                || field.getColumns().contains(result)
                || field.getDiagonals().contains(result);
    }

    //check if opponent won
    public boolean ifLosing(Field field) {
        String result = "" + changeChar() + changeChar() + changeChar();
        return field.getRows().contains(result)
                || field.getColumns().contains(result)
                || field.getDiagonals().contains(result);
    }
}







