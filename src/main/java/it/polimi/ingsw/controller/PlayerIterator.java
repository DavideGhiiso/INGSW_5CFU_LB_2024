package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Player;

import java.util.Iterator;
import java.util.List;

public class PlayerIterator implements Iterator<Player> {
    private final List<Player> playerList;
    private int turnNumber = 0;
    private int index = 0;

    public PlayerIterator(List<Player> players) {
        playerList = players;
    }
    @Override
    public boolean hasNext() {
        return !playerList.isEmpty();
    }

    @Override
    public Player next() {
        Player nextPlayer = playerList.get(index);
        if(index == playerList.size() - 1)
            turnNumber++;
        index = (index + 1) % playerList.size();
        return nextPlayer;
    }

    public int getTurnNumber() {
        return turnNumber;
    }
}
