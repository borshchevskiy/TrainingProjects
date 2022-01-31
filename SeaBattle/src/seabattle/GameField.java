package seabattle;

public class GameField {
    private int height = 11;
    private int width = 11;
    private String[][] field;

    public GameField() {
        initializeField();
    }

    public String[][] getField() {
        return field;
    }

    public void setCell(int y, int x, String value){
        field[y][x] = value;
    }

    public String getCell(int y, int x){
        return field[y][x];
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void initializeField() {
        field = new String[height][width];
        field[0][0] = "  ";//0,0 is blank
        for (int i = 1; i < field[0].length; i++) {
            field[0][i] = i + " "; //top row is numbers 1 2 3...
        }
        for (int i = 1; i < field.length; i++) {
            field[i][0] = (char) (64 + i) + " ";//left column is letters A B C...
        }
        for (int i = 1; i < this.height; i++) {
            for (int j = 1; j < this.width; j++) {
                field[i][j] = "~ ";
            }
        }
        //fieldFow = Arrays.stream(field).map(String[]::clone).toArray(String[][]::new);//copy field to fieldFow
    }

    public void printField() {
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                System.out.print(field[i][j]);
            }
            System.out.println();
        }
    }

}


