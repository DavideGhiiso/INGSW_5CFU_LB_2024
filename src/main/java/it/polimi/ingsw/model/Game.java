package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exceptions.MaxPlayersReachedException;
import it.polimi.ingsw.model.exceptions.NonexistentPlayerException;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Class that represents the model of a game
 */
public class Game {
    public static final int MAX_PLAYERS = 4;
    public static final int N_OF_TURNS = 10;
    private final List<Player> players;
    private boolean started;
    private final Team team1;
    private final Team team2;
    private final Table table;

    public Game() {
        this.players = new ArrayList<>();
        this.team1 = new Team();
        this.team2 = new Team();
        this.table = new Table();
    }

    public boolean isFull() {
        return players.size() == MAX_PLAYERS;
    }

    /**
     * Adds a player to the games player and adds it to a team in a way that the teams player number is balanced. If a
     * team is empty, the new player joins that team.
     * @param player new Player to add
     * @throws MaxPlayersReachedException thrown if teams are already full
     */
    public void addPlayer(Player player) throws MaxPlayersReachedException {
        players.add(player);
        if (team1.getPlayers().size() == team2.getPlayers().size())
            team1.addPlayer(player);
        else
            team2.addPlayer(player);
    }

    public void removePlayer(Player player) {
        players.remove(player);
        team1.removePlayer(player);
        team2.removePlayer(player);
    }

    public List<Player> getPlayers() {
        return players;
    }
    public Player getPlayer(String username) throws NonexistentPlayerException {
        Player player;
        try {
            player = players.stream()
                    .filter(p -> p.getName().equals(username)).toList().getLast();
        } catch (NoSuchElementException e) {
            throw new NonexistentPlayerException(username);
        }
        return player;
    }

    public Table getTable() {
        return table;
    }

    public Team getPlayerTeam(Player player) {
        return team1.contains(player) ? team1 : team2;
    }
    public Team getFirstTeam() {
        return team1;
    }
    public Team getSecondTeam() {
        return team2;
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    /**
     * Adds remaining cards on the table to the last team that has taken a card
     */
    public void addRemainingCards() {
        Team team = team1.isLastOneToTake() ? team1 : team2;
        team.addTakenCards(table.getPlacedCards());
        table.getPlacedCards().clear();
    }
}
