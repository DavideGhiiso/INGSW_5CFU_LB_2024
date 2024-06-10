package it.polimi.ingsw.events.data.server;

import it.polimi.ingsw.events.data.BaseEvent;
/**
 *  Event used by the server to warn the clients that a new round is starting
 */
public class GameResumingWarningEvent extends BaseEvent {
    public GameResumingWarningEvent() {
        ID = "GAME_RESUMING_WARNING_EVENT";
    }
}
