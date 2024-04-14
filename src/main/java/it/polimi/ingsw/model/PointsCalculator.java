package it.polimi.ingsw.model;

import it.polimi.ingsw.utils.CardListUtils;

import java.util.ArrayList;
import java.util.List;

public class PointsCalculator {
    private final List<Card> takenCards;
    public PointsCalculator(List<Card> takenCards) {
        this.takenCards = takenCards;
    }
    private boolean hasGoldsPoint() {
        return CardListUtils.suitsCount(takenCards, Suit.GOLDS) > 5;
    }

    private boolean hasCardsPoint() {
        return takenCards.size() > 20;
    }

    private boolean hasSetteBelloPoint() {
        try {
            return takenCards.contains(new Card(Suit.GOLDS, 7));
        } catch (IllegalCardConstructionException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean hasPrimieraPoint() {
        if(CardListUtils.numbersCount(takenCards, 7) > 2) return true;
        if(CardListUtils.numbersCount(takenCards, 6) > 2) return true;
        return false;
    }

    public List<Points> getPoints() {
        List<Points> points = new ArrayList<>();
        if(hasCardsPoint()) points.add(Points.CARDS);
        if(hasPrimieraPoint()) points.add(Points.PRIMIERA);
        if(hasGoldsPoint()) points.add(Points.GOLD);
        if(hasSetteBelloPoint()) points.add(Points.SETTEBELLO);
        return points;
    }
}
