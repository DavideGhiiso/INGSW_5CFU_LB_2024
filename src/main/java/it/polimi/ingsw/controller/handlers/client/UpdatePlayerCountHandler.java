package it.polimi.ingsw.controller.handlers.client;

import it.polimi.ingsw.events.EventHandler;
import it.polimi.ingsw.events.data.Event;
import it.polimi.ingsw.events.data.server.UpdatePlayerCountEvent;
import it.polimi.ingsw.view.SceneLoader;

/**
 * @see UpdatePlayerCountEvent
 */
public class UpdatePlayerCountHandler implements EventHandler {
    @Override
    public void handle(Event event) {
        SceneLoader.getCurrentController().handle(event);
    }
}
