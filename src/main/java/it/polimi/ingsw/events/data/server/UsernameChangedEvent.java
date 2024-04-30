package it.polimi.ingsw.events.data.server;

import it.polimi.ingsw.events.data.BaseEvent;

public class UsernameChangedEvent extends BaseEvent {
    private final String username;
    public UsernameChangedEvent(String username) {
        ID = "USERNAME_CHANGED_EVENT";
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
