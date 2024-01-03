package me.iaksh.cluster.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import me.iaksh.cluster.Main;

public class MainWindowController {
    @FXML
    public Button buttonShowTestWindow;
    @FXML
    public Button showTrackViewButton;

    @FXML
    public void onShowTestWindowButtonClick(ActionEvent actionEvent) {
        Main.coreTestStage.show();
    }
    public void onShowTrackViewButtonClick(ActionEvent actionEvent) {
        Main.trackViewStage.show();
    }

    public MainWindowController() {
        System.out.println("MainWindowController()");
    }
}
