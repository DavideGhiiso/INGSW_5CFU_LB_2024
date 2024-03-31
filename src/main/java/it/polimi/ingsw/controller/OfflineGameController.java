package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Dealer;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.exceptions.MaxPlayersReachedException;

public class OfflineGameController extends GameController {

    public OfflineGameController(Game game) {
        super(game);
    }

    @Override
    public void startGame() {
        Dealer dealer = new Dealer();
        try { // adds one player and three BOTS
            game.addPlayer(new Player("", dealer.getCardsHand(), false));
            for(int index=1; index <= MAX_PLAYERS - 1; index++) {
                game.addPlayer(new Player("BOT_" + index, dealer.getCardsHand(), true));
            }
        } catch (MaxPlayersReachedException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void endGame() {

    }
}
