package it.polimi.ingsw.view.viewcontroller.transitions;

import it.polimi.ingsw.view.View;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import java.net.URL;

public class GroupCardTransition {
    private final TranslateTransition translateTransition;
    private boolean onFinishedSet = false;
    public GroupCardTransition(ImageView image, double endingX, double endingY, double duration) {
        translateTransition = new TranslateTransition();
        translateTransition.setNode(image);
        translateTransition.setDuration(Duration.millis(duration));
        translateTransition.setToX(image.getX() + endingX);
        translateTransition.setToY(image.getY() + endingY);
        translateTransition.setCycleCount(1);
        translateTransition.setAutoReverse(false);
    }
    public void play() {
        translateTransition.play();
    }

    public void setOnFinished(EventHandler<ActionEvent> eventHandler) {
        if(!onFinishedSet) {
            translateTransition.setOnFinished(eventHandler);
            onFinishedSet = true;
        }
    }
}
