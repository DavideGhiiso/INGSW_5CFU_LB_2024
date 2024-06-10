package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exceptions.IllegalCardConstructionException;
import it.polimi.ingsw.utils.CardListUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that contains all methods necessary to calculate end game results
 */
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
        if(!hasAtLeastACardPerSuit())
            return false;
        int nOfSeven = CardListUtils.numbersCount(takenCards, 7);
        int nOfSix = CardListUtils.numbersCount(takenCards, 6);
        int nOfOne = CardListUtils.numbersCount(takenCards, 1);
        int nOfFive = CardListUtils.numbersCount(takenCards, 5);
        int nOfFour = CardListUtils.numbersCount(takenCards, 4);
        int nOfThree = CardListUtils.numbersCount(takenCards, 3);
        int nOfTwo = CardListUtils.numbersCount(takenCards, 2);
        return nOfSeven > 2 ||
                nOfSeven == 2 && nOfSix > 2 ||
                nOfSix == 2 && nOfOne > 2 ||
                nOfOne == 2 && nOfFive > 2 ||
                nOfFive == 2 && nOfFour > 2 ||
                nOfFour == 2 && nOfThree > 2 ||
                nOfThree == 2 && nOfTwo > 2;
    }

    private boolean hasAtLeastACardPerSuit() {
        return  CardListUtils.suitsCount(takenCards, Suit.GOLDS) > 0 &&
                CardListUtils.suitsCount(takenCards, Suit.CLUBS) > 0 &&
                CardListUtils.suitsCount(takenCards, Suit.CUPS) > 0 &&
                CardListUtils.suitsCount(takenCards, Suit.SWORDS) > 0;
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
