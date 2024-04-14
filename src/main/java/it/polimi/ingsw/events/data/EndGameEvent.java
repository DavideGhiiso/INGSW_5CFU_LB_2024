package it.polimi.ingsw.events.data;

public class EndGameEvent extends BaseEvent {
    public EndGameEvent(boolean isLocal) {
        this.local = isLocal;
    }
}
