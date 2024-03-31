package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Dealer;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;

public abstract class GameController {
    protected final Game game;
    protected final int MAX_PLAYERS=4;

    protected GameController(Game game) {
        this.game = game;
    }

    public abstract void startGame();
    public abstract void endGame();
}
