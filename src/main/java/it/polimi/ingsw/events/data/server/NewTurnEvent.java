package it.polimi.ingsw.events.data.server;

import it.polimi.ingsw.events.data.BaseEvent;

/**
 * Event used by the server to notify all client that a new turn has begun
 */
public class NewTurnEvent extends BaseEvent {
    private final String username;
    public NewTurnEvent(String currentPlayerUsername) {
        ID = "NEW_TURN_EVENT";
        username = currentPlayerUsername;
    }

    public String getUsername() {
        return username;
    }
}
