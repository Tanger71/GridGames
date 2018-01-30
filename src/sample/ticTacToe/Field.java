package sample.ticTacToe;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import sample.Grid_Intf;

public class Field implements Grid_Intf {

    private int WIDTH;
    private int HIEGHT;
    private StackPane pn_rt = new StackPane();
    private TicCell[][] cells;
    private Insets inset = new Insets(10, 10, 10, 10);

    private boolean gameInProgress = true;

    static String outputMessage = "Player X's turn";

    //Field() Constructor: inits
    Field(int w, int h) {
        this.WIDTH = w;
        this.HIEGHT = h;
        cells = new TicCell[WIDTH][HIEGHT];
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HIEGHT; j++) {
                cells[i][j] = new TicCell(i, j);
            }
        }
    }

    @Override
    //draw() method: draws GUI for the game grid
    public void draw() {
        GridPane pn_layout = new GridPane();
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HIEGHT; j++) {
                GridPane.setMargin(cells[i][j].pn_rt, inset);
                pn_layout.add(cells[i][j].pn_rt, i, j);
                setMouseEvents(cells[i][j]);
            }
        }
        pn_layout.setAlignment(Pos.CENTER);
        pn_rt.getChildren().addAll(pn_layout);
    }

    @Override
    //getPane() method: returns this.pn_rt
    public Pane getPane() {
        return this.pn_rt;
    }

    @Override
    public char checkForWin() {
        System.out.println("Checking for win...");
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < WIDTH; j++) {
                System.out.print(cells[j][i].state + " ");
            }
            System.out.print("\n");
        }
        for (int i = 0; i < WIDTH; i++) {
            if (cells[i][0].state == cells[i][1].state && cells[i][1].state == cells[i][2].state && cells[i][0].state != '-')
                return cells[i][0].state;
            if (cells[0][i].state == cells[1][i].state && cells[1][i].state == cells[2][i].state && cells[i][0].state != '-')
                return cells[0][i].state;
        }
        if (cells[0][0].state == cells[1][1].state && cells[1][1].state == cells[2][2].state && cells[0][0].state != '-')
            return cells[0][0].state;
        if (cells[2][0].state == cells[1][1].state && cells[1][1].state == cells[0][2].state && cells[2][0].state != '-')
            return cells[2][0].state;
        return '-';
    }

    private void setMouseEvents(TicCell c) {
        c.pn_layout.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (gameInProgress) {
                    c.rec_btn.setStrokeWidth(4);
                    c.rec_btn.setWidth(97);
                    c.rec_btn.setHeight(97);
                }
            }
        });
        c.pn_layout.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (gameInProgress) {
                    c.rec_btn.setStrokeWidth(1);
                    c.rec_btn.setWidth(100);
                    c.rec_btn.setHeight(100);
                }
            }
        });
        c.pn_layout.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (gameInProgress) {
                    c.rec_btn.setFill(Color.GRAY);
                }
            }
        });
        c.pn_layout.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (gameInProgress) {
                    c.rec_btn.setFill(Color.DARKGRAY);
                    if (c.state == '-') {
                        if (TicCell.turn) {
                            c.setState('X');
                            TicCell.turn = false;
                            outputMessage = "Player O's turn";
                        } else {
                            c.setState('O');
                            TicCell.turn = true;
                            outputMessage = "Player X's turn";
                        }
                    }
                    if (checkForWin() == 'X') {
                        outputMessage = "Player X's wins!";
                        onGameEnd('X');
                    } else if (checkForWin() == 'O') {
                        outputMessage = "Player O's wins!";
                        onGameEnd('O');
                    }
                    TicTacToe.updateMessage();

                }
            }
        });
    }

    @Override
    public void onGameEnd(char winner) {
        gameInProgress = false;
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HIEGHT; j++) {
                cells[i][j].rec_btn.setStrokeWidth(1);
            }
        }
    }
}
