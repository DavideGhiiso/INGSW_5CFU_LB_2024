package model;

import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.model.IllegalCardConstructionException;
import it.polimi.ingsw.model.Suit;
import it.polimi.ingsw.model.Table;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TableTest {
    @Test
    public void correctPlaceCardTest() {
        Table table = new Table();
        List<Card> expectedSortedCardList = new ArrayList<>();
        try {
            expectedSortedCardList.add(new Card(Suit.GOLDS, 10));
            expectedSortedCardList.add(new Card(Suit.CUPS, 10));
            expectedSortedCardList.add(new Card(Suit.CLUBS, 3));
            expectedSortedCardList.add(new Card(Suit.SWORDS, 1));


            // insertion in random order
            table.addCard(expectedSortedCardList.get(2));
            table.addCard(expectedSortedCardList.get(0));
            table.addCard(expectedSortedCardList.get(1));
            table.addCard(expectedSortedCardList.get(3));

        } catch (IllegalCardConstructionException e) {
            throw new RuntimeException(e);
        }
        System.out.println(table.getPlacedCards().toString());
        System.out.println(expectedSortedCardList.toString());
        assertArrayEquals(expectedSortedCardList.toArray(), table.getPlacedCards().toArray());
    }
    @Test
    public void getCombinationsTest() {

        List<List<Card>> combo = new ArrayList<>();
        Table table = new Table();
        try {
            table.addCard(new Card(Suit.GOLDS, 10));
            table.addCard(new Card(Suit.CUPS, 10));
            table.addCard(new Card(Suit.CLUBS, 3));
            table.addCard(new Card(Suit.SWORDS, 1));
        } catch (IllegalCardConstructionException e) {
            throw new RuntimeException(e);
        }

        for(Card card: table.getPlacedCards()) {
            List<Card> firstElement = new ArrayList<>();
            firstElement.add(card);
            table.getCombinations(combo, firstElement, table.getPlacedCards(), table.getPlacedCards().indexOf(card), 4);
        }
        combo.sort(Comparator.comparingInt(List::size));
        System.out.println(combo.toString());
    }
}
