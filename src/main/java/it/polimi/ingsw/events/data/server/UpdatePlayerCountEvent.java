package it.polimi.ingsw.events.data.server;

import it.polimi.ingsw.events.data.BaseEvent;

/**
 * Event used by the sever to notify all clients that the number of clients in the waiting room is changed
 */
public class UpdatePlayerCountEvent extends BaseEvent {
    private final int playerCount;
    public UpdatePlayerCountEvent(int newPlayerCount) {
        ID = "UPDATE_PLAYER_COUNT_EVENT";
        playerCount = newPlayerCount;
    }

    public int getPlayerCount() {
        return playerCount;
    }
}
