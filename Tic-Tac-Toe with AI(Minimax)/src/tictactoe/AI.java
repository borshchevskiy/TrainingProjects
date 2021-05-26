package tictactoe;


public abstract class AI extends Player{

    public AI(Field field, char charToPut) {
        super(field, charToPut);
    }

    protected int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max + 1 - min)) + min);
    }

    protected char changeChar() {
        return  charToPut == 'X' ? 'O' : 'X';
    }

}
