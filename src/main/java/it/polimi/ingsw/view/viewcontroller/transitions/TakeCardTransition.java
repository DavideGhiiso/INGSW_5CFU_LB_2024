package it.polimi.ingsw.view.viewcontroller.transitions;

import it.polimi.ingsw.view.View;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.net.URL;

/**
 * Transition wrapper class that, given an ImageView, animates it as if it was taken e.g. it rotates it by showing the back
 */
public class TakeCardTransition {
    private final ScaleTransition shrinkTransition;
    private final ScaleTransition growTransition;
    private boolean onFinishedSet = false;
    private final ImageView image;
    public TakeCardTransition(ImageView image, double duration) {
        this.image = image;
        double initialScale = image.getScaleX();
        shrinkTransition = new ScaleTransition();
        shrinkTransition.setNode(image);
        growTransition = new ScaleTransition();
        growTransition.setNode(image);
        shrinkTransition.setDuration(Duration.millis(duration/3));
        growTransition.setDuration(Duration.millis(duration*2/3));
        shrinkTransition.setToX(0);
        growTransition.setToX(initialScale);
        shrinkTransition.setCycleCount(1);
        growTransition.setCycleCount(1);
        shrinkTransition.setAutoReverse(false);
        growTransition.setAutoReverse(false);
    }
    public void play() {
        URL backUrl = View.class.getResource("images/back.png");
        shrinkTransition.setOnFinished(e -> {
            image.setImage(new Image(String.valueOf(backUrl)));
            growTransition.play();
        });
        shrinkTransition.play();
    }

    public void setOnFinished(EventHandler<ActionEvent> eventHandler) {
        if(!onFinishedSet) {
            growTransition.setOnFinished(eventHandler);
            onFinishedSet = true;
        }
    }
}
