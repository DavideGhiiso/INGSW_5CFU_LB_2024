package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class PlayerIteratorTest {
    private final List<Player> players = List.of(
            new Player("P1",new ArrayList<>(), false),
            new Player("P2",new ArrayList<>(), false),
            new Player("P3",new ArrayList<>(), false)
    );
    @Test
    public void nextPlayerTest() {
        PlayerIterator playerIterator = new PlayerIterator(players);
        Assertions.assertEquals("P1", playerIterator.next().getName());
        Assertions.assertEquals("P2", playerIterator.next().getName());
        Assertions.assertEquals("P3", playerIterator.next().getName());
        Assertions.assertEquals("P1", playerIterator.next().getName());
    }
    @Test
    public void turnNumberTest() {
        PlayerIterator playerIterator = new PlayerIterator(players);
        Assertions.assertEquals("P1", playerIterator.next().getName());
        Assertions.assertEquals("P2", playerIterator.next().getName());
        Assertions.assertEquals("P3", playerIterator.next().getName());
        Assertions.assertEquals(1, playerIterator.getTurnNumber());
        Assertions.assertEquals("P1", playerIterator.next().getName());
        Assertions.assertEquals("P2", playerIterator.next().getName());
        Assertions.assertEquals("P3", playerIterator.next().getName());
        Assertions.assertEquals(2, playerIterator.getTurnNumber());
    }
}
