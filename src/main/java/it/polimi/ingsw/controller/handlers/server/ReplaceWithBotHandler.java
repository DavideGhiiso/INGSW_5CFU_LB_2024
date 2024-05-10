package it.polimi.ingsw.controller.handlers.server;

import it.polimi.ingsw.controller.OnlineGameController;
import it.polimi.ingsw.events.EventHandler;
import it.polimi.ingsw.events.data.Event;

/**
 * @see it.polimi.ingsw.events.data.client.ReplaceWithBotEvent
 */
public class ReplaceWithBotHandler implements EventHandler {
    @Override
    public void handle(Event event) {
        OnlineGameController.getInstance().handleClientExit(event.getConnection().getConnectionID());
    }
}
