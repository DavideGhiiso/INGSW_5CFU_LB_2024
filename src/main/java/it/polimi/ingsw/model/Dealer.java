package it.polimi.ingsw.model;

import it.polimi.ingsw.model.exceptions.IllegalCardConstructionException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Factory class that generates all cards and generates all Player's hands
 */
public class Dealer {
    private final int MAX_NUMBER = 10;
    private final int MIN_NUMBER = 1;
    private final int HAND_SIZE = 10;
    private final List<Card> cards;

    public Dealer() {
        this.cards = new ArrayList<>();
        generateDeck();
        Collections.shuffle(cards);
    }

    /**
     * Returns ten random cards from the deck
     * @return a list of Cards of size HAND_SIZE
     */
    public List<Card> getCardsHand() {
        List<Card> result = new ArrayList<>(cards.subList(0,HAND_SIZE));
        cards.subList(0,HAND_SIZE).clear();
        result.sort(Card::compareTo);
        return result;
    }

    public void takeBackHand(List<Card> hand) {
        cards.addAll(hand);
    }

    private void generateDeck() {
        for(Suit suit: Suit.values()) {
            for(int number = MIN_NUMBER; number <= MAX_NUMBER; number++) {
                try {
                    cards.add(new Card(suit, number));
                } catch (IllegalCardConstructionException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
