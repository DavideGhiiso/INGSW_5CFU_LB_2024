package it.polimi.ingsw.events.data;

public class PingEvent extends BaseEvent {
    public PingEvent() {
        ID = "PING_EVENT";
        requiresConnection = true;
    }
}
