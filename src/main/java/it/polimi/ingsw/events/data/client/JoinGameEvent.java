package it.polimi.ingsw.events.data.client;

import it.polimi.ingsw.events.data.BaseEvent;
import it.polimi.ingsw.events.data.Event;

/**
 * Class Event that represent a Client joining a game. It contains the username that the Client wants to use in the game
 */
public class JoinGameEvent extends BaseEvent {
    private final String username;
    public JoinGameEvent(String username) {
        this.ID = "JOIN_GAME_EVENT";
        requiresConnection = true;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
