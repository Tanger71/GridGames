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

    public int minesMarked = 0;

    public StackPane pn_rt = new StackPane();
    public Cell[][] cells;
    public GridPane pn_layout;
    public Insets inset = new Insets(1, 1, 1, 1);

    public boolean isFirstClick = true;

    public MineField(int w, int h, int m) {
        this.WIDTH = w;
        this.HEIGHT = h;
        this.MINES = m;
        this.cells = new Cell[WIDTH][HEIGHT];
        for (int i = 0; i < this.WIDTH; i++) {
            for (int j = 0; j < this.HEIGHT; j++) {
                this.cells[i][j] = new OpenCell(i, j);
            }
        }
    }

    @Override
    public void draw() {
        //TODO: GUI
        pn_layout = new GridPane();
        for(int i = 0 ; i < this.WIDTH ; i++){
            for(int j  = 0 ; j < this.HEIGHT ; j++){
                pn_layout.add(this.cells[i][j].pn_rt, i, j);
                pn_layout.setMargin(this.cells[i][j].pn_rt, this.inset);
                setMouseEvents(this.cells[i][j]);
            }
        }
        pn_layout.setAlignment(Pos.CENTER);
        this.pn_rt.getChildren().addAll(pn_layout);
    }

    public void plantBombs(int notX, int notY) {
        boolean dontPlace = false;
        Random random = new Random();
        int minesPlaced = 0;
        while (minesPlaced <= this.MINES) {
            int x = random.nextInt(this.WIDTH);
            int y = random.nextInt(this.HEIGHT);
            for(int i = -1 ; i <= 1 ; i++){
                for(int j = -1 ; j <= 1 ; j++){
                    if(x + i < WIDTH && x + i >= 0 && y + j < HEIGHT && y + j >= 0) {
//                        System.out.println((notX + i) + " " + (notY + j));
                        if(x == notX + i && y == notY + j){
                            dontPlace = true;
                        }
                    }
                }
            }
            if (!(this.cells[x][y] instanceof MineCell) && !dontPlace) {
                this.cells[x][y] = new MineCell(x, y);
                pn_layout.add(this.cells[x][y].pn_rt, x, y);
                pn_layout.setMargin(this.cells[x][y].pn_rt, this.inset);
                setMouseEvents(this.cells[x][y]);

                minesPlaced++;
            }
            dontPlace = false;
        }
        System.out.println("Mines placed...");

    }

    @Override
    public Pane getPane() {
        return pn_rt;
    }

    @Override
    public char checkForWin() {
        if(minesMarked == MINES){
//            System.out.println((char)(MINES - minesMarked + 48) + " Mines left.");
            return (char)(MINES - minesMarked + 48);
        }
        return '0';
    }

    @Override
    public void onWin(char winner) {

    }

    public void setMouseEvents(Cell c) {
        c.pn_layout.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(!c.uncoverd){
                    c.rec_btn.setStrokeWidth(2);
                    c.rec_btn.setWidth(34);
                    c.rec_btn.setHeight(34);
                }
            }
        });
        c.pn_layout.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(!c.uncoverd){
                    c.rec_btn.setStrokeWidth(1);
                    c.rec_btn.setWidth(36);
                    c.rec_btn.setHeight(36);
                }
            }
        });
        c.pn_layout.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(!c.uncoverd){
                    c.rec_btn.setFill(Color.GRAY);
                }
            }
        });
        c.pn_layout.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(!c.uncoverd){
                    c.uncoverd = true;
                    c.rec_btn.setStrokeWidth(1);
                    c.rec_btn.setWidth(36);
                    c.rec_btn.setHeight(36);
                    if(isFirstClick){
                        isFirstClick = false;
                        plantBombs(c.posX, c.posY);
                        for(int i = 0 ; i < WIDTH ; i++){
                            for(int j  = 0 ; j < HEIGHT ; j++){
                                if (!(cells[i][j] instanceof MineCell)){
                                    ((OpenCell)cells[i][j]).setState(Integer.toString(calcMinesNear(cells[i][j])));
                                }
                            }
                        }
                    }
                }
                System.out.println("clicked on " + c.posX + " " + c.posY);
                if(c instanceof MineCell){
                    onclickMine((MineCell) c); //Downcast
                }else{
                    onclickOpen((OpenCell) c); //Downcast
                }

            }
        });
    }

    public void onclickMine(MineCell c){
        System.out.println("Game Over.");
        for(int i = 0 ; i < WIDTH ; i++){
            for(int j  = 0 ; j < HEIGHT ; j++){
                if (!cells[i][j].uncoverd){
                    cells[i][j].uncover();
                }
            }
        }
    }

    public void onclickOpen(OpenCell c){
        c.uncover();
    }

    public int calcMinesNear(Cell c){
        int x = c.posX;
        int y = c.posY;
        int mineCount = 0;
        for(int i = -1 ; i <= 1 ; i++){
            for(int j = -1 ; j <= 1 ; j++){
                if(x + i < WIDTH && x + i >= 0 && y + j < HEIGHT && y + j >= 0) {
                    if (cells[x + i][y + j] instanceof MineCell) {
                        mineCount++;
                    }
                }
            }
        }
//        System.out.println(mineCount);
        return mineCount;
    }

    public void autoUncover(int x, int y){

    }

}
