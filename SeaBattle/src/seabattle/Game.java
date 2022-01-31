package seabattle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Game {

    Player player1;
    Player player2;

    Player activePlayer;
    Player opponent;

    private boolean firstPlayerTurn = true;
    Scanner scanner = new Scanner(System.in);

    //We have 2 players, each one has list of ships and 2 fields - playerField and enemyField
    //Each field is String array. playerField shows player's ships. enemyField shows results of players shots.
    //"~" for fog of war, "M" for miss, "X" for hit.

    public void initialize() {
        player1 = new Player("Player 1");
        player2 = new Player("Player 2");
        activePlayer = player1;
        opponent = player2;
        player1.placeShips();
        changeTurn();
        player2.placeShips();
    }

    public void startGame() {
        while (!activePlayer.isWin()) {
            changeTurn();
            activePlayer.showEnemyField();
            System.out.println("---------------------");
            activePlayer.showPlayerField();
            System.out.println(activePlayer.getName() + ", it's your turn");

            shoot();
        }
    }

    //active player takes a shot and we compare it with opponent field
    public void shoot() {
        BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
        boolean shot = false;
        String input;
        String result = "";
        String resultMessage = "";
        //Player opponent = firstPlayerTurn ? player2 : player1;
        while (!shot) {
            System.out.println();
            System.out.print("> ");
            try {
                input = inputReader.readLine();
            } catch (IOException e) {
                System.out.println("Error! You entered the wrong coordinates! Try again:");
                continue;
            }
            //delete leading and trailing spaces, turn to upper case
            input = input.trim().toUpperCase();

            //split input string to 1st and 2nd coordinates, eg. "F1" -> {F, 1}
            String[] coordinates = input.split("", 2);

            //if input split was not successful - continue
            if (coordinates.length < 2){
                System.out.println("Error! You entered the wrong coordinates! Try again:");
                continue;
            }

            coordinates[0] = coordinates[0].strip();
            coordinates[1] = coordinates[1].strip();


            int y;
            int x;
            try {
                y = coordinates[0].charAt(0) - 64;
                x = Integer.parseInt(coordinates[1]);
            }//if failed to parse Integer
            catch (NumberFormatException n){
                System.out.println("Error! You entered the wrong coordinates! Try again:");
                continue;
            }
            //check if coordinates out of bounds
            if ((y < 1 || y > 10) || (x < 1 || x > 10)) {
                System.out.println("Error! You entered the wrong coordinates! Try again:");
                continue;
            }
            //compare shot coordinates with enemy field
            switch (opponent.getPlayerFieldCell(y, x)) {
                case "~ ":
                    result = "M ";
                    resultMessage = "You missed.";
                    break;
                case "O ":
                    result = "X ";
                    resultMessage = opponent.damageShip(y, x);
                    break;
                case "X ":
                    result = "X ";
                    resultMessage = "You missed.";
                    break;
            }
            activePlayer.setEnemyFieldCell(y, x, result);
            System.out.println();
            System.out.println(resultMessage);
            if (activePlayer.isWin()) {
                break;
            }
            shot = true;
        }
    }

    //this method swaps active player and opponent
    public void changeTurn() {
        System.out.println("Press Enter and pass the move to another player\n");
        String input = scanner.nextLine();
        while (!input.isEmpty()) {
            input = scanner.nextLine();
        }
        //swap active player and opponent
        firstPlayerTurn = !firstPlayerTurn;
        if (!firstPlayerTurn) {
            activePlayer = player2;
            opponent = player1;
        } else {
            activePlayer = player1;
            opponent = player2;
        }

    }
}