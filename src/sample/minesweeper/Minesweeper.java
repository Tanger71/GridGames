package sample.minesweeper;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import sample.ticTacToe.Field;

public class Minesweeper {

    public Stage stg_mine;
    public Scene scn_menu;
    public Scene scn_game;

    public int GAMEWIDTH = 500;
    public int GAMEHEIGHT = 580;

    MineField grid;

    public Minesweeper(){
        stg_mine = new Stage();
        scn_menu = new Scene(this.drawMenu());

        stg_mine.setTitle("TicTacToe");
        stg_mine.setScene(scn_menu);
        stg_mine.show();
    }

    public StackPane drawMenu(){
        StackPane rtn = new StackPane(new Rectangle(GAMEWIDTH, GAMEHEIGHT, Color.LIGHTGRAY));
        VBox pn_menuComp = new VBox();
        StackPane.setAlignment(pn_menuComp, Pos.CENTER_LEFT);
        Label lbl_title = new Label("Minesweeper");
        lbl_title.setFont(Font.font ("Verdana", FontWeight.BOLD, 50));
        pn_menuComp.setAlignment(Pos.CENTER);
        pn_menuComp.setSpacing(10);

        Button btn_tic = new Button("Play Minesweeper");
        btn_tic.setOnAction(value ->  {
            System.out.println("Changing scene...");
            drawGame();
        });

        pn_menuComp.getChildren().addAll(lbl_title, btn_tic);
        rtn.getChildren().addAll(pn_menuComp);
        return rtn;
    }

    public void drawGame(){
        grid = new MineField(10, 10, 20);
        grid.draw();

        //CenterPane
        Label lbl_title = new Label("Minesweeper");
        lbl_title.setFont(Font.font ("Verdana", FontWeight.BOLD, 50));
        lbl_title.setTranslateY(-200);
        Pane pn_grid = grid.getPane();
        pn_grid.setTranslateY(40);
        StackPane pn_gameGrid = new StackPane(new Rectangle(GAMEWIDTH, GAMEHEIGHT - 80, Color.LIGHTGRAY), pn_grid, lbl_title);

        //TopPane
        //btn_quit : quits application
        Button btn_quit = new Button("Quit");
        btn_quit.setOnAction(value ->  {
            System.out.println("Quitting...");
            System.exit(0);
        });
        //btn_close : changes scene to this.scn_close
        Button btn_close = new Button("Close Game");
        btn_close.setOnAction(value ->  {
            System.out.println("Closing game...");
            stg_mine.close();
        });
        //btn_restart : changes scene to this.scn_close
        Button btn_restart = new Button("Restart");
        btn_restart.setOnAction(value ->  {
            System.out.println("Restarting game...");
            drawGame();
        });
        HBox pn_toolbarLayout = new HBox(btn_quit, btn_close, btn_restart);
        pn_toolbarLayout.setPadding(new Insets(5, 5, 5, 5));
        pn_toolbarLayout.setSpacing(5);
        Rectangle rec_toolBarBG = new Rectangle(GAMEWIDTH, 40, Color.GRAY);
        Pane pn_toolbar = new Pane();
        pn_toolbar.getChildren().addAll(rec_toolBarBG, pn_toolbarLayout);

        //BottomPane

        Rectangle rec_textBarBG = new Rectangle(GAMEWIDTH, 40, Color.WHITE);
        Pane pn_textBar = new Pane();
        pn_textBar.getChildren().addAll(rec_textBarBG);

        //BorderPane comp
        BorderPane pn_layout = new BorderPane();
        pn_layout.setCenter(pn_gameGrid);
        pn_layout.setTop(pn_toolbar);

        Pane pn_rt = new Pane();
        pn_rt.getChildren().addAll(pn_layout);
        scn_game = new Scene(pn_rt);

        stg_mine.setScene(scn_game);
        stg_mine.show();
    }

}
