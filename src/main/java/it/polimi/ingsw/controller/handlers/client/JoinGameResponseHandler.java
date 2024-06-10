package it.polimi.ingsw.controller.handlers.client;

import it.polimi.ingsw.controller.handlers.EventHandler;
import it.polimi.ingsw.events.data.Event;
import it.polimi.ingsw.events.data.server.JoinGameResponseEvent;
import it.polimi.ingsw.view.SceneLoader;

/**
 * @see JoinGameResponseEvent
 */
public class JoinGameResponseHandler implements EventHandler {
    @Override
    public void handle(Event event) {
        SceneLoader.getCurrentController().handle(event);
    }
}
