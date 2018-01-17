package sample;

import javafx.scene.layout.Pane;

public interface Grid_Intf {
    //TODO: add more as we go that are common...its a little weak right now

    void draw();

    Pane getPane();

    char checkForWin();

    void onWin(char winner);
}
