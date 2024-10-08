package it.polimi.ingsw.events;

import it.polimi.ingsw.events.data.BaseEvent;
import it.polimi.ingsw.networking.Connection;
import it.polimi.ingsw.networking.Server;

import java.io.IOException;
import java.util.Map;

/**
 * Class that allows to send an Event through a Connection in various ways
 */
public class EventTransmitter {
    private final Map<Connection, Thread> connections;

    public EventTransmitter(Map<Connection, Thread> connections) {
        this.connections = connections;
    }

    /**
     * Sends the event to all players
     * @param event Event object to broadcast
     */
    public void broadcast(BaseEvent event) throws IOException {
        for (Connection connection : connections.keySet()) {
            if(Server.DEBUG)
                System.out.println("Broadcast: " + event.getID());
            connection.send(event);
        }
    }
}
