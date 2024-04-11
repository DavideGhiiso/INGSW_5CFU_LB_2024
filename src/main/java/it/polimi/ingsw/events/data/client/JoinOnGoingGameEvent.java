package it.polimi.ingsw.events.data.client;

import it.polimi.ingsw.events.data.BaseEvent;

/**
 * Event thrown when a Client wants to join the game by replacing a bot
 */
public class JoinOnGoingGameEvent extends BaseEvent {
    private final String username;
    public JoinOnGoingGameEvent(String username) {
        this.ID = "JOIN_ONGOING_GAME_EVENT";
        requiresConnection = true;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
