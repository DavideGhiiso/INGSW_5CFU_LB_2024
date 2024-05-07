package it.polimi.ingsw.view.viewcontroller.transitions;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class TablePlacementTransition {
    private final TranslateTransition translateTransition;
    public TablePlacementTransition(ImageView image, double startingX, double startingY, double duration) {
        translateTransition = new TranslateTransition();
        translateTransition.setNode(image);
        translateTransition.setDuration(Duration.millis(duration));
        translateTransition.setFromX(image.getX() + startingX);
        translateTransition.setFromY(image.getY() + startingY);
        translateTransition.setToX(image.getX());
        translateTransition.setToY(image.getY());
        translateTransition.setCycleCount(1);
        translateTransition.setAutoReverse(false);
    }

    public void play() {
        translateTransition.play();
    }
    public void setOnFinished(EventHandler<ActionEvent> eventHandler) {
        translateTransition.setOnFinished(eventHandler);
    }
}
