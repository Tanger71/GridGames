package sample.minesweeper;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import sample.Cell;
import sample.Grid_Intf;
import sample.ticTacToe.TicCell;
import sample.ticTacToe.TicTacToe;

import java.util.Random;

public class MineField implements Grid_Intf {


    public int WIDTH;
    public int HEIGHT;
    public int MINES;

    public StackPane pn_rt = new StackPane();
    public Cell[][] cells;
    public Insets inset = new Insets(1, 1, 1, 1);

    public MineField(int w, int h, int m) {
        this.WIDTH = w;
        this.HEIGHT = h;
        this.MINES = m;
        this.cells = new OpenCell[WIDTH][HEIGHT];
        for (int i = 0; i < this.WIDTH; i++) {
            for (int j = 0; j < this.HEIGHT; j++) {
                this.cells[i][j] = new OpenCell(i, j);
            }
        }
    }

    @Override
    public void draw() {
        //TODO: GUI
        GridPane pn_layout = new GridPane();
        for(int i = 0 ; i < this.WIDTH ; i++){
            for(int j  = 0 ; j < this.HEIGHT ; j++){
                pn_layout.add(this.cells[i][j].pn_rt, i, j);
                pn_layout.setMargin(this.cells[i][j].pn_rt, this.inset);
                setMouseEvents(this.cells[i][j]);
                System.out.println("cell printed!");
            }
        }
        pn_layout.setAlignment(Pos.CENTER);
        this.pn_rt.getChildren().addAll(pn_layout);
    }

    public void plantBombs(int notX, int notY) {
        Random random = new Random();
        int minesPlaced = 0;
        while (minesPlaced <= this.MINES) {
            int x = random.nextInt(this.WIDTH);
            int y = random.nextInt(this.HEIGHT);
            if (!(this.cells[x][y] instanceof MineCell) || x != notX && y != notY) {
                this.cells[x][y] = new MineCell(x, y);
            }
            minesPlaced++;
        }
    }

    @Override
    public Pane getPane() {
        return pn_rt;
    }

    @Override
    public char checkForWin() {
        return '0';
    }

    @Override
    public void onWin(char winner) {

    }

    public void setMouseEvents(Cell c) {
        c.pn_layout.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

            }
        });
        c.pn_layout.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

            }
        });
        c.pn_layout.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

            }
        });
        c.pn_layout.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

            }
        });
    }
}
