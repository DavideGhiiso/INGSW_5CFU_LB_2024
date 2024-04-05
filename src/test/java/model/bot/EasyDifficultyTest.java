package model.bot;

import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.model.Dealer;
import it.polimi.ingsw.model.IllegalCardConstructionException;
import it.polimi.ingsw.model.Suit;
import it.polimi.ingsw.model.bot.EasyDifficulty;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EasyDifficultyTest {
    @Test
    public void correctEmptyTableBehaviourTest() {
        EasyDifficulty bot = new EasyDifficulty();
        List<Card> inHandCards = new ArrayList<>();
        List<Card> tableCards = new ArrayList<>();
        List<Card> placedCards = new ArrayList<>();
        try {
            inHandCards.add(new Card(Suit.GOLDS, 1));
            inHandCards.add(new Card(Suit.CLUBS, 1));
            inHandCards.add(new Card(Suit.CUPS, 2));
            inHandCards.add(new Card(Suit.CLUBS, 2));
            inHandCards.sort(Card::compareTo);
        } catch (IllegalCardConstructionException e) {
            throw new RuntimeException(e);
        }
        Card chosenCard = bot.chooseCard(inHandCards, tableCards, placedCards);
        assertEquals(1, chosenCard.getNumber());
        assertEquals(Suit.CLUBS, chosenCard.getSuit());
    }
    @Test
    public void correctNonEmptyTableRandomCardsBehaviourTest() {
        EasyDifficulty bot = new EasyDifficulty();
        Dealer d = new Dealer();
        List<Card> inHandCards = d.getCardsHand().subList(0,7);
        List<Card> tableCards = d.getCardsHand();
        Collections.shuffle(tableCards);
        tableCards = tableCards.subList(0,1);
        List<Card> placedCards = new ArrayList<>();
        System.out.println(tableCards.toString());
        Card chosenCard = bot.chooseCard(inHandCards, tableCards, placedCards);
    }
}
