package me.iaksh.cluster.ui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import me.iaksh.cluster.core.mixer.NESLikeSynthesizer;
import me.iaksh.cluster.core.player.Player;
import me.iaksh.cluster.core.CoreTestcase;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainWindow extends Application {
    public Button playButton;
    public TextArea savePathInputArea;
    public Button saveButton;

    private final Logger logger;
    private final int bpm = 120;

    public MainWindow() {
        logger = Logger.getLogger("MainWindow");
    }

    public void saveButtonOnClick(ActionEvent actionEvent) {
        int bpm = 120;
        String path = savePathInputArea.getText();
        logger.log(Level.INFO,String.format("\"saving to \"%s\")",path));
        new NESLikeSynthesizer(bpm).saveToWav(path,new CoreTestcase().genTestSection());
    }

    public void playButtonOnClick(ActionEvent actionEvent) {
        String path = savePathInputArea.getText();
        logger.log(Level.INFO,"playing core test case");
        new Player(0.1f).play(0.25f,new NESLikeSynthesizer(bpm).genWavform(new CoreTestcase().genTestSection()));
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/MainWindow.fxml")));
        primaryStage.setTitle("test");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }

    public static void start(String[] args) {
        launch(args);
    }
}
