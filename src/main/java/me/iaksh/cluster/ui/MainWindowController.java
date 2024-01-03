package me.iaksh.cluster.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import me.iaksh.cluster.Main;

public class MainWindowController {

    @FXML
    public Button buttonShowTestWindow;

    @FXML
    public void onShowTestWindowButtonClick(ActionEvent actionEvent) {
        Main.coreTestStage.show();
    }
}
