package it.polimi.ingsw.controller.handlers;

import it.polimi.ingsw.events.EventHandler;
import it.polimi.ingsw.events.data.Event;
import it.polimi.ingsw.view.SceneLoader;

/**
 * @see it.polimi.ingsw.events.data.server.YourTurnEvent
 */
public class YourTurnEventHandler implements EventHandler {
    @Override
    public void handle(Event event) {
        SceneLoader.getPlayerView().setYourTurn(true);
    }
}
