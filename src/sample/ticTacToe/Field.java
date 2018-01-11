package sample.ticTacToe;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import sample.Grid_Intf;

public class Field implements Grid_Intf {

    public int WIDTH;
    public int HIGHT;
    Pane pn_rt = new Pane();
    Cell[][] cells;

    public Field(int w, int h){
        this.WIDTH = w;
        this.HIGHT = h;
        cells = new Cell[WIDTH][HIGHT];
        for(int i = 0 ; i < WIDTH ; i++){
            for(int j  = 0 ; j < HIGHT ; j++){
                cells[i][j] = new Cell(i, j);
            }
        }
    }

    @Override
    public void draw(){
        GridPane pn_layout = new GridPane();
    }

    @Override
    public Pane getPane(){
        return this.pn_rt;
    }

}
