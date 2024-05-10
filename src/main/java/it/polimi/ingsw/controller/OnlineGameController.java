package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.model.Dealer;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.exceptions.MaxPlayersReachedException;
import it.polimi.ingsw.model.exceptions.NonexistentPlayerException;
import it.polimi.ingsw.model.exceptions.UsernameTakenException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public class OnlineGameController extends GameController {
    private static OnlineGameController instance = null;



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

    /**
     * Tries to add a new Player to the game instance
     * @param username username of the Player to add
     * @throws MaxPlayersReachedException when the game is full
     * @throws UsernameTakenException when the username is already in use
     */
    public void addPlayer(String username) throws MaxPlayersReachedException, UsernameTakenException {
        try {
            game.getPlayer(username);
            throw new UsernameTakenException();
        } catch (NonexistentPlayerException e) { // username is available
            if (game.isFull()) throw new MaxPlayersReachedException();
            game.addPlayer(new Player(username, dealer.getCardsHand(), false));
        }
    }

    public void addBot() throws MaxPlayersReachedException {
        int index = 0;
        while(true) {
            try {
                game.getPlayer("BOT" + ++index);
            } catch (NonexistentPlayerException e) {
                break;
            }
        }
        if(game.isFull()) throw new MaxPlayersReachedException();
        game.addPlayer(new Player("BOT" + index, dealer.getCardsHand(), true));
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

    public int getPlayersCount() {
        return game.getPlayers().size();
    }

    public boolean canStartGame() {
        return getPlayersCount() == Game.MAX_PLAYERS;
    }
    public List<Card> getTableCards() {
        return game.getTable().getPlacedCards();
    }

    /**
     * Removes a player and eventually replaces him with a bot only if the game is started
     * @param username Player username to replace
     */
    public void handleClientExit(String username) {
        Player player;
        try {
            player = game.getPlayer(username);
        } catch (NonexistentPlayerException e) {
            return;
        }
        if(game.isStarted())
            replaceClientWithBot(player);
        else
            removePlayer(player);
    }

    private void replaceClientWithBot(Player player) {
        player.setBot(true);
        player.setName(player.getName() + "_BOT");
    }

    private void removePlayer(Player player) {
        dealer.takeBackHand(player.getHand());
        game.removePlayer(player);
    }

    public List<Card> getPlayerCards(String username) throws NonexistentPlayerException {
        return game.getPlayer(username).getHand();
    }

    public boolean isGameStarted() {
        return game.isStarted();
    }

    @Override
    public String toString() {
        return game.getPlayers().toString();
    }
}
