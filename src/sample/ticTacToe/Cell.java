package sample.ticTacToe;

public class Cell {
    public int posX;
    public int posY;
    public char state = '-'; // 'x', 'o' , or '-'

    public Cell(int x, int y){
        this.posX = x;
        this.posY = y;
    }

    public Cell(int x, int y, char st){
        this.posX = x;
        this.posY = y;
        this.state = st;
    }

    public void setState(char st){
        this.state = st;
        //TODO: GUI stuff
    }
}
