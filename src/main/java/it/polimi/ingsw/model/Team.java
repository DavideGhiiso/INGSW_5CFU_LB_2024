package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that represents a team. It contains all information necessary to calculate end game points
 */
public class Team {
    final static int MAX_TEAM_SIZE = 2;
    /**
     * Players that are part of the team
     */
    private final List<Player> players;
    /**
     * List of cards taken by this team
     */
    private final List<Card> takenCards;
    /**
     * Number of "scopa" made by the team in the whole game
     */
    private int totalScopa;
    /**
     * Number of "scopa" made by the team in a round
     */
    private int roundScopa;
    /**
     * true if the fist team was the last one to take a card
     */
    private boolean lastOneToTake;


    public Team() {
        this.players = new ArrayList<>();
        this.takenCards = new ArrayList<>();
        this.totalScopa = 0;
        this.roundScopa = 0;
    }

    public void addTakenCards(List<Card> newCards) {
        takenCards.addAll(newCards);
    }

    public int getTotalScopa() {
        return totalScopa;
    }
    public int getRoundScopa() {
        return roundScopa;
    }

    /**
     * Method called when the round is ended. Resets the state of the team to the initial state
     */
    public void endRound() {
        totalScopa += roundScopa;
        roundScopa = 0;
        takenCards.clear();
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void addScopa() {
        this.roundScopa++;
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void removePlayer(Player player) {
        players.remove(player);
    }
    public boolean contains(Player player) {
        for(Player p: players) {
            if(p.getName().equals(player.getName()))
                return true;
        }
        return false;
    }

    public List<Card> getTakenCards() {
        return takenCards;
    }

    public boolean isLastOneToTake() {
        return lastOneToTake;
    }

    public void setLastOneToTake(boolean lastOneToTake) {
        this.lastOneToTake = lastOneToTake;
    }
}
