package tictactoe;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Game {

    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    boolean gameFinished;
    Player player1;
    Player player2;
    Field field;
    char firstPlayerChar = 'X';
    char secondPlayerChar = 'O';

    public void initialize() {
        field = new Field();
        field.count = 0;
        gameFinished = false;
        menu();
    }

    public void menu() {
        String input;
        while (true) {
            System.out.print("Input command: > ");
            //try to read parameters
            try {
                input = reader.readLine();
            } catch (IOException e) {
                System.out.println("Bad parameters!");
                continue;
            }
            //remove forwarding and trailing spaces with .trim()
            input = input.trim();
            //split into three parameters
            String[] inputParameters = input.split(" ", 3);
            //if first parameter is "exit" than terminate the application
            if (inputParameters[0].equals("exit")) {
                gameFinished = true;
                break;
            }
            //if there is less then 3 parameters - show warning message
            if (inputParameters.length < 3) {
                System.out.println("Bad parameters!");
                continue;
            }
            //If first parameter is "start" than check other parameters and assign players
            if (inputParameters[0].equals("start")) {
                switch (inputParameters[1]) {
                    case "user":
                        player1 = new User(field, firstPlayerChar);
                        break;
                    case "easy":
                        player1 = new AIEasy(field, firstPlayerChar);
                        break;
                    case "medium":
                        player1 = new AIMedium(field, firstPlayerChar);
                        break;
                    case "hard":
                        player1 = new AIHard(field, firstPlayerChar);
                        break;
                    default:
                        System.out.println("Bad parameters!");
                        continue;
                }
                switch (inputParameters[2]) {
                    case "user":
                        player2 = new User(field, secondPlayerChar);
                        break;
                    case "easy":
                        player2 = new AIEasy(field, secondPlayerChar);
                        break;
                    case "medium":
                        player2 = new AIMedium(field, secondPlayerChar);
                        break;
                    case "hard":
                        player2 = new AIHard(field, secondPlayerChar);
                        break;
                    default:
                        System.out.println("Bad parameters!");
                        continue;
                }
            }
            break;
        }
    }

    public void play() {
        while (true) {
            initialize();
            if (gameFinished){
                break;
            }
            field.printField();
            while (true) {
                player1.turn();
                field.printField();
                if (isGameFinished(player1)) {
                    break;
                }
                player2.turn();
                field.printField();
                if (isGameFinished(player2)) {
                    break;
                }
            }
        }
    }

    //checks for terminal state of the game
    public boolean isGameFinished(Player player) {
        String result = "" + player.charToPut + player.charToPut + player.charToPut;
        if (field.getRows().contains(result)
                || field.getColumns().contains(result)
                || field.getDiagonals().contains(result)) {
            System.out.println(player.charToPut + " wins");
            return true;
        } else {
            if (field.count == 9) {
                System.out.println("Draw");
                return true;
            }
        }
        return false;
    }
}
