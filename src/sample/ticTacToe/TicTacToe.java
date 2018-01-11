package sample.ticTacToe;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class TicTacToe {
    public Stage stg_ttt;
    public Scene scn_menu;
    public Scene scn_game;

    public TicTacToe(){
        stg_ttt = new Stage();
        scn_menu = new Scene(drawMenu());

        stg_ttt.setTitle("TicTacToe");
        stg_ttt.setScene(scn_menu);
        stg_ttt.show();
    }

    public StackPane drawMenu(){
        StackPane rtn = new StackPane(new Rectangle(500, 500, Color.LIGHTGRAY));
        VBox pn_menuComp = new VBox();
        StackPane.setAlignment(pn_menuComp, Pos.CENTER_LEFT);
        Label lbl_title = new Label("Tic Tac Toe");
        lbl_title.setFont(Font.font ("Verdana", FontWeight.BOLD, 50));
        pn_menuComp.setAlignment(Pos.CENTER);
        pn_menuComp.setSpacing(10);

        Button btn_tic = new Button("Play TicTacToe");
        btn_tic.setOnAction(value ->  {
            System.out.println("Changing scene...");
            drawGame();
        });

        pn_menuComp.getChildren().addAll(lbl_title, btn_tic);
        rtn.getChildren().addAll(pn_menuComp);
        return rtn;
    }

    public void drawGame(){
        BorderPane pn_layout = new BorderPane();
        Pane pn_rt = new Pane();
        scn_game = new Scene(pn_rt);
        pn_rt.getChildren().addAll(new Rectangle(400, 200, Color.LIGHTGRAY), pn_layout);
        scn_game = new Scene(pn_layout);

        Field grid = new Field(3, 3);

        stg_ttt.setScene(scn_menu);
        stg_ttt.show();
    }

}