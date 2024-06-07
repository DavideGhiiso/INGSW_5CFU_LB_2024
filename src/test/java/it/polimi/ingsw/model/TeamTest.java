package it.polimi.ingsw.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class TeamTest {
    @Test
    public void containsMethodTest() {
        List<Player> players = List.of(
            new Player("P1", new ArrayList<>(), false),
            new Player("P2", new ArrayList<>(), true),
            new Player("P3", new ArrayList<>(), true),
            new Player("P4", new ArrayList<>(), false)
        );
        Team team = new Team();
        players.forEach(team::addPlayer);
        Assertions.assertTrue(team.contains(players.getFirst()));
        Assertions.assertFalse(team.contains(new Player("A", new ArrayList<>(), true)));
    }
}
