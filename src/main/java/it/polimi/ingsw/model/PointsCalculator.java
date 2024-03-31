package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.CardListUtils;

import java.util.List;

public class PointsCalculator {
    private final List<Card> takenCards;
    public PointsCalculator(List<Card> takenCards) {
        this.takenCards = takenCards;
    }
    public boolean hasGoldsPoint() {
        return CardListUtils.suitsCount(takenCards, Suit.GOLDS) > 5;
    }

    public boolean hasCardsPoint() {
        return takenCards.size() > 20;
    }

    public boolean hasSetteBelloPoint() {
        try {
            return takenCards.contains(new Card(Suit.GOLDS, 7));
        } catch (IllegalCardConstructionException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean hasPrimieraPoint() {
        if(CardListUtils.numbersCount(takenCards, 7) > 2) return true;
        if(CardListUtils.numbersCount(takenCards, 6) > 2) return true;
        return false;
    }
}
