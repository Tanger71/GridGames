package sample;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;

public abstract class Cell {

    public int posX;
    public int posY;

    public StackPane pn_layout;
    public Rectangle rec_btn;
    protected Label lbl_state;

    public Pane pn_rt = new Pane();

    public boolean uncoverd = false;
    public boolean isFlagged;
    protected int cellState;

    protected Polygon tri_flag = new Polygon();

    /**
     * inits stuff
     * @param x x position of cell
     * @param y y position of cell
     */
    public Cell(int x, int y) {
        this.posX = x;
        this.posY = y;
        this.uncoverd = false;
        this.isFlagged = false;

        this.draw();
    }

    /**
     * Inits GUI elements for cell
     */
    public abstract void draw();

    /**
     * reveals specific cell components
     */
    public void uncover() {

    }

    /**
     * flags a cell on rightclick
     */
    public void flag() {
        System.out.println("adding flag...");
        if (!isFlagged) {
            tri_flag.setFill(Color.DARKRED);
            isFlagged = true;
        }
    }

    /**
     * removes flag on rightclick
     */
    public void unflag() {
        System.out.println("removing flag...");
        if (isFlagged) {
            tri_flag.setFill(Color.TRANSPARENT);
            isFlagged = false;
        }
    }

}
