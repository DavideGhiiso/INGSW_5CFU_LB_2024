package it.polimi.ingsw.events.data;

public class EndGameEvent extends BaseEvent {
    public EndGameEvent() {
        ID = "END_GAME_EVENT";
    }
    public EndGameEvent(boolean isLocal) {
        this();
        this.local = isLocal;
    }
}
