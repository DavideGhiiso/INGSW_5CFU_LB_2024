package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exceptions.MaxPlayersReachedException;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private final Team[] teams;
    private final Table table;

    public Game() {
        this.teams = new Team[2];
        this.table = new Table();
    }

    public void addPlayer(Player player) throws MaxPlayersReachedException {
        try {
            teams[0].addPlayer(player);
        } catch (MaxPlayersReachedException e) {
            teams[1].addPlayer(player);
        }
    }

    public void removePlayer(Player player) {
        teams[0].removePlayer(player);
        teams[1].removePlayer(player);
    }

}
