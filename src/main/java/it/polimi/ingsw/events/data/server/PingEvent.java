package it.polimi.ingsw.events.data.server;

import it.polimi.ingsw.events.data.BaseEvent;

public class PingEvent extends BaseEvent {
    public PingEvent() {
        ID = "PING_EVENT";
        requiresConnection = true;
    }
}
