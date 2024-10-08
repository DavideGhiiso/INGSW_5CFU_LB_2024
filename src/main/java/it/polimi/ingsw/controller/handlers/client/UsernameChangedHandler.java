package it.polimi.ingsw.controller.handlers.client;

import it.polimi.ingsw.controller.handlers.EventHandler;
import it.polimi.ingsw.events.data.Event;
import it.polimi.ingsw.events.data.server.UsernameChangedEvent;
import it.polimi.ingsw.view.SceneLoader;

/**
 * @see UsernameChangedEvent
 */
public class UsernameChangedHandler implements EventHandler {
    @Override
    public void handle(Event event) {
        UsernameChangedEvent usernameChangedEvent = (UsernameChangedEvent) event.getEvent();
        SceneLoader.getPlayerView().setUsername(usernameChangedEvent.getUsername());
    }
}
