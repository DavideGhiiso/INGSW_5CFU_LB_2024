package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;

import java.util.List;

public abstract class GameController {
    protected final Game game;
    protected Player currentPlayer;
    protected PlayerIterator playerIterator;
    protected int team1Points = 0;
    protected int team2Points = 0;

    protected GameController(Game game) {
        this.game = game;
    }

    public List<Card> placeCard(Card card) {
        Table table = game.getTable();
        List<Card> placedCards = table.placeCard(card);
        currentPlayer.playCard(card);
        game.getPlayerTeam(currentPlayer).addTakenCards(placedCards);
        List<Card> tableCards = table.getPlacedCards();
        if(tableCards.isEmpty())
            game.getPlayerTeam(currentPlayer).addScopa();
        return tableCards;
    }

    public void startGame() {
        playerIterator = new PlayerIterator(game.getPlayers());
        currentPlayer = playerIterator.next();
    }
    public abstract void endMatch();

    public List<Card> getCurrentPlayerHand() {
        return currentPlayer.getHand();
    }

    public void nextTurn() throws EndGameException {
        currentPlayer = playerIterator.next();
        if (playerIterator.getTurnNumber() == 10)
            throw new EndGameException();
    }

    public GameResult[] endGame() {
        GameResult[] gameResults = getGamesResults();
        team1Points += gameResults[0].getTotalPoints();
        team2Points += gameResults[1].getTotalPoints();
        return gameResults;
    }

    /**
     * Method that returns a list of GameResult in which the first element belongs to the first team and the second
     * to the second team
     */
    private GameResult[] getGamesResults() {
        Team team1 = game.getFirstTeam();
        Team team2 = game.getSecondTeam();
        GameResult[] results = new GameResult[2];

        PointsCalculator pointsCalculator = new PointsCalculator(team1.getTakenCards());
        GameResult team1GameResult = new GameResult(
                pointsCalculator.getPoints(),
                team1.getScopa(),
                team1.getPlayers().getFirst().getName(),
                team1.getPlayers().getLast().getName());
        pointsCalculator = new PointsCalculator(team2.getTakenCards());
        GameResult team2GameResult = new GameResult(
                pointsCalculator.getPoints(),
                team2.getScopa(),
                team2.getPlayers().getFirst().getName(),
                team2.getPlayers().getLast().getName());
        results[0] = team1GameResult;
        results[1] = team2GameResult;
        return results;
    }
}
