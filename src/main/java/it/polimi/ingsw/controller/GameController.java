package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.bot.*;
import it.polimi.ingsw.model.exceptions.EndGameException;

import java.util.List;

/**
 * Class used to control the game by manipulating the model. It contains the game instance, the player iterator and the bot
 */
public abstract class GameController {
    protected Game game;
    protected Player currentPlayer;
    protected PlayerIterator playerIterator;
    protected int team1Points = 0;
    protected int team2Points = 0;

    protected Bot bot;
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
        bot = new Bot(new HardDifficulty(),
                game.getFirstTeam().getTakenCards(),
                game.getSecondTeam().getTakenCards(),
                game.getTable().getPlacedCards());
    }

    public void setBotDifficulty(Difficulties difficulty) {
        bot.setDifficulty(Difficulties.getDifficulty(difficulty));
    }

    public void continueGame() {
        playerIterator = new PlayerIterator(game.getPlayers());
        currentPlayer = playerIterator.next();
        dealer = new Dealer();
        for(Player player: game.getPlayers()) {
            player.setHand(dealer.getCardsHand());
        }
    }
    public Card playCardBot(List<Card> inHandList) {
        return bot.playCard(inHandList);
    }

    protected abstract void endMatch();

    public Player getCurrentPlayer() {
        return currentPlayer;
    }
    public boolean botIsPlaying() {
        return game.getPlayers().stream().anyMatch(Player::isBot);
    }

    public void nextTurn() throws EndGameException {
        if (playerIterator.getTurnNumber() == Game.N_OF_TURNS) {
            throw new EndGameException();
        }
        currentPlayer = playerIterator.next();
    }

    public GameResult[] endGame() {
        game.addRemainingCards();
        GameResult[] gameResults = getGamesResults();
        team1Points += gameResults[0].getTotalPoints();
        team2Points += gameResults[1].getTotalPoints();
        game.getFirstTeam().endRound();
        game.getSecondTeam().endRound();
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
                team1.getRoundScopa(),
                team1.getPlayers().getFirst().getName(),
                team1.getPlayers().getLast().getName());
        pointsCalculator = new PointsCalculator(team2.getTakenCards());
        GameResult team2GameResult = new GameResult(
                pointsCalculator.getPoints(),
                team2.getRoundScopa(),
                team2.getPlayers().getFirst().getName(),
                team2.getPlayers().getLast().getName());
        results[0] = team1GameResult;
        results[1] = team2GameResult;
        return results;
    }
}
