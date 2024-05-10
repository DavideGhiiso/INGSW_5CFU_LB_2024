package it.polimi.ingsw.controller.handlers.server;

import it.polimi.ingsw.events.EventHandler;
import it.polimi.ingsw.events.data.ConnectionEvent;
import it.polimi.ingsw.events.data.Event;

/**
 * Event handler that handles a Pong Event by resetting the counter of the connection that sent it
 * @see it.polimi.ingsw.events.data.client.PongEvent
 */
public class PongHandler implements EventHandler {
    @Override
    public void handle(Event event) {
        event.getConnection().resetPingFailure();
    }
}
