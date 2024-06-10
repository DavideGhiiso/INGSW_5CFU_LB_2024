package it.polimi.ingsw.events.data;

/**
 * Internal event triggered when the model notifies the game controller that the game is ending
 */
public class EndGameEvent extends BaseEvent {
    public EndGameEvent() {
        ID = "END_GAME_EVENT";
    }
    public EndGameEvent(boolean isLocal) {
        this();
        this.local = isLocal;
    }
}
