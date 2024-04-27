package it.polimi.ingsw.events.data.client;

import it.polimi.ingsw.events.data.BaseEvent;
import it.polimi.ingsw.events.data.GameInfo;

/**
 * Event used to request any kind of game information to the server. The server will return the info only if it's
 * different from the oldValue
 */
public class RequestGameInfoEvent extends BaseEvent {
    private final GameInfo requestedInfo;
    private Object oldValue;

    public RequestGameInfoEvent(GameInfo gameInfo) {
        ID = "REQUEST_GAME_INFO_EVENT";
        requiresConnection = true;
        requestedInfo = gameInfo;
    }
    public RequestGameInfoEvent(GameInfo gameInfo, Object oldValue) {
        this(gameInfo);
        this.oldValue = oldValue;
    }

    public GameInfo getRequestedInfo() {
        return requestedInfo;
    }

    public Object getOldValue() {
        return oldValue;
    }
}
