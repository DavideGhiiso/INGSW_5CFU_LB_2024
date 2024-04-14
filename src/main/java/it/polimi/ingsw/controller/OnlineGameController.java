package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.model.Dealer;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.exceptions.MaxPlayersReachedException;
import it.polimi.ingsw.model.exceptions.NonexistentPlayerException;

import java.util.List;
import java.util.NoSuchElementException;

public class OnlineGameController extends GameController {
    private static OnlineGameController instance = null;
    private Dealer dealer;


    private OnlineGameController(Game game) {
        super(game);
    }

    public static OnlineGameController getInstance(Game game) {
        if(instance == null) instance = new OnlineGameController(game);
        return instance;
    }
    public static OnlineGameController getInstance() {
        return instance;
    }

    @Override
    public void endMatch() {

    }

    public void addPlayer(String username) throws MaxPlayersReachedException {
        try {
            game.addPlayer(new Player(username, dealer.getCardsHand(), false));
        } catch (MaxPlayersReachedException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean botIsPlaying() {
        return game.getPlayers().stream().anyMatch(Player::isBot);
    }

    public void replaceBotWithClient(String username) throws MaxPlayersReachedException {
        Player firstBot;
        try {
            firstBot = game.getPlayers().stream().filter(Player::isBot).toList().getFirst();
        } catch (NoSuchElementException e) {
            throw new MaxPlayersReachedException();
        }
        firstBot.setName(username);
        firstBot.setBot(false);
    }

    public void replaceClientWithBot(String username) {
        Player player;
        try {
            player = game.getPlayer(username);
        } catch (NonexistentPlayerException e) {
            return;
        }
        player.setBot(true);
        player.setName(player.getName() + "_BOT");
    }
}
