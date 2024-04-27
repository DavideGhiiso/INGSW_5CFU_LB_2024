package it.polimi.ingsw.events;

import it.polimi.ingsw.events.data.BaseEvent;
import it.polimi.ingsw.networking.Connection;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
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
     * Sends the event to player identified by passed username
     * @param username player to send the event to
     * @param event BaseEvent object to send
     */
    public void sendTo(String username, BaseEvent event) {
        for(Connection connection: connections.keySet()) {
            if(connection.getConnectionID().equals(username)) {
                try {
                    connection.send(event);
                    break;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    /**
     * Sends the event to all players
     * @param event Event object to broadcast
     */
    public void broadcast(BaseEvent event) throws IOException {
        for (Connection connection : connections.keySet()) {
            System.out.println("Broadcast: " + event.getID());
            connection.send(event);
        }
    }
}
