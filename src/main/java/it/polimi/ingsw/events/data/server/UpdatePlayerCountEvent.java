package it.polimi.ingsw.events.data.server;

import it.polimi.ingsw.events.data.BaseEvent;

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
