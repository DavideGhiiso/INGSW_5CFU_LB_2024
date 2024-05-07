package it.polimi.ingsw.controller.handlers;

import it.polimi.ingsw.events.EventHandler;
import it.polimi.ingsw.events.data.Event;
import it.polimi.ingsw.view.SceneLoader;
import javafx.application.Platform;

public class EndGameResultsHandler implements EventHandler {
    @Override
    public void handle(Event event) {
        Platform.runLater(() -> {
            SceneLoader.changeScene("fxml/endgame.fxml");
            SceneLoader.getCurrentController().handle(event);
        });
    }
}
