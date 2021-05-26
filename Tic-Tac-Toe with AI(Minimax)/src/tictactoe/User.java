package tictactoe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User extends Player {
    //Pattern and matcher to check player's input. Only numbers 0-9 are allowed
    private Pattern pattern = Pattern.compile("[^0-9]");
    private Matcher matcher;

    public User(Field field, char charToPut) {
        super(field, charToPut);
    }

    @Override
    protected void turn() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String inputCoordinates;
        while (true) {
            //try read coordinates
            System.out.print("Enter the coordinates: > ");
            try {
                inputCoordinates = reader.readLine();
            } catch (IOException e) {
                System.out.println("Input error! Try again!");
                continue;
            }
            //remove forwarding and trailing spaces with .trim(), replace spaces inside string with "",
            //so we have only characters in input, without spaces, eg. 1 1 -> 11
            inputCoordinates = inputCoordinates.trim().replaceAll(" ", "");

            //check if string contains any symbols except numbers 0-9
            matcher = pattern.matcher(inputCoordinates);
            if (matcher.find()) {
                System.out.println("You should enter numbers!");
                continue;
            }

            //split input into two values, eg. 11 -> {1, 1}
            String[] coordinates = inputCoordinates.split("", 2);

            //try to parse each value
            try {

                x = Integer.parseInt(coordinates[1]) - 1;
                // -1, because array coordinates start from 0. If players inputs row #1 it is row #0 in the array
                y = Integer.parseInt(coordinates[0]) - 1;
            } catch (NumberFormatException n) {
                System.out.println("You should enter numbers!");
                continue;
            }

            //check if values fit within boundaries
            if (y > 2 || y < 0 || x > 2 || x < 0) {
                System.out.println("Coordinates should be from 1 to 3!");
                continue;
            }

            //check if this cell is occupied
            if (field.checkIfOccupied(y, x, this)) {
                continue;
            }
            //all checks passed, break the loop
            break;
        }
        //place the char on the field
        field.putCharacter(y, x, charToPut);
    }
}
