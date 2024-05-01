package it.polimi.ingsw.view;

public class PlayerView {
    private boolean yourTurn;
    private String username;

    public PlayerView() {
        this.yourTurn = false;
        username = "";
    }

    public boolean isYourTurn() {
        return yourTurn;
    }

    public void setYourTurn(boolean yourTurn) {
        this.yourTurn = yourTurn;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
