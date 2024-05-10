package it.polimi.ingsw.controller.handlers.client;

import it.polimi.ingsw.events.EventHandler;
import it.polimi.ingsw.events.data.Event;
import it.polimi.ingsw.view.SceneLoader;
import javafx.application.Platform;

public class EndGameResultsHandler implements EventHandler {
    @Override
    public void handle(Event event) {
        SceneLoader.getCurrentController().handle(event);
    }
}
