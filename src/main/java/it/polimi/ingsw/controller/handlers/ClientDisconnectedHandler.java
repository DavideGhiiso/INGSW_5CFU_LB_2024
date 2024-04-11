package it.polimi.ingsw.controller.handlers;

import it.polimi.ingsw.events.EventHandler;
import it.polimi.ingsw.events.data.ConnectionEvent;
import it.polimi.ingsw.events.data.Event;
import it.polimi.ingsw.networking.Connection;

import java.io.IOException;

/**
 * Handles a ClientDisconnectedEvent by closing passed connection
 * @see it.polimi.ingsw.events.data.client.ClientDisconnectedEvent
 */
public class ClientDisconnectedHandler implements EventHandler {
    @Override
    public void handle(Event event) {
        try {
            event.getConnection().close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
