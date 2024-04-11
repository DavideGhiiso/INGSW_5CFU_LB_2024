package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exceptions.MaxPlayersReachedException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Class that represents the model of a game
 */
public class Game {
    private Game instance = null;
    public static final int MAX_PLAYERS = 4;
    public static final int N_OF_TURNS = 10;
    private final List<Player> players;
    private final Team[] teams;
    private final Table table;

    private Game() {
        this.players = new ArrayList<>();
        this.teams = new Team[2];
        this.table = new Table();
    }
    public Game getInstance() {
        if(instance == null)
            instance = new Game();
        return instance;
    }


    /**
     * Adds a player to the games player and adds it to a team in a way that the teams player number is balanced. If a
     * team is empty, the new player joins that team.
     * @param player new Player to add
     * @throws MaxPlayersReachedException thrown if teams are already full
     */
    public void addPlayer(Player player) throws MaxPlayersReachedException {
        if (players.size() == MAX_PLAYERS) throw new MaxPlayersReachedException();
        players.add(player);
        if (teams[0].getPlayers().isEmpty() || !teams[1].getPlayers().isEmpty())
            teams[0].addPlayer(player);
        else
            teams[1].addPlayer(player);
    }

    public void removePlayer(Player player) {
        players.remove(player);
        teams[0].removePlayer(player);
        teams[1].removePlayer(player);
    }

    public List<Player> getPlayers() {
        return players;
    }

    public Table getTable() {
        return table;
    }

    public void startGame() {

    }

}
