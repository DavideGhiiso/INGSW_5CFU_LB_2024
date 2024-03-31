package it.polimi.ingsw.utils;

import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.model.Suit;

import java.util.List;

public class CardListUtils {
    public static int sumOfNumbers(List<Card> cards) {
        return cards.stream()
                .map(Card::getNumber)
                .reduce(0, Integer::sum);
    }

    public static int suitsCount(List<Card> cards, Suit queriedSuit) {
        return cards.stream()
                .map(c -> (c.getSuit().equals(queriedSuit) ? 1 : 0))
                .reduce(0, Integer::sum);
    }

    public static int numbersCount(List<Card> cards, int queriedNumber) {
        return cards.stream()
                .map(c -> (c.getNumber() == queriedNumber ? 1 : 0))
                .reduce(0, Integer::sum);
    }
}
