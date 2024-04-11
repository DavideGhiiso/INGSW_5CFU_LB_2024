package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.model.Dealer;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;

import java.util.Iterator;
import java.util.List;

public abstract class GameController {
    protected final Game game;
    protected Player currentPlayer;
    protected PlayerIterator playerIterator;

    protected GameController(Game game) {
        this.game = game;
    }

    public void placeCard(Card card) {
        List<Card> placedCards = game.getTable().placeCard(card);
        game.getPlayerTeam(currentPlayer).addTakenCards(placedCards);
    }

    public void startGame() {
        playerIterator = new PlayerIterator(game.getPlayers());
        currentPlayer = playerIterator.next();
    }
    public abstract void endGame();

}
