package it.polimi.ingsw.model;

/**
 * Enum containing all possible cards suit
 */
public enum Suit {
    CUPS,
    SWORDS,
    CLUBS,
    GOLDS;


    public static int compare(Suit suit1, Suit suit2) {
        if(suit1 == GOLDS && suit2 == GOLDS) return 0;
        if(suit1 == GOLDS) return 1;
        if(suit2 == GOLDS) return -1;
        return 0;
    }
}
