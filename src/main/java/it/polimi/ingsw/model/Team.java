package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exceptions.MaxPlayersReachedException;
import it.polimi.ingsw.utils.CardListUtils;

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
     * Number of "scopa" made by the team
     */
    private int scopa;
    // true if the fist team was the last one to take a card
    private boolean lastOneToTake;


    public Team() {
        this.players = new ArrayList<>();
        this.takenCards = new ArrayList<>();
        this.scopa = 0;
    }

    public void addTakenCards(List<Card> newCards) {
        takenCards.addAll(newCards);
    }

    public int getScopa() {
        return scopa;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void addScopa() {
        this.scopa++;
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void removePlayer(Player player) {
        players.remove(player);
    }
    public boolean contains(Player player) {
        return players.contains(player);
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
