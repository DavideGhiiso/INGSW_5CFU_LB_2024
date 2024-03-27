package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Table {
    private final List<Card> placedCards;
    public Table() {
        placedCards = new ArrayList<>();
    }

    public void placeCard(Card card) {
        placedCards.add(card);
        Collections.sort(placedCards);
    }

    @Override
    public String toString() {
        return "" + placedCards;
    }
}
