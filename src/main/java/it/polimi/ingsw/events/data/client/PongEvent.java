package it.polimi.ingsw.events.data.client;

import it.polimi.ingsw.events.data.BaseEvent;

/**
 * Event that represent a PONG in the ping pong routine
 */
public class PongEvent extends BaseEvent {
    public PongEvent() {
        ID = "PONG_EVENT";
        requiresConnection = true;
    }
}
