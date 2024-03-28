package model;

import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.model.IllegalCardConstructionException;
import it.polimi.ingsw.model.Suit;
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
