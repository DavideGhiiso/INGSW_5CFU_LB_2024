package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exceptions.IllegalCardConstructionException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PointsCalculatorTest {
    private PointsCalculator pointsCalculator;

    @Test
    public void setteBelloPointsTest() {
        try {
            pointsCalculator = new PointsCalculator(
                    List.of(new Card(Suit.GOLDS, 7))
            );
        } catch (IllegalCardConstructionException e) {
            throw new RuntimeException(e);
        }
        assertTrue(pointsCalculator.getPoints().contains(Points.SETTEBELLO));
    }
    @Test
    public void goldsPointsTest() {
        try {
            pointsCalculator = new PointsCalculator(
                    List.of(new Card(Suit.GOLDS, 1),
                            new Card(Suit.GOLDS, 2),
                            new Card(Suit.GOLDS, 3),
                            new Card(Suit.GOLDS, 4),
                            new Card(Suit.GOLDS, 5),
                            new Card(Suit.GOLDS, 6))
            );
        } catch (IllegalCardConstructionException e) {
            throw new RuntimeException(e);
        }
        assertTrue(pointsCalculator.getPoints().contains(Points.GOLD));
    }
    @Test
    public void cardsPointsTest() {
        try {
            pointsCalculator = new PointsCalculator(
                    List.of(new Card(Suit.CUPS, 1),
                            new Card(Suit.GOLDS, 2),
                            new Card(Suit.SWORDS, 3),
                            new Card(Suit.CLUBS, 4),
                            new Card(Suit.CUPS, 5),
                            new Card(Suit.CLUBS, 6),
                            new Card(Suit.CUPS, 2),
                            new Card(Suit.SWORDS, 4),
                            new Card(Suit.CLUBS, 7),
                            new Card(Suit.GOLDS, 2),
                            new Card(Suit.GOLDS, 7),
                            new Card(Suit.GOLDS, 9),
                            new Card(Suit.GOLDS, 2),
                            new Card(Suit.SWORDS, 10),
                            new Card(Suit.GOLDS, 2),
                            new Card(Suit.GOLDS, 4),
                            new Card(Suit.CLUBS, 5),
                            new Card(Suit.GOLDS, 7),
                            new Card(Suit.GOLDS, 3),
                            new Card(Suit.CUPS, 7),
                            new Card(Suit.GOLDS, 10))
            );
        } catch (IllegalCardConstructionException e) {
            throw new RuntimeException(e);
        }
        assertTrue(pointsCalculator.getPoints().contains(Points.CARDS));
    }
}
