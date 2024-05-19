package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exceptions.IllegalCardConstructionException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class CardTest {
    @Test
    public void illegalCardConstructionTest() {
        assertThrows(IllegalCardConstructionException.class, () -> new Card(Suit.SWORDS, 11)); // nonexistent
        assertThrows(IllegalCardConstructionException.class, () -> new Card(Suit.SWORDS, -1));
        assertThrows(IllegalCardConstructionException.class, () -> new Card(Suit.SWORDS, 0));
    }
}
