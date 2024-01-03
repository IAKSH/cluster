package me.iaksh.cluster.ui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollBar;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import me.iaksh.cluster.Main;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class TrackViewController implements Initializable {
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

    //private ArrayList<>

    private void drawCanvas() {
        GraphicsContext gc = CanvasSquareWaveA.getGraphicsContext2D();
        gc.setFill(Color.RED);
        gc.fillRect(50,50,50,50);
    }

    public TrackViewController() {
        System.out.println("TrackViewController()");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        drawCanvas();
    }

    public void onCanvasSqAClick(MouseEvent mouseEvent) {
        System.out.println("onCanvasSqAClick()");
        if(mouseEvent.getButton() == MouseButton.PRIMARY) {
            Main.sectionViewStage.show();
        } else {
            System.out.println("secondary");
        }
    }

    public void onCanvasSqBClick(MouseEvent mouseEvent) {
        System.out.println("onCanvasSqBClick()");
        if(mouseEvent.getButton() == MouseButton.PRIMARY) {
            Main.sectionViewStage.show();
        } else {
            System.out.println("secondary");
        }
    }

    public void onCanvasTriClick(MouseEvent mouseEvent) {
        System.out.println("onCanvasTriClick()");
        if(mouseEvent.getButton() == MouseButton.PRIMARY) {
            Main.sectionViewStage.show();
        } else {
            System.out.println("secondary");
        }
    }

    public void onCanvasNoClick(MouseEvent mouseEvent) {
        System.out.println("onCanvasNoClick()");
        if(mouseEvent.getButton() == MouseButton.PRIMARY) {
            Main.sectionViewStage.show();
        } else {
            System.out.println("secondary");
        }
    }
}
