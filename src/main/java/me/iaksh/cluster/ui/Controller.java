package me.iaksh.cluster.ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import me.iaksh.cluster.Main;
import me.iaksh.cluster.core.CoreTestcase;
import me.iaksh.cluster.core.mixer.Exporter;
import me.iaksh.cluster.core.mixer.NESLikeSynthesizer;
import me.iaksh.cluster.core.player.Player;

public class Controller {
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
    @FXML
    public Button buttonShowTestWindow;
    @FXML
    public Button showTrackViewButton;
    @FXML
    public ScrollBar ScrollbarNoiseWave;
    @FXML
    public Canvas CanvasNoiseWave;
    @FXML
    public ScrollBar ScrollbarTriangleWave;
    @FXML
    public Canvas CanvasTriangleWave;
    @FXML
    public ScrollBar ScrollBarSquareWaveB;
    @FXML
    public Canvas CanvasSquareWaveB;
    @FXML
    public ScrollBar ScrollBarSquareWaveA;
    @FXML
    public Canvas CanvasSquareWaveA;

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

    @FXML
    public void onShowTestWindowButtonClick(ActionEvent actionEvent) {
        Main.coreTestStage.show();
    }

    private void setSliderDisable(boolean b) {
        volumeSlider.setDisable(b);
        bpmSlider.setDisable(b);
    }

    public void onShowTrackViewButtonClick(ActionEvent actionEvent) {
        Main.trackViewStage.show();
    }
}
