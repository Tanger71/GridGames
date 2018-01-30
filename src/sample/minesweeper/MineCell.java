package sample.minesweeper;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import sample.Cell;

public class MineCell extends Cell {

    Circle cir_bomb = new Circle(5, Color.BLACK);

    MineCell(int x, int y) {
        super(x, y);
    }

    @Override
    public void draw() {
        pn_layout = new StackPane();
        rec_btn = new Rectangle(36, 36, Color.DARKGRAY);
        lbl_state = new Label("x"); // NOT INITIALLY ADDED TO pn_layout
        rec_btn.setStrokeWidth(1);
        rec_btn.setStroke(Color.BLACK);
        tri_flag.getPoints().addAll(3.0, 9.0, 15.0, 3.0, 15.0, 15.0);
        tri_flag.setFill(Color.TRANSPARENT);
        pn_layout.getChildren().addAll(rec_btn, tri_flag);
        pn_rt.getChildren().addAll(pn_layout);
    }

    @Override
    public void uncover() {
        if (!uncoverd) {
            pn_layout.getChildren().addAll(lbl_state);
            this.uncoverd = true;
        }
    }

}
