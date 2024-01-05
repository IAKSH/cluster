package me.iaksh.cluster;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {

    public static boolean closing;
    public static Stage primaryStage;
    public static Stage aboutPageStage;
    public static Stage noteEditPageStage;

    private void initPimaryStage(Stage primaryStage) throws IOException {
        Parent mainWindowRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/MainWindow.fxml")));
        primaryStage.setTitle("cluster 8bit music editor");
        primaryStage.setScene(new Scene(mainWindowRoot, 1000, 600));
        this.primaryStage = primaryStage;
    }

    private void initAboutPage() throws IOException {
        Parent aboutPageRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/AboutPage.fxml")));
        aboutPageStage = new Stage();
        aboutPageStage.setTitle("关于");
        aboutPageStage.setScene(new Scene(aboutPageRoot, 400, 200));
        aboutPageStage.setResizable(false);
        this.aboutPageStage = aboutPageStage;
    }

    private void initNoteEditPage() throws IOException {
        Parent aboutPageRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("fxml/NoteEditPage.fxml")));
        noteEditPageStage = new Stage();
        noteEditPageStage.setTitle("编辑音符");
        noteEditPageStage.setScene(new Scene(aboutPageRoot, 300, 200));
        noteEditPageStage.setResizable(false);
        this.noteEditPageStage = noteEditPageStage;
    }

    @Override
    public void init() {
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        initPimaryStage(primaryStage);
        initAboutPage();
        initNoteEditPage();
        primaryStage.show();
    }

    @Override
    public void stop() {
        closing = true;
    }

    public static void main(String[] args) {
        launch(args);
    }
}