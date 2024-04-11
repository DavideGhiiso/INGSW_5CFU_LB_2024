package it.polimi.ingsw.events.data.client;

import it.polimi.ingsw.events.data.BaseEvent;

public class PongEvent extends BaseEvent {
    public PongEvent() {
        ID = "PONG_EVENT";
        requiresConnection = true;
    }
}
