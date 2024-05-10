package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.bot.Bot;
import it.polimi.ingsw.model.bot.Difficulty;
import it.polimi.ingsw.model.bot.EasyDifficulty;
import it.polimi.ingsw.model.bot.HardDifficulty;

import java.util.List;

public abstract class GameController {
    protected final Game game;
    protected Player currentPlayer;
    protected PlayerIterator playerIterator;
    protected int team1Points = 0;
    protected int team2Points = 0;

    protected Bot bot;
    protected boolean isFirstTeamLastCatcher = true;
    protected Dealer dealer;

    protected GameController(Game game) {
        this.game = game;
        dealer = new Dealer();
    }

    /**
     * Method that places the card on the board, gives taken cards to the team and removes the card from currentPlayer
     * @param card played card
     * @return state of table after placement
     */
    public List<Card> placeCard(Card card) {
        Table table = game.getTable();
        List<Card> takenCards = table.placeCard(card);
        if(!takenCards.isEmpty())
            setLastTeamToTake(game.getPlayerTeam(currentPlayer));
        currentPlayer.playCard(card);
        game.getPlayerTeam(currentPlayer).addTakenCards(takenCards);
        List<Card> tableCards = table.getPlacedCards();
        if(tableCards.isEmpty())
            game.getPlayerTeam(currentPlayer).addScopa();
        return tableCards;
    }

    private void setLastTeamToTake(Team team) {
        game.getFirstTeam().setLastOneToTake(false);
        game.getSecondTeam().setLastOneToTake(false);
        team.setLastOneToTake(true);
    }

    public void startGame() {
        game.setStarted(true);
        playerIterator = new PlayerIterator(game.getPlayers());
        currentPlayer = playerIterator.next();
        bot = new Bot(new EasyDifficulty(),
                game.getFirstTeam().getTakenCards(),
                game.getSecondTeam().getTakenCards(),
                game.getTable().getPlacedCards());
    }

    public void continueGame() {
        playerIterator = new PlayerIterator(game.getPlayers());
        currentPlayer = playerIterator.next();
        dealer = new Dealer();
        for(Player player: game.getPlayers()) {
            player.setHand(dealer.getCardsHand());
        }
    }

    public void setBotDifficulty(Difficulty difficulty) {
        bot.setDifficulty(difficulty);
    }
    public Card playCardBot(List<Card> inHandList) {
        return bot.playCard(inHandList);
    }

    public abstract void endMatch();

    public Player getCurrentPlayer() {
        return currentPlayer;
    }
    public boolean botIsPlaying() {
        return game.getPlayers().stream().anyMatch(Player::isBot);
    }

    public boolean isFirstTeamLastCatcher() {
        return isFirstTeamLastCatcher;
    }

    public void setFirstTeamLastCatcher(boolean firstTeamLastCatcher) {
        isFirstTeamLastCatcher = firstTeamLastCatcher;
    }

    public void nextTurn() throws EndGameException {
        if (playerIterator.getTurnNumber() == 10) {
            throw new EndGameException();
        }
        currentPlayer = playerIterator.next();
    }

    public GameResult[] endGame() {
        game.addRemainingCards();
        GameResult[] gameResults = getGamesResults();
        team1Points += gameResults[0].getTotalPoints();
        team2Points += gameResults[1].getTotalPoints();
        return gameResults;
    }

    public String[] getFirstTeamNames() {
        return game.getFirstTeam().getPlayers().stream().map(Player::getName).toArray(String[]::new);
    }

    public String[] getSecondTeamNames() {
        return game.getSecondTeam().getPlayers().stream().map(Player::getName).toArray(String[]::new);
    }

    public int getTeam1Points() {
        return team1Points;
    }

    public int getTeam2Points() {
        return team2Points;
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
