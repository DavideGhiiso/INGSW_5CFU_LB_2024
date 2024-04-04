package it.polimi.ingsw.messages.data;

public class NewGameMessage extends Message {
    private final String username;
    public NewGameMessage(String username) {
        ID = "NEW_GAME";
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
