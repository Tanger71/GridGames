package sample.minesweeper;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import sample.Cell;
import sample.Grid_Intf;

import java.util.Random;

import static sample.minesweeper.Minesweeper.btn_restart;

public class MineField implements Grid_Intf {

    private final int WIDTH;
    private final int HEIGHT;
    private final int MINES;

    private int minesMarked = 0;

    private StackPane pn_rt = new StackPane();
    private Cell[][] cells;
    private GridPane pn_layout;
    private Insets inset = new Insets(1, 1, 1, 1);

    private boolean isFirstClick = true;

    //MineField() Constructor: inits final vars, fills this.cells array
    MineField(int w, int h, int m) {
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

    //draw() method: draws the grid UI
    @Override
    public void draw() {
        pn_layout = new GridPane();
        for (int i = 0; i < this.WIDTH; i++) {
            for (int j = 0; j < this.HEIGHT; j++) {
                pn_layout.add(this.cells[i][j].pn_rt, i, j);
                GridPane.setMargin(this.cells[i][j].pn_rt, this.inset);
                setMouseEvents(this.cells[i][j]);
            }
        }
        pn_layout.setAlignment(Pos.CENTER);
        this.pn_rt.getChildren().addAll(pn_layout);
    }

    //plantBombs() method: randomly assigns MineCell objects to this.cells array indexs after the first click,
    // Avoids placing on the first cell clicked or the 8 around it
    private void plantBombs(int notX, int notY) {
        boolean dontPlace = false;
        Random random = new Random();
        int minesPlaced = 0;
        while (minesPlaced <= this.MINES) {
            int x = random.nextInt(this.WIDTH);
            int y = random.nextInt(this.HEIGHT);
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    if (x + i < WIDTH && x + i >= 0 && y + j < HEIGHT && y + j >= 0) {
                        if (x == notX + i && y == notY + j) {
                            dontPlace = true;
                        }
                    }
                }
            }
            if (!(this.cells[x][y] instanceof MineCell) && !dontPlace) {
                this.cells[x][y].rec_btn.setStrokeWidth(0);
                this.cells[x][y].rec_btn.setFill(Color.TRANSPARENT);
                this.cells[x][y] = new MineCell(x, y);
                pn_layout.add(this.cells[x][y].pn_rt, x, y);
                GridPane.setMargin(this.cells[x][y].pn_rt, this.inset);
                setMouseEvents(this.cells[x][y]);

                minesPlaced++;
            }
            dontPlace = false;
        }
        System.out.println("Mines placed...");

    }

    //getPane() method: returns this.pn_rt
    @Override
    public Pane getPane() {
        return this.pn_rt;
    }

    @Override
    public char checkForWin() {
        return (char) (MINES - minesMarked + 48);
    }

    @Override
    public void onGameEnd(char winner) {
        btn_restart.setStyle("fx-background-color: #89e5ff");
        System.out.print("Therer are " + winner + " mines left!");
    }

    private void onGameEnd() { // Overloaded method
        btn_restart.setStyle("fx-background-color: #ff7c7c");
        System.out.print("You lose...");
    }

    private void setMouseEvents(Cell c) {
        c.pn_layout.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (!c.uncoverd) {
                    c.rec_btn.setStrokeWidth(2);
                    c.rec_btn.setWidth(34);
                    c.rec_btn.setHeight(34);
                }
            }
        });
        c.pn_layout.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (!c.uncoverd) {
                    c.rec_btn.setStrokeWidth(1);
                    c.rec_btn.setWidth(36);
                    c.rec_btn.setHeight(36);
                }
            }
        });
        c.pn_layout.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (!c.uncoverd) {
                    c.rec_btn.setFill(Color.GRAY);
                }
            }
        });
        c.pn_layout.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getButton() == MouseButton.SECONDARY) {
                    if (c.isFlagged) {
                        c.unflag();
                        minesMarked--;
                    } else {
                        c.flag();
                        minesMarked++;
                    }
                    c.rec_btn.setFill(Color.DARKGRAY);
                } else {
                    if (!c.uncoverd) {
                        c.rec_btn.setStrokeWidth(1);
                        c.rec_btn.setWidth(36);
                        c.rec_btn.setHeight(36);
                        if (isFirstClick) {
                            isFirstClick = false;
                            plantBombs(c.posX, c.posY);
                            for (int i = 0; i < WIDTH; i++) {
                                for (int j = 0; j < HEIGHT; j++) {
                                    if (!(cells[i][j] instanceof MineCell)) {
                                        ((OpenCell) cells[i][j]).setState(Integer.toString(calcMinesNear(cells[i][j])));
                                    }
                                }
                            }
                        }
                        if (c instanceof MineCell) {
                            onclickMine((MineCell) c); //Downcast
                        } else {
                            onclickOpen((OpenCell) c); //Downcast
                        }
                        if (checkForWin() == 0) {
                            onGameEnd(checkForWin());
                        }
                        System.out.println("clicked on " + c.posX + " " + c.posY);
                    }
                }

            }
        });
    }

    private void onclickMine(MineCell c) {
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                if (!cells[i][j].uncoverd) {
                    cells[i][j].uncover();
                    cells[i][j].unflag();
                }
            }
        }
        c.cir_bomb.setStroke(Color.DARKRED);
        c.cir_bomb.setStrokeWidth(1);
        c.pn_layout.getChildren().add(c.cir_bomb);
        onGameEnd();
    }

    private void onclickOpen(OpenCell c) {
        System.out.println("Open!");
        c.uncover();
    }

    private int calcMinesNear(Cell c) {
        int x = c.posX;
        int y = c.posY;
        int mineCount = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (x + i < WIDTH && x + i >= 0 && y + j < HEIGHT && y + j >= 0) {
                    if (cells[x + i][y + j] instanceof MineCell) {
                        mineCount++;
                    }
                }
            }
        }
        return mineCount;
    }
}
