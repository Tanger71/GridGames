package sample.minesweeper;

import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class OpenCell extends sample.Cell {

    /**
     * @param x x position of cell
     * @param y y position of cell
     */
    OpenCell(int x, int y) {
        super(x, y);
    }

    /**
     * Inits GUI elements for cell
     */
    @Override
    public void draw() {
        pn_layout = new StackPane();
        rec_btn = new Rectangle(36, 36, Color.DARKGRAY);
        lbl_state = new Label("-"); // NOT INITIALLY ADDED TO pn_layout
        rec_btn.setStrokeWidth(1);
        rec_btn.setStroke(Color.BLACK);
        tri_flag.getPoints().addAll(3.0, 9.0, 15.0, 3.0, 15.0, 15.0);
        tri_flag.setFill(Color.TRANSPARENT);
        pn_layout.getChildren().addAll(rec_btn, tri_flag);
        pn_rt.getChildren().addAll(pn_layout);
    }

    /**
     * reveals specific cell components
     */
    @Override
    public void uncover() {
        if (!uncoverd) {
            pn_layout.getChildren().addAll(lbl_state);
            this.uncoverd = true;
        }
    }

    /**
     * @param state string to be displayed on cell: mine proximity
     */
    void setState(String state) {
        this.cellState = Integer.parseInt(state);
        lbl_state.setText(state);
    }

}
