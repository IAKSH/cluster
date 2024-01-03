package me.iaksh.cluster;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {

    public static Stage coreTestStage;

    private void initMainWindow(Stage primaryStage) throws IOException {
        Parent mainWindowRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/MainWindow.fxml")));
        primaryStage.setTitle("test");
        primaryStage.setScene(new Scene(mainWindowRoot, 600, 400));
    }

    private void initCoreTestWindow() throws IOException {
        Parent mainWindowRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/CoreTestWindow.fxml")));
        coreTestStage = new Stage();
        coreTestStage.setTitle("test");
        coreTestStage.setScene(new Scene(mainWindowRoot, 400, 200));
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        initMainWindow(primaryStage);
        initCoreTestWindow();
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}