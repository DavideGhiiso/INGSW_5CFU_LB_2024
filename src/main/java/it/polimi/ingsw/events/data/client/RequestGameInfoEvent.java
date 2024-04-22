package it.polimi.ingsw.events.data.client;

import it.polimi.ingsw.events.data.BaseEvent;
import it.polimi.ingsw.events.data.GameInfo;

/**
 * Event used to request any kind of game information to the server
 */
public class RequestGameInfoEvent extends BaseEvent {
    private final GameInfo requestedInfo;
    public RequestGameInfoEvent(GameInfo gameInfo) {
        ID = "REQUEST_GAME_INFO_EVENT";
        requiresConnection = true;
        requestedInfo = gameInfo;
    }

    public GameInfo getRequestedInfo() {
        return requestedInfo;
    }
}
