package it.polimi.ingsw.controller.handlers;

import it.polimi.ingsw.events.EventHandler;
import it.polimi.ingsw.events.data.ConnectionEvent;
import it.polimi.ingsw.events.data.Event;
import it.polimi.ingsw.events.data.client.PongEvent;
import it.polimi.ingsw.networking.Connection;

import java.io.IOException;

/**
 * Client side handler used to handle the reception of a PingEvent by sending a PongEvent back
 * @see it.polimi.ingsw.events.data.PingEvent
 */
public class PingHandler implements EventHandler {
    @Override
    public void handle(Event event) {
        try {
            event.getConnection().send(new PongEvent());
        } catch (IOException e) {
            try {
                event.getConnection().close();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            throw new RuntimeException(e);
        }
    }
}
