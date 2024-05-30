package it.polimi.ingsw.controller;

import it.polimi.ingsw.events.data.EndGameEvent;
import it.polimi.ingsw.events.data.server.*;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.exceptions.EndGameException;
import it.polimi.ingsw.model.exceptions.MaxPlayersReachedException;
import it.polimi.ingsw.model.exceptions.NonexistentPlayerException;
import it.polimi.ingsw.view.SceneLoader;
import it.polimi.ingsw.view.viewcontroller.InGameController;

import java.util.Iterator;
import java.util.List;

public class OfflineGameController extends GameController {
    private static OfflineGameController instance = null;
    private InGameController observer = null;
    public final static String DEFAULT_OFFLINE_GAME = "Tu";

    public static OfflineGameController getInstance() {
        return instance;
    }
    public static OfflineGameController getInstance(Game game) {
        instance = new OfflineGameController(game);
        return instance;
    }

    public OfflineGameController(Game game) {
        super(game);
        initializePlayers(new Dealer());
    }

    public void setObserver(InGameController instance) {
        observer = instance;
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
    public void startGame() {
        super.startGame();
        observer.handle(new ScoreEvent(
                game.getFirstTeam().getPlayers().toArray(new Player[2]),
                game.getSecondTeam().getPlayers().toArray(new Player[2]),
                0,
                0
        ));
        List<Card> hand;
        try {
            hand = game.getPlayer(DEFAULT_OFFLINE_GAME).getHand();
        } catch (NonexistentPlayerException e) {
            throw new RuntimeException(e);
        }
        observer.handle(new HandChangedEvent(hand));
        observer.handle(new NewTurnEvent(currentPlayer.getName()));
    }
    public void placeCardAndPlayBot(Card card) {
        List<Card> table = super.placeCard(card);
        List<Card> hand;
        try {
            hand = game.getPlayer(DEFAULT_OFFLINE_GAME).getHand();
        } catch (NonexistentPlayerException e) {
            throw new RuntimeException(e);
        }
        observer.handle(new HandChangedEvent(hand));
        observer.handle(new TableChangedEvent(table, card));
        try {
            nextTurn();
            observer.handle(new NewTurnEvent(currentPlayer.getName()));
        } catch (EndGameException e) {
            throw new RuntimeException(e);
        }
        while (currentPlayer.isBot()) {
            try {
                playBotTurn();
            } catch (EndGameException e) {
                sendEndGameResultsEvent();
                return;
            }
        }
        SceneLoader.getPlayerView().setYourTurn(true);
    }

    public void playBotTurn() throws EndGameException {
        Card botCard = bot.playCard(currentPlayer.getHand());
        List<Card> table = super.placeCard(botCard);
        observer.handle(new TableChangedEvent(table, botCard));
        nextTurn();
        observer.handle(new NewTurnEvent(currentPlayer.getName()));
    }

    private void sendEndGameResultsEvent() {
        GameResult[] gameResults = endGame();
        EndGameResult result = EndGameResult.DRAW;
        if(gameResults[0].getTotalPoints() > gameResults[1].getTotalPoints())
            result = EndGameResult.TEAM1;
        else if (gameResults[0].getTotalPoints() < gameResults[1].getTotalPoints())
            result = EndGameResult.TEAM2;
        observer.handle(new EndGameResultsEvent(
                gameResults[0],
                gameResults[1],
                result,
                getTeam1Points(),
                getTeam2Points()
        ));
    }

    @Override
    public void continueGame() {
        super.continueGame();
        observer.handle(new ScoreEvent(
                getFirstTeamNames(),
                getSecondTeamNames(),
                getTeam1Points(),
                getTeam2Points()
        ));
        observer.handle(new NewTurnEvent(currentPlayer.getName()));
        observer.handle(new HandChangedEvent(currentPlayer.getHand()));
    }

    @Override
    public void endMatch() {

    }
}
