package sample.ticTacToe;

import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Cell {
    public int posX;
    public int posY;
    public char state = '-'; // 'x', 'o' , or '-'

    public Pane pn_rt = new Pane();

    //Cell() Constructor: inits
    public Cell(int x, int y){
        this.posX = x;
        this.posY = y;
        draw();
    }
    //Cell() Constructor: inits
    public Cell(int x, int y, char st){
        this.posX = x;
        this.posY = y;
        this.state = st;
    }

    //setState() method: defines this.state
    public void setState(char st){
        this.state = st;
        //TODO: GUI stuff
    }

    //draw() method: draws GUI for this.pn_rt
    public void draw(){
        StackPane pn_layout = new StackPane();
        Rectangle rec_btn = new Rectangle(100, 100, Color.DARKGRAY);
        rec_btn.setArcWidth(10);
        rec_btn.setArcWidth(10);
        pn_layout.getChildren().addAll(rec_btn);
        pn_rt.getChildren().addAll(pn_layout);
    }
}
