package it.polimi.ingsw.events.data;

public class EndGameEvent extends BaseEvent {
    public EndGameEvent(boolean isLocal) {
        ID = "END_GAME_EVENT";
        this.local = isLocal;
    }
}
