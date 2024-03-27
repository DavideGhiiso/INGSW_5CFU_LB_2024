package it.polimi.ingsw.model;

import java.io.Serializable;

public class Card implements Serializable, Comparable<Card> {
    private final Suit suit;
    private final int number;

    public Card(Suit suit, int number) {
        this.suit = suit;
        this.number = number;
    }

    @Override
    public int compareTo(Card otherCard) {
        int integerCompare = Integer.compare(this.number, otherCard.getNumber());
        if(integerCompare != 0) return integerCompare;
        return Suit.compare(this.suit, otherCard.getSuit());
    }

    public Suit getSuit() {
        return suit;
    }

    public int getNumber() {
        return number;
    }


}
