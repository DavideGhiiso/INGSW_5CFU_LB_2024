package it.polimi.ingsw.events.data.client;

import it.polimi.ingsw.events.data.BaseEvent;

/**
 * Class Event that represent a player voluntarily leaving the game.
 */
public class LeaveGameEvent extends BaseEvent {
    public LeaveGameEvent() {
        this.ID = "LEAVE_GAME_EVENT";
        requiresConnection = true;
    }
}
