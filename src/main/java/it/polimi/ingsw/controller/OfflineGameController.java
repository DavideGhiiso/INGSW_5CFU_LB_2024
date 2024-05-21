package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Dealer;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.exceptions.MaxPlayersReachedException;

import java.util.Iterator;

public class OfflineGameController extends GameController {
    private static OfflineGameController instance = null;

    public final String DEFAULT_OFFLINE_GAME = "Tu";

    public static OfflineGameController getInstance() {
        return instance;
    }
    public static OfflineGameController getInstance(Game game) {
        if(instance == null) instance = new OfflineGameController(game);
        return instance;
    }

    public OfflineGameController(Game game) {
        super(game);
        initializePlayers(new Dealer());
    }

    private void initializePlayers(Dealer dealer) {
        try { // adds one player and three BOTS
            game.addPlayer(new Player(DEFAULT_OFFLINE_GAME, dealer.getCardsHand(), false));

            for(int index=1; index <= Game.MAX_PLAYERS - 1; index++) {
                game.addPlayer(new Player("BOT_" + index, dealer.getCardsHand(), true));
            }
        } catch (MaxPlayersReachedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void endMatch() {

    }
}
