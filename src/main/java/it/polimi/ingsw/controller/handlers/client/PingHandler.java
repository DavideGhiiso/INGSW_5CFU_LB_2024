package it.polimi.ingsw.controller.handlers.client;

import it.polimi.ingsw.controller.handlers.EventHandler;
import it.polimi.ingsw.events.data.Event;
import it.polimi.ingsw.events.data.client.PongEvent;
import it.polimi.ingsw.events.data.server.PingEvent;

import java.io.IOException;

/**
 * Client side handler used to handle the reception of a PingEvent by sending a PongEvent back
 * @see PingEvent
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
