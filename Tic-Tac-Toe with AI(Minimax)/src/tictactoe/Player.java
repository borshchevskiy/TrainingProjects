package tictactoe;

public abstract class Player {

    //y and x coordinates for player's move
    protected int x;
    protected int y;


    protected Field field;

    //player's character - 'X' or 'O
    protected char charToPut;


    public Player(Field field, char charToPut){
        this.field = field;
        this.charToPut = charToPut;
    }

    protected abstract void turn();





}
