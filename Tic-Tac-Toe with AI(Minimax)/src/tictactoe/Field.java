package tictactoe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Field {

    Character[][] gameField;
    int count = 0;

    public Field() {
        //fill array with spaces
        gameField = new Character[3][3];
        for (int i = 0; i < gameField.length; i++) {
            for (int j = 0; j < gameField[0].length; j++) {
                gameField[i][j] = ' ';
            }
        }
    }

    public void printField() {
        System.out.println("---------");
        for (Character[] ch : gameField) {
            System.out.print("| ");
            for (char c : ch) {
                System.out.print(c + " ");
            }
            System.out.println("| ");
        }
        System.out.println("---------");
    }

    //get list in which every row is represented as a string, eg. row {'O', 'X', 'X'} -> "OXX"
    public List<String> getRows() {
        List<String> rows = new ArrayList<>();
        rows.add(Arrays.stream(gameField[0]).map(Object::toString).reduce("",String::concat));
        rows.add(Arrays.stream(gameField[1]).map(Object::toString).reduce("",String::concat));
        rows.add(Arrays.stream(gameField[2]).map(Object::toString).reduce("",String::concat));
        return rows;
    }
    //get list in which every column is represented as a string
    public List<String> getColumns() {
        List<String> columns = new ArrayList<>();
        columns.add(Arrays.stream(gameField).map(s -> s[0]).map(Object::toString).reduce("", String::concat));
        columns.add(Arrays.stream(gameField).map(s -> s[1]).map(Object::toString).reduce("", String::concat));
        columns.add(Arrays.stream(gameField).map(s -> s[2]).map(Object::toString).reduce("", String::concat));
        return columns;
    }
    //get list in which every diagonal is represented as a string
    public List<String> getDiagonals() {
        List<String> diagonals = new ArrayList<>();
        diagonals.add("" + gameField[0][0] + gameField[1][1] + gameField[2][2]);
        diagonals.add("" + gameField[0][2] + gameField[1][1] + gameField[2][0]);
        return diagonals;
    }
    //checks if cell is occupied. If it is User turn, shows warning message
    public boolean checkIfOccupied(int y, int x, Player player) {
        if (gameField[y][x] == 'X' || gameField[y][x] == 'O') {
            if (player instanceof User) {
                System.out.println("This cell is occupied! Choose another one!");
            }
            return true;
        }
        return false;
    }
    //puts the character on field
    public void putCharacter(int y, int x, Character ch) {
        Character[] chars = gameField[y];
        chars[x] = ch;
        gameField[y] = chars;
        count++;
    }
}
