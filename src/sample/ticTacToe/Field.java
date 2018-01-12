package sample.ticTacToe;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import sample.Grid_Intf;

public class Field implements Grid_Intf {

    public int WIDTH;
    public int HIEGHT;
    StackPane pn_rt = new StackPane();
    Cell[][] cells;
    public Insets inset = new Insets(10, 10, 10, 10);

    //Field() Constructor: inits
    public Field(int w, int h){
        this.WIDTH = w;
        this.HIEGHT = h;
        cells = new Cell[WIDTH][HIEGHT];
        for(int i = 0 ; i < WIDTH ; i++){
            for(int j  = 0 ; j < HIEGHT ; j++){
                cells[i][j] = new Cell(i, j);
            }
        }
    }

    @Override
    //draw() method: draws GUI for the game grid
    public void draw(){
        GridPane pn_layout = new GridPane();
        for(int i = 0 ; i < WIDTH ; i++){
            for(int j  = 0 ; j < HIEGHT ; j++){
                pn_layout.setMargin(cells[i][j].pn_rt, inset);
                pn_layout.add(cells[i][j].pn_rt, i, j);
            }
        }
        pn_layout.setAlignment(Pos.CENTER);
        pn_rt.getChildren().addAll(pn_layout);
    }

    @Override
    //getPane() method: returns this.pn_rt
    public Pane getPane(){
        return this.pn_rt;
    }

}
