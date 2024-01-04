package me.iaksh.cluster;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import me.iaksh.cluster.ui.MainWindow;

import java.util.Objects;

public class Main extends Application {

    public static Stage primaryStage;

    @Override
    public void init() {
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent mainWindowRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/MainWindow.fxml")));
        primaryStage.setTitle("Editor");
        primaryStage.setScene(new Scene(mainWindowRoot, 1000, 600));
        // 以后解决布局问题了再允许拉伸
        primaryStage.setResizable(false);
        this.primaryStage = primaryStage;
        primaryStage.show();
    }

    @Override
    public void stop() {
        MainWindow.setLabelUpdateThreadShouldRunning(false);
    }

    public static void main(String[] args) {
        launch(args);
    }
}