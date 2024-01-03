package me.iaksh.cluster.ui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import me.iaksh.cluster.core.mixer.Exporter;
import me.iaksh.cluster.core.mixer.NESLikeSynthesizer;
import me.iaksh.cluster.core.player.Player;
import me.iaksh.cluster.core.CoreTestcase;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainWindow extends Application {
    public TextArea savePathInputArea;
    public Button saveButton;
    public Button playButton;

    private final Logger logger;
    private final int bpm = 120;

    public MainWindow() {
        logger = Logger.getLogger("MainWindow");
    }

    public void saveButtonOnClick(ActionEvent actionEvent) {
        int bpm = 120;
        String path = savePathInputArea.getText();
        logger.log(Level.INFO,String.format("\"saving to \"%s\")",path));
        Thread t = new Thread(() -> {
            saveButton.setDisable(true);
            Exporter exporter = new NESLikeSynthesizer(bpm);
            exporter.saveWaveform(path,exporter.genWaveform(new CoreTestcase().genTestSection()));
            saveButton.setDisable(false);
        });
        t.start();
    }

    public void playButtonOnClick(ActionEvent actionEvent) {
        String path = savePathInputArea.getText();
        logger.log(Level.INFO,"playing core test case");
        Thread t = new Thread(() -> {
            playButton.setDisable(true);
            Exporter exporter = new NESLikeSynthesizer(bpm);
            new Player(0.1f).play(0.25f,exporter.genWaveform(new CoreTestcase().genTestSection()));
            playButton.setDisable(false);
        });
        t.start();
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/MainWindow.fxml")));
        primaryStage.setTitle("test");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

    public static void start(String[] args) {
        launch(args);
    }
}
