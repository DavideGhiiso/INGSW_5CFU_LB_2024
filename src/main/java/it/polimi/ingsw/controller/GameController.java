package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Dealer;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;

import java.util.Iterator;

public abstract class GameController {
    protected final Game game;
    protected Player currentPlayer;
    protected PlayerIterator playerIterator;

    protected GameController(Game game) {
        this.game = game;
    }

    public abstract void startGame();
    public abstract void endGame();
}
