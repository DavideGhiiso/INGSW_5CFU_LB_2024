package it.polimi.ingsw.events.data;

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
