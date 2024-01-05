package me.iaksh.cluster.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FXApplication  extends Application {
    private static FXApplication app;
    private boolean running;
    private Stage primaryStage;
    private Stage aboutPageStage;
    private Stage noteEditPageStage;
    private MainWindowController mainWindowController;
    private NoteEditPageController noteEditPageController;
    private AboutPageController aboutPageController;
    private Logger logger;

    private void initPrimaryStage(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/MainWindow.fxml"));
        Parent root = loader.load();
        mainWindowController = loader.getController();
        stage.setTitle("cluster 8bit music editor");
        stage.setScene(new Scene(root, 1000, 600));
        this.primaryStage = stage;
    }

    private void initAboutPage() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/AboutPage.fxml"));
        Parent root = loader.load();
        aboutPageController = loader.getController();
        aboutPageStage = new Stage();
        aboutPageStage.setTitle("关于");
        aboutPageStage.setScene(new Scene(root, 400, 200));
        aboutPageStage.setResizable(false);
    }

    private void initNoteEditPage() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("fxml/NoteEditPage.fxml"));
        Parent root = loader.load();
        noteEditPageController = loader.getController();
        noteEditPageStage = new Stage();
        noteEditPageStage.setTitle("编辑音符");
        noteEditPageStage.setScene(new Scene(root, 300, 200));
        noteEditPageStage.setResizable(false);
    }

    private void setExceptionHandler() {
        logger = Logger.getLogger("FXApplication");
        Thread.setDefaultUncaughtExceptionHandler((thread,throwable) -> {
            logger.log(Level.WARNING,String.format("Exception!\n---\nMessage:\n%s",throwable.getMessage()));
            throwable.printStackTrace();
        });
    }

    @Override
    public void init() {
        app = this;
        running = true;
        setExceptionHandler();
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        initPrimaryStage(primaryStage);
        initAboutPage();
        initNoteEditPage();
        primaryStage.show();
    }

    @Override
    public void stop() {
        running = false;
    }

    public static void runApp() {
        Application.launch();
    }

    public boolean isRunning() {
        return running;
    }

    public Stage getAboutPageStage() {
        return aboutPageStage;
    }

    public Stage getNoteEditPageStage() {
        return noteEditPageStage;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public AboutPageController getAboutPageController() {
        return aboutPageController;
    }

    public MainWindowController getMainWindowController() {
        return mainWindowController;
    }

    public NoteEditPageController getNoteEditPageController() {
        return noteEditPageController;
    }

    public static FXApplication getInstance() {
        return app;
    }
}
