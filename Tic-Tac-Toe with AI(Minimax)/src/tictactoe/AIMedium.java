package tictactoe;

import java.util.List;

public class AIMedium extends AI {
    public AIMedium(Field field, char charToPut) {
        super(field, charToPut);
    }

    //Medium AI will check if it can win on the next move and if it can he will make winning move.
    //If cannot win in one move it will check if can lose in one move. If so it will try to defend.
    //If there is no win or defeat in one move it will make a random move;
    @Override
    public void turn() {

        //check if wins in one move
        if (checkIfWinning(charToPut)) {
            field.putCharacter(y, x, charToPut);
            System.out.println("Making move level \"medium\"");
            return;
        }
        //check if opponent can win in one move
        if (checkIfWinning(changeChar())) {
            field.putCharacter(y, x, charToPut);
            System.out.println("Making move level \"medium\"");
            return;
        }

        //If nobody can win in one move make a random move
        while (true) {
            y = getRandomNumber(0, 2);
            x = getRandomNumber(0, 2);

            if (field.checkIfOccupied(y, x, this)) {
                continue;
            }
            //all checks passed, break the loop
            break;
        }
        //place the required char
        field.putCharacter(y, x, charToPut);
        System.out.println("Making move level \"medium\"");
    }

    //checks if character 'ch' is able to win - if there is two characters and a ' ' in a row
    private boolean checkIfWinning(char ch) {
        int countFilled;
        //check the columns for win in one move
        List<String> list = field.getColumns();
        for (String s : list) {
            countFilled = 0;
            if (s.contains(" ")) {
                countFilled = (int) s.chars().filter(x -> x == ch).count();
            }
            if (countFilled == 2) {
                y = s.indexOf(' ');
                x = list.indexOf(s);
                return true;
            }
        }
        //check the rows for win in one move
        list = field.getRows();
        for (String s : list) {
            countFilled = 0;
            if (s.contains(" ")) {
                countFilled = (int) s.chars().filter(x -> x == ch).count();
            }
            if (countFilled == 2) {
                y = list.indexOf(s);
                x = s.indexOf(' ');
                return true;
            }
        }
        //check the diagonals for win in one move
        list = field.getDiagonals();
        for (String s : list) {
            countFilled = 0;
            if (s.contains(" ")) {
                countFilled = (int) s.chars().filter(x -> x == ch).count();
            }
            if (countFilled == 2) {
                y = s.indexOf(' ');
                if (list.indexOf(s) == 0) {
                    x = s.indexOf(' ');
                } else {
                    x = Math.abs(y - 2);
                }
                return true;
            }
        }
        //if can not win in one move - return false
        return false;
    }
}
