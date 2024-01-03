package me.iaksh.cluster.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import me.iaksh.cluster.core.CoreTestcase;
import me.iaksh.cluster.core.mixer.Exporter;
import me.iaksh.cluster.core.mixer.NESLikeSynthesizer;
import me.iaksh.cluster.core.player.Player;

public class CoreTestWindowController {
    @FXML
    public Button buttonPlayCoreTest;
    @FXML
    public Button buttonExportCoreTest;
    @FXML
    public TextField textFieldExportPath;
    @FXML
    public Slider volumeSlider;
    @FXML
    public Slider bpmSlider;

    private void setSliderDisable(boolean b) {
        volumeSlider.setDisable(b);
        bpmSlider.setDisable(b);
    }

    @FXML
    public void onPlayCoreTestButtonClick(ActionEvent actionEvent) {
        Thread t = new Thread(() -> {
            buttonPlayCoreTest.setDisable(true);
            setSliderDisable(true);
            Exporter exporter = new NESLikeSynthesizer((int) bpmSlider.getValue());
            new Player((float) volumeSlider.getValue()).play(exporter.genWaveform(new CoreTestcase().genTestSection()));
            buttonPlayCoreTest.setDisable(false);
            setSliderDisable(false);
        });
        t.start();
    }

    @FXML
    public void onExportCoreTestButtonClick(ActionEvent actionEvent) {
        String path = textFieldExportPath.getText();
        Thread t = new Thread(() -> {
            buttonExportCoreTest.setDisable(true);
            setSliderDisable(true);
            Exporter exporter = new NESLikeSynthesizer((int) bpmSlider.getValue());
            exporter.saveWaveform(path,exporter.genWaveform(new CoreTestcase().genTestSection()));
            buttonExportCoreTest.setDisable(false);
            setSliderDisable(false);
        });
        t.start();
    }
}
