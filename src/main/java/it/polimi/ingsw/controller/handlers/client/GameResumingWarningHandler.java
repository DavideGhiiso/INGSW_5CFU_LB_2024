package it.polimi.ingsw.controller.handlers.client;

import it.polimi.ingsw.controller.handlers.EventHandler;
import it.polimi.ingsw.events.data.Event;
import it.polimi.ingsw.events.data.server.GameResumingWarningEvent;
import it.polimi.ingsw.view.SceneLoader;

/**
 * @see GameResumingWarningEvent
 */
public class GameResumingWarningHandler implements EventHandler {
    @Override
    public void handle(Event event) {
        SceneLoader.getCurrentController().handle(event);
    }
}
