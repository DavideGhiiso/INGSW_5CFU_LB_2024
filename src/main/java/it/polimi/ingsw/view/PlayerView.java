package it.polimi.ingsw.view;

import it.polimi.ingsw.model.bot.Difficulties;

/**
 * Class that represent a small Client model serving as a buffer to store information received from the server
 */
public class PlayerView {
    private boolean yourTurn;
    private String username;
    private boolean offline;
    private Difficulties currentDifficulty;

    public PlayerView() {
        currentDifficulty = Difficulties.HARD;
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

    public boolean isOffline() {
        return offline;
    }

    public void setOffline(boolean offline) {
        this.offline = offline;
    }

    public Difficulties getCurrentDifficulty() {
        return currentDifficulty;
    }

    public void setCurrentDifficulty(Difficulties currentDifficulty) {
        this.currentDifficulty = currentDifficulty;
    }
}
