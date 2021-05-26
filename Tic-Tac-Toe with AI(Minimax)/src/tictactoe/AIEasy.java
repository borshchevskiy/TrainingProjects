package tictactoe;

public class AIEasy extends AI {
    public AIEasy(Field field, char charToPut) {
        super(field, charToPut);
    }

    //Easy AI will move to random cell on it's turn
    @Override
    public void turn() {
        while (true) {
            //get random coordinates from 0 to 2
            y = getRandomNumber(0, 2);
            x = getRandomNumber(0, 2);

            //Check if this cell is occupied
            if (field.checkIfOccupied(y, x, this)) {
                continue;
            }
            //all checks passed, break the loop
            break;
        }
        //place the required char
        field.putCharacter(y, x, charToPut);
        //Show the for AI move
        System.out.println("Making move level \"easy\"");
    }
}
