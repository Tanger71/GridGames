package sample;

import javafx.application.Application;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import sample.minesweeper.Minesweeper;
import sample.snakesAndLadders.Player;
import sample.snakesAndLadders.SnakesAndLadders;
import sample.ticTacToe.TicTacToe;

import java.util.ArrayList;

/**
 * Collection of Grid-Based Games
 * - TicTacToe
 * - Minesweeper
 * - SnakesAndLadders
 *
 * Created by:
 * Sawyer Tang (TicTacToe, Minesweeper) 1/29/2018
 * Naod Dereje (SnakesAndLadders)
 */

public class Main extends Application {

    @Override
    //start() method: implements stg_main with scn_menu
    public void start(Stage stg_main) throws Exception {
        Scene scn_menu = new Scene(drawMenu());
        stg_main.setTitle("Menu");
        stg_main.setScene(scn_menu);
        stg_main.show();
    }

    //drawMenu() method: draws GUI for main menu
    private StackPane drawMenu() {
        StackPane rt = new StackPane(new Rectangle(400, 200, Color.LIGHTGRAY));
        VBox pn_menuComp = new VBox();
        StackPane.setAlignment(pn_menuComp, Pos.CENTER_LEFT);
        Label lbl_title = new Label("Grid Games");
        lbl_title.setFont(Font.font("Verdana", FontWeight.BOLD, 50));
        pn_menuComp.setAlignment(Pos.CENTER);
        pn_menuComp.setSpacing(10);

        Button btn_tic = new Button("Play TicTacToe");
        btn_tic.setOnAction(value -> {
            System.out.println("Creating ticTacToe...");
            new TicTacToe();
        });
        Button btn_snakes = new Button("Play Snakes and Ladders");
        btn_snakes.setOnAction(value -> {
            System.out.println("Creating snakesAndLadders...");
            new SnakesAndLadders(new ArrayList<Player>());
        });
        Button btn_minesweeper = new Button("Play Minesweeper");
        btn_minesweeper.setOnAction(value -> {
            System.out.println("Creating Minesweeper...");
            new Minesweeper();
        });
        pn_menuComp.getChildren().addAll(lbl_title, btn_tic, btn_snakes, btn_minesweeper);
        rt.getChildren().addAll(pn_menuComp);
        return rt;
    }


    public static void main(String[] args) {
        launch(args);
    }
}
