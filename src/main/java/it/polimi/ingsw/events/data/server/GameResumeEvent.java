package it.polimi.ingsw.events.data.server;

import it.polimi.ingsw.events.data.BaseEvent;

/**
 *  Event used by the server to notify the clients that a new round is started
 */
public class GameResumeEvent extends BaseEvent {
    public GameResumeEvent() {
        ID = "GAME_RESUME_EVENT";
    }
}
