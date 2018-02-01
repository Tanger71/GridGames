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

import java.util.ArrayList;
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

    /**
     * inits final vars, fills this.cells array
     *
     * @param w int width of board
     * @param h int height of board
     * @param m int number of mines
     */
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

    /**
     * draws the grid UI
     */
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

    /**
     * Randomly assigns MineCell objects to this.cells array indexs after the first click,
     * Avoids placing on the first cell clicked or the 8 around it
     *
     * @param notX x Value where not to generate a mine
     * @param notY y Value where not to generate a mine
     */
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

    /**
     * @return pane of grid field
     */
    @Override
    public Pane getPane() {
        return this.pn_rt;
    }

    /**
     * @return the difference in mines marked to minesTotal
     */
    @Override
    public char checkForWin() {
        System.out.println("WinCheck: " + (MINES - minesMarked));
        return (char) (MINES - minesMarked + 47);
    }

    /**
     * outputs win statement to consol
     *
     * @param winner number of mines left on board
     */
    @Override
    public void onGameEnd(char winner) {
        btn_restart.setStyle("fx-background-color: #89e5ff");
        System.out.print("Therer are " + winner + " mines left!");
    }

    /**
     * outputs lose statement to consol
     */
    private void onGameEnd() { // Overloaded method
        btn_restart.setStyle("fx-background-color: #ff7c7c");
        System.out.print("You lose...");
    }

    /**
     * @param c cell to implement MouseEvents to
     */
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
                if (!c.isFlagged){
                    if (!c.uncoverd) {
                        c.rec_btn.setFill(Color.GRAY);
                    }
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
                    if (!c.isFlagged){
                        if (!c.uncoverd) {
                            c.rec_btn.setStrokeWidth(1);
                            c.rec_btn.setWidth(36);
                            c.rec_btn.setHeight(36);
                            if (isFirstClick) {
                                isFirstClick = false;
                                plantBombs(c.posX, c.posY);
                                System.out.println("calcminesnear");
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
                            if (checkForWin() == '0') {
                                onGameEnd(checkForWin());
                            }
                            System.out.println("clicked on " + c.posX + " " + c.posY);
                        }
                    }
                }
            }
        });
    }

    /**
     * UI for when mine is clicked
     *
     * @param c cell to pull GUI elements from
     */
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
        onGameEnd();
    }

    /**
     * UI for when open cell is clicked
     *
     * @param c cell to pull GUI elements from
     */
    private void onclickOpen(OpenCell c) {
        c.uncover();
        autoUncover(c);
    }

    /**
     * finds number of mines around clicked cell
     *
     * @param c cell to check for mine proximity
     * @return number of mines around c
     */
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

    /**
     * automatically uncovers cells and its adjacent cells that have values of zero
     * some help from Felix's code
     *
     * @param c
     */
    private void autoUncover(Cell c) { //TODO: FINISH!!!
        System.out.println("uncovering...");
        ArrayList<BoardPosition> toBeChecked = new ArrayList<>();
        if(c.cellState == 0){
            toBeChecked.add(new BoardPosition(c.posX, c.posY));
            while (!toBeChecked.isEmpty()) {
                BoardPosition checkingNow = toBeChecked.remove(0);
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        if (checkingNow.getX() + i < WIDTH && checkingNow.getX() + i >= 0 && checkingNow.getY() + j < HEIGHT && checkingNow.getY() + j >= 0) {
                            if(cells[checkingNow.getX() + i][checkingNow.getY() + j].cellState == 0 && !cells[checkingNow.getX() + i][checkingNow.getY() + j].uncoverd){
                                toBeChecked.add(new BoardPosition(checkingNow.getX() + i, checkingNow.getY() + j));
                            }
                            cells[checkingNow.getX() + i][checkingNow.getY() + j].uncover();
                        }
                    }
                }

            }
        }


    }
}
