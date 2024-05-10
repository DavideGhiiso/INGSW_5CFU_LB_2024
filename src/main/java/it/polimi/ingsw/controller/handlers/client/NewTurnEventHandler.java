package it.polimi.ingsw.controller.handlers.client;

import it.polimi.ingsw.events.EventHandler;
import it.polimi.ingsw.events.data.Event;
import it.polimi.ingsw.events.data.server.NewTurnEvent;
import it.polimi.ingsw.view.PlayerView;
import it.polimi.ingsw.view.SceneLoader;
import javafx.application.Platform;

/**
 * @see NewTurnEvent
 */
public class NewTurnEventHandler implements EventHandler {
    @Override
    public void handle(Event event) {
        NewTurnEvent newTurnEvent = (NewTurnEvent) event.getEvent();
        PlayerView playerView = SceneLoader.getPlayerView();
        if(newTurnEvent.getUsername().equals(playerView.getUsername()))
            playerView.setYourTurn(true);
        Platform.runLater(() -> SceneLoader.getCurrentController().handle(event));
    }
}
