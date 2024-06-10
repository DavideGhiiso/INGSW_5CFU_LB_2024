package it.polimi.ingsw.events.data.server;

import it.polimi.ingsw.events.data.BaseEvent;

/**
 * Internal event thrown when a client suddenly disconnects from the server
 */
public class ClientDisconnectedEvent extends BaseEvent {
    public ClientDisconnectedEvent() {
        ID = "CLIENT_DISCONNECTED_EVENT";
        requiresConnection = true;
    }
}
