package sample.minesweeper;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class OpenCell extends sample.Cell{

    public int minesNear;

    public OpenCell(int x, int y){
        super(x, y);
    }

    public void draw(){
        pn_layout = new StackPane();
        rec_btn = new Rectangle(36, 36, Color.DARKGRAY);
        rec_btn.setStrokeWidth(2);
        rec_btn.setStroke(Color.BLACK);
        pn_layout.getChildren().addAll(rec_btn);
        pn_rt.getChildren().addAll(pn_layout);

    }

}
