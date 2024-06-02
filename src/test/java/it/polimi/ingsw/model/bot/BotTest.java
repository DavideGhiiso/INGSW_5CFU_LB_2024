package it.polimi.ingsw.model.bot;

import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.model.Suit;
import it.polimi.ingsw.model.exceptions.IllegalCardConstructionException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class BotTest {
    @Test
    public void correctNegativeWeightChoiceTest() {
        List<Card> hand;
        List<Card> table;
        try {
            hand = List.of(
                    new Card(Suit.GOLDS, 7),
                    new Card(Suit.SWORDS, 7),
                    new Card(Suit.CUPS, 7)
            );
            table = List.of(new Card(Suit.CUPS,1));
        } catch (IllegalCardConstructionException e) {
            throw new RuntimeException(e);
        }
        Bot bot = new Bot(new HardDifficulty(), new ArrayList<>(), new ArrayList<>(), table);
        Assertions.assertNotEquals(Suit.GOLDS, bot.playCard(hand).getSuit());
    }
}
