package it.polimi.ingsw.model;

import java.util.Comparator;
import java.util.List;

/**
 * Implementation of comparator to compare two list of cards.
 */
public class ListofCardsComparator implements Comparator<List<Card>> {

    /**
     * Compare method that allows to compare lists of cards. Lists are sorted in descending order by their length.
     * If the lengths are equal, the number of GOLDS cards has the priority.
     * @param o1 the first card to be compared.
     * @param o2 the second card to be compared.
     * @return
     * <ul>
     *     <li>a positive value if o1 is longer than o2 or the lengths are equal and o1
     *     has more GOLDS</li>
     *     <li>a negative value if o2 is longer than o1 or the lengths are equal and o2
     *      has more GOLDS</li>
     *     <li>0 if the numbers are equal and suits are both GOLDS or not GOLDS</li>
     * </ul>
     */
    @Override
    public int compare(List<Card> o1, List<Card> o2) {
        int integerCompare = Integer.compare(o1.size(), o2.size());
        if(integerCompare != 0) return integerCompare;
        return goldsCount(o2) - goldsCount(o1);
    }

    private int goldsCount(List<Card> cards) {
        return cards.stream()
                .map(c -> (c.getSuit().equals(Suit.GOLDS) ? 1:0))
                .reduce(0, Integer::sum);
    }
}


