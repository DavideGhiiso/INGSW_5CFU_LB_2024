package it.polimi.ingsw.events.data.client;

import it.polimi.ingsw.events.data.BaseEvent;

/**
 * Event used by client at the end of a round to ask the server to start a new round
 */
public class ContinueGameEvent extends BaseEvent {
    public ContinueGameEvent() {
        ID = "CONTINUE_GAME_EVENT";
    }
}
