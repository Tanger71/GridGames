package sample;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

public abstract class Cell {

    public int posX;
    public int posY;

    public StackPane pn_layout;
    public Rectangle rec_btn;
    public Label lbl_state;

    public Pane pn_rt = new Pane();

    public boolean uncoverd = false;

    public Cell(int x, int y){
        this.posX = x;
        this.posY = y;
        this.draw();
    }

    public abstract void draw();

    public void uncover(){

    }

}
