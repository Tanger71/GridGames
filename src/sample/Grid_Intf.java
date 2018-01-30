package sample;

import javafx.scene.layout.Pane;

public interface Grid_Intf {

    void draw();

    Pane getPane();

    char checkForWin();

    void onGameEnd(char winner);
}
