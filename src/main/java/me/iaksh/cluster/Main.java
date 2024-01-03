package me.iaksh.cluster;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {

    public static Stage coreTestStage;
    public static Stage trackViewStage;
    public static Stage sectionViewStage;

    private void initMainWindow(Stage primaryStage) throws IOException {
        Parent mainWindowRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/MainWindow.fxml")));
        primaryStage.setTitle("Main Window");
        primaryStage.setScene(new Scene(mainWindowRoot, 600, 400));
        primaryStage.setResizable(false);
    }

    private void initCoreTestWindow() throws IOException {
        Parent coreTestRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/CoreTestWindow.fxml")));
        coreTestStage = new Stage();
        coreTestStage.setTitle("Core Test Window");
        coreTestStage.setScene(new Scene(coreTestRoot, 420, 140));
        coreTestStage.setResizable(false);
    }

    private void initTrackView() throws IOException {
        Parent coreTestRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/TrackView.fxml")));
        trackViewStage = new Stage();
        trackViewStage.setTitle("Track View");
        trackViewStage.setScene(new Scene(coreTestRoot, 600, 400));
        trackViewStage.setResizable(false);
    }

    private void initSectionView() throws IOException {
        Parent coreTestRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/SectionView.fxml")));
        sectionViewStage = new Stage();
        sectionViewStage.setTitle("Section View");
        sectionViewStage.setScene(new Scene(coreTestRoot, 600, 400));
        sectionViewStage.setResizable(false);
    }

    @Override
    public void init() {
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        initMainWindow(primaryStage);
        initCoreTestWindow();
        initTrackView();
        initSectionView();
        primaryStage.show();
    }

    @Override
    public void stop() {
    }

    public static void main(String[] args) {
        launch(args);
    }
}