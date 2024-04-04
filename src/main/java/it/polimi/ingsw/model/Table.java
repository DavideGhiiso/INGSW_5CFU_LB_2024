package it.polimi.ingsw.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import it.polimi.ingsw.utils.CardListUtils;

public class Table {
    private final List<Card> placedCards;
    public Table() {
        placedCards = new ArrayList<>();
    }

    /**
     * Adds a card to the placeCards list and sorts the list according to the compare rule defined in the {@link Card}
     * Comparable class.
     * @param card card object to add
     */
    public void addCard(Card card) {
        placedCards.add(card);
        Collections.sort(placedCards);
    }


    public List<Card> getPlacedCards() {
        return placedCards;
    }

    /**
     * Method that handles the placement of a card and returns the eventual taken cards. Two cases are possible:
     * <ul>
     *     <li>
     *         if the card can take 1 or more cards on the table, these cards are removed from the table
     *         and returned along with played card
     *     </li>
     *     <li>
     *         if the card can't take any card, it's added on the table
     *     </li>
     * </ul>
     * @param card played card
     * @return the list of taken card. The list is empty if the card is placed on the table
     */
    public List<Card> placeCard(Card card) {
        List<Card> takenCards = new ArrayList<>();
        List<List<Card>> combinations = CardListUtils.getAllCombinations(placedCards,4); // is not possible to take more than 4 cards with 1 card

        // if card can take 1 or more cards, adds it and takenCard to takenCards:
        for(List<Card> combination: combinations) {
            if(card.getNumber() == CardListUtils.sumOfNumbers(combination)) {
                takenCards.add(card);
                takenCards.addAll(combination);
                placedCards.removeAll(combination);
                return takenCards;
            }
        }
        // else place card on table:
        placedCards.add(card);
        return takenCards;
    }


}
