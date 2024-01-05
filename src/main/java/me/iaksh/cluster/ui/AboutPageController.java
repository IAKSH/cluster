package me.iaksh.cluster.ui;

import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import me.iaksh.cluster.core.CoreTestcase;

public class AboutPageController {

    public ImageView aboutPageImage;

    public void onAboutPageImageClick(MouseEvent mouseEvent) {
        new CoreTestcase().play();
    }
}
