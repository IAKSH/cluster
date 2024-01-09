package me.iaksh.cluster.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class VBPMSettingPageController {

    public Button saveButton;
    public TextField volumeTextField;
    public TextField bpmTextField;

    private int getVolumeInput() {
        int input = Integer.parseInt(volumeTextField.getText());
        if(input < 0 || input > 100)
            throw new IllegalArgumentException();
        return input;
    }

    private int getBPMInput() {
        int input = Integer.parseInt(bpmTextField.getText());
        if(input <= 0 || input > 400)
            throw new IllegalArgumentException();
        return input;
    }

    @FXML
    public void onSaveButtonClick(ActionEvent actionEvent) {
        MainWindowController controller = FXApplication.getInstance().getMainWindowController();
        if(!bpmTextField.getText().isEmpty())
            controller.bpmSlider.setValue(getBPMInput());
        if(!volumeTextField.getText().isEmpty())
            controller.volumeSlider.setValue(getVolumeInput());
        FXApplication.getInstance().getVbpmSettingPageStage().hide();
    }
}
