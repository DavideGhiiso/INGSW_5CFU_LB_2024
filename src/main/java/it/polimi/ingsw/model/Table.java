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
        List<List<Card>> combinations = getAllCombinations(3); // is not possible to take more than 3 cards with 1 card

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



    /**
     * Method that generates every possible unordered combination without repetition of length maxCombinationsLength
     * from this placedCards. The combinations are ordered in ascending length order. If two combinations are the same
     * size, a combination containing GOLDS comes first.
     * @param maxCombinationsLength max length of the combinations
     * @return a list of list containing every possible unordered combination without repetition
     */
    private List<List<Card>> getAllCombinations(int maxCombinationsLength) {
        List<List<Card>> combinations = new ArrayList<>();
        for(Card card: placedCards) {
            List<Card> firstElement = new ArrayList<>();
            firstElement.add(card);
            getCombinations(combinations, firstElement, placedCards, placedCards.indexOf(card), maxCombinationsLength);
        }
        combinations.sort((l1, l2) -> (new ListofCardsComparator()).compare(l1, l2));
        return combinations;
    }

    /**
     * TODO: set private
     * Method that generates all possible unordered combination of a single card of length less or equal than maxLength
     * and puts them in combinationsContainer list of lists of card
     * @param combinationsContainer list of lists that will contain all combinations at the end of the recursion
     * @param previousCombination previous recursive call combination
     * @param cards List from which combination are generated
     * @param startingIndex index to start iteration
     * @param maxLength max combinations length
     */
    public void getCombinations(
            List<List<Card>> combinationsContainer,
            List<Card> previousCombination,
            List<Card> cards,
            int startingIndex,
            int maxLength
    ) {
        if(previousCombination.size() >= maxLength) return;
        for(int i = startingIndex ; i < cards.size(); i++) {
            List<Card> newCombination = new ArrayList<>(previousCombination);

            if(! newCombination.contains(cards.get(i))) newCombination.add(cards.get(i));
            if(! combinationsContainer.contains(newCombination)) combinationsContainer.add(newCombination);
            getCombinations(combinationsContainer,newCombination,cards,++startingIndex, maxLength);
        }
    }
}
