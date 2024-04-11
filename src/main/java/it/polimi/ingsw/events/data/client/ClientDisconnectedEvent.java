package it.polimi.ingsw.events.data.client;

import it.polimi.ingsw.events.data.BaseEvent;

public class ClientDisconnectedEvent extends BaseEvent {
    public ClientDisconnectedEvent() {
        ID = "CLIENT_DISCONNECTED";
        requiresConnection = true;
    }
}
