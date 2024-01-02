package me.iaksh.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MainWindow extends Application {

    private Logger logger;

    @Override
    public void start(Stage stage) {
        String javaVersion = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");
        Label l1 = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");
        Scene scene = new Scene(new FlowPane(l1), 640, 480);
        stage.setScene(scene);
        stage.setTitle("C8ME");
        stage.show();
    }

    @Override
    public void init() throws Exception {
        logger = Logger.getLogger("MainWindow");
        logger.log(Level.INFO,String.format("init() from %s",Thread.currentThread().getName()));
    }

    @Override
    public void stop() throws Exception {
        logger.log(Level.INFO,String.format("stop() from %s",Thread.currentThread().getName()));
    }

    public void exec() {
        launch();
    }

}