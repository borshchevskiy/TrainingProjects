package seabattle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Player {

    private List<Ship> ships;
    private GameField playerField;
    private GameField enemyField;

    private int yStart;
    private int yEnd;
    private int xStart;
    private int xEnd;

    private String name;

    private boolean win;

    public boolean isWin() {
        return win;
    }

    public Player(String name) {
        ships = new ArrayList<>();
        ships.add(0, new AircraftCarrier());
        ships.add(1, new Battleship());
        ships.add(2, new Submarine());
        ships.add(3, new Cruiser());
        ships.add(4, new Destroyer());

        playerField = new GameField();
        enemyField = new GameField();

        this.name = name;

        win = false;
    }

    public String getName() {
        return name;
    }

    public String[][] getPlayerField() {
        return playerField.getField();
    }

    public String[][] getEnemyField() {
        return enemyField.getField();
    }

    public String getPlayerFieldCell(int y, int x) {
        return playerField.getCell(y, x);
    }

    public void setPlayerFieldCell(int y, int x, String value) {
        playerField.setCell(y, x, value);
    }

    public String getEnemyFieldCell(int y, int x) {
        return enemyField.getCell(y, x);
    }

    public void setEnemyFieldCell(int y, int x, String value) {
        enemyField.setCell(y, x, value);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void placeShips() {
        BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));
        boolean isVertical;
        String input = "";
        System.out.printf("%s, place your ships on the game field%n", name);
        System.out.println();
        showPlayerField();
        System.out.println();
        for (Ship ship : ships) {
            boolean shipIsPlaced = false;
            while (!shipIsPlaced) {

                System.out.printf("Enter the coordinates of the %s (%d cells)%n", ship.getName(),
                        ship.getSize());
                System.out.println();
                System.out.print("> ");

                //read the input coordinates
                try {
                    input = inputReader.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //convert coordinates to integer for convenient comparison
                if (!convertInputCoordinates(input)) {
                    continue;
                }

                //check the orientation of the ship.
                //print error message and continue if ship is placed diagonally;
                if (!checkOrientation(ship)) {
                    continue;
                }

                //check if there is neighboring ships, continue if collisions
                if (checkCollision()) {
                    continue;
                }
                //Set isVertical == true if ship is placed vertically.
                isVertical = isVertical();

                //if all checks are passed - save coordinates to ship
                saveCoordinatesToShip(isVertical, ship);

                // place the ship on field
                System.out.println();
                shipIsPlaced = drawShip(isVertical);
            }
        }
    }

    public boolean convertInputCoordinates(String input) {
        //delete leading and trailing spaces, turn to upper case
        input = input.trim().toUpperCase();

        //split input string to 1st and 2nd coordinates, eg. "F1 F5" -> {F1, F5}
        String[] inputCoordinates = input.split(" ", 2);

        //if input split was not successful return false
        if (inputCoordinates.length < 2) {
            return false;
        }

        inputCoordinates[0] = inputCoordinates[0].strip();
        inputCoordinates[1] = inputCoordinates[1].strip();

        //split first and second coordinates to 2 arrays with x and y coordinates, eg. {F1, F5} -> {F, 1} and {F, 5}
        String[] firstCoordinate = inputCoordinates[0].split("", 2);
        String[] secondCoordinate = inputCoordinates[1].split("", 2);

        //check if coordinates are diagonal - x1 != x2 and y1 != y2 (where x is x-coordinate (1 - 10), y is y-coordinate (A - J))
        if (!firstCoordinate[0].equals(secondCoordinate[0]) & !firstCoordinate[1].equals(secondCoordinate[1])) {
            System.out.println("Error! Wrong ship location! Try again:");
            System.out.println();
            return false;
        }


        //transform coordinates to int for convenient comparison
        try {
            yStart = Math.min(firstCoordinate[0].charAt(0) - 64, secondCoordinate[0].charAt(0) - 64);
            yEnd = Math.max(firstCoordinate[0].charAt(0) - 64, secondCoordinate[0].charAt(0) - 64);
            xStart = Math.min(Integer.parseInt(firstCoordinate[1]), Integer.parseInt(secondCoordinate[1]));
            xEnd = Math.max(Integer.parseInt(firstCoordinate[1]), Integer.parseInt(secondCoordinate[1]));

        }//if failed to parse Integer
        catch (NumberFormatException n) {
            System.out.println("Error! You entered the wrong coordinates! Try again:");
            System.out.println();
            return false;
        }
        //check if coordinates out of bounds
        if ((yStart < 1 || yEnd > 10) || (xStart < 1 || xEnd > 10)) {
            System.out.println("Error! You entered the wrong coordinates! Try again:");
            return false;
        }
        //all checks passed - return true
        return true;

    }

    public boolean checkOrientation(Ship ship) {
        //check the size if ship is placed horizontally
        if (xStart != xEnd) {
            if (xEnd - xStart != ship.getSize() - 1) {
                System.out.printf("Error! Wrong length of the %s! Try again:%n", ship.getName());
                return false;
            }
        }
        //check the size if ship is placed vertically
        if (yStart != yEnd) {
            if (yEnd - yStart != ship.getSize() - 1) {
                System.out.printf("Error! Wrong length of the %s! Try again:%n", ship.getName());
                return false;
            }
        }
        return true;
    }

    public boolean isVertical() {
        return xStart == xEnd;
    }

    public boolean checkCollision() {
        boolean isCollision = false;
        for (int y = yStart - 1; y <= yEnd + 1 && !isCollision; y++) {
            for (int x = xStart - 1; x <= xEnd + 1 && !isCollision; x++) {
                if (y < 1 || y > 10) {
                    continue;
                }
                if (x < 1 || x > 10) {
                    continue;
                }
                if (getPlayerFieldCell(y, x).equals("O ")) {
                    System.out.println();
                    System.out.println("Error! You placed it too close to another one. Try again:");
                    isCollision = true;
                }
            }
        }
        return isCollision;
    }

    public void saveCoordinatesToShip(boolean isVertical, Ship ship) {
        //saves coordinates to ship depending of it's orientation
        if (isVertical) {
            for (int j = 0; j < ship.getSize(); j++) {
                ship.getCoordinates().add(j, new int[]{yStart + j, xStart});
            }
        } else {
            for (int j = 0; j < ship.getSize(); j++) {
                ship.getCoordinates().add(j, new int[]{yStart, xStart + j});
            }
        }
    }

    public boolean drawShip(boolean isVertical) {
        if (isVertical) {
            for (int j = yStart; j <= yEnd; j++) {
                setPlayerFieldCell(j, xStart, "O ");
            }
        } else {
            for (int j = xStart; j <= xEnd; j++) {
                setPlayerFieldCell(yStart, j, "O ");
            }
        }
        showPlayerField();
        System.out.println();
        return true;
    }

    public String damageShip(int y, int x) {
        int[] shot = new int[]{y, x};
        Iterator<Ship> iterator = ships.iterator();
        while (iterator.hasNext()) {
            Ship ship = iterator.next();
            //for each ship in the list we check if it contains coordinates of the shot,
            //if it does, we remove this coordinate from ships' coordinates list.
            ship.getCoordinates().removeIf(coordinate -> Arrays.equals(coordinate, shot));
            setPlayerFieldCell(y, x, "X ");
            //if ships coordinates list becomes empty - ship is sank
            if (ship.getCoordinates().isEmpty()) {
                iterator.remove();
                //if list of player's ships becomes (we check list of enemy ships) active player wins
                if (ships.isEmpty()) {
                    win = true;
                    return "You sank the last ship. You won. Congratulations!";
                }
                return "You sank a ship!";
            }
        }
        return "You hit a ship!";
    }

    public void showPlayerField() {
        playerField.printField();
    }

    public void showEnemyField() {
        enemyField.printField();
    }


}
