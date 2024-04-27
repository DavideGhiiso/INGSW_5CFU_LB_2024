package it.polimi.ingsw.controller.handlers;

import it.polimi.ingsw.events.EventHandler;
import it.polimi.ingsw.events.data.Event;
import it.polimi.ingsw.view.SceneLoader;
import javafx.application.Platform;

/**
 * @see it.polimi.ingsw.events.data.client.StartGameEvent
 */
public class StartGameClientHandler implements EventHandler {
    @Override
    public void handle(Event event) {
        Platform.runLater(() -> {
            SceneLoader.changeScene("fxml/ingame.fxml");
        });
    }
}
