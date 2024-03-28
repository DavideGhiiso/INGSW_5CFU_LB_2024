package it.polimi.ingsw.model;

import java.io.Serializable;

/**
 * Class that defines a card
 */
public class Card implements Serializable, Comparable<Card> {
    private final Suit suit;
    private final int number;

    public Card(Suit suit, int number) throws IllegalCardConstructionException {
        if(number > 10 || number < 1) throw new IllegalCardConstructionException(suit, number);
        this.suit = suit;
        this.number = number;
    }

    /**
     * Method that allows to sort the cards inside an Iterable. Cards are sorted in an descending order by their number.
     * If the numbers are equal, the GOLDS suit has the priority.
     * @param otherCard the card to be compared.
     * @return
     * <ul>
     *     <li>a negative value if this number is greater than the otherCard number or the numbers are equal and this suit
     *     is GOLDS</li>
     *     <li>a positive value if this number is less than the otherCard number or the numbers are equal and other suit
     *     is GOLDS</li>
     *     <li>0 if the numbers are equal and suits are both GOLDS or not GOLDS</li>
     * </ul>
     */
    @Override
    public int compareTo(Card otherCard) {
        int integerCompare = Integer.compare(otherCard.getNumber(), this.number);
        if(integerCompare != 0) return integerCompare;
        return Suit.compare(otherCard.getSuit(), this.suit);
    }

    public Suit getSuit() {
        return suit;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return number + " of " + suit.toString();
    }
}
