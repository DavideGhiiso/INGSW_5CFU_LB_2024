package it.polimi.ingsw.utils;

import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.model.ListofCardsComparator;
import it.polimi.ingsw.model.Suit;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Class containing all static method used to manipulate lists of cards
 */
public class CardListUtils {
    public static int sumOfNumbers(List<Card> cards) {
        return cards.stream()
                .map(Card::getNumber)
                .reduce(0, Integer::sum);
    }

    public static int suitsCount(List<Card> cards, Suit queriedSuit) {
        return (int) cards.stream()
                .filter(card -> card.getSuit() == queriedSuit)
                .count();
    }

    public static int numbersCount(List<Card> cards, int queriedNumber) {
        return (int) cards.stream()
                .filter(card -> card.getNumber() == queriedNumber)
                .count();
    }

    /**
     * Method that generates every possible unordered combination without repetition of length maxCombinationsLength
     * of passed card list. The combinations are ordered in ascending length order. If two combinations are the same
     * size, a combination containing GOLDS comes first.
     * @param cardList target list
     * @param maxCombinationsLength max length of the combinations
     * @return a list of list containing every possible unordered combination without repetition
     */
    public static List<List<Card>> getAllCombinations(List<Card> cardList, int maxCombinationsLength) {
        List<List<Card>> combinations = new ArrayList<>();
        for(Card card: cardList) {
            List<Card> firstElement = new ArrayList<>();
            firstElement.add(card);
            getCombinations(combinations, firstElement, cardList, cardList.indexOf(card), maxCombinationsLength);
        }
        combinations.sort((l1, l2) -> (new ListofCardsComparator()).compare(l1, l2));
        return combinations;
    }

    /**
     * Method that generates all possible unordered combination of a single card of length less or equal than maxLength
     * and puts them in combinationsContainer list of lists of card
     * @param combinationsContainer list of lists that will contain all combinations at the end of the recursion
     * @param previousCombination previous recursive call combination
     * @param cards List from which combination are generated
     * @param startingIndex index to start iteration
     * @param maxLength max combinations length
     */
    public static void getCombinations(
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

    /**
     * Returns the card that appears more often in the list. Prioritize low cards
     * @param cardList list to inspect
     * @return the card that appears more often in the list
     */
    public static Card cardWithHighestCount(List<Card> cardList) {
        Card cardWithHighestCount = cardList.getLast();
        int maxCount = 0;
        for(Card card: cardList.reversed()) {
            int nOfSameCardsInHand = CardListUtils.numbersCount(cardList, card.getNumber());
            if(nOfSameCardsInHand > maxCount) {
                maxCount = nOfSameCardsInHand;
                cardWithHighestCount = card;
            }
        }
        return cardWithHighestCount;
    }

    /**
     * Returns the card that appears more often in the two lists and is contained in the first list. Prioritize low cards
     * @param cardList list that must contain the returned card
     * @param secondaryCardList secondary list
     * @return the card that appears more often in the list
     */
    public static Card cardWithHighestCount(List<Card> cardList, List<Card> secondaryCardList) {
        Card cardWithHighestCount = cardList.getLast();
        int maxCount = 0;
        for(Card card: cardList.reversed()) {
            int nOfSameCardsInHand = CardListUtils.numbersCount(
                    Stream.concat(cardList.stream(), secondaryCardList.stream()).toList(),
                    card.getNumber());
            if(nOfSameCardsInHand > maxCount) {
                maxCount = nOfSameCardsInHand;
                cardWithHighestCount = card;
            }
        }
        return cardWithHighestCount;
    }
}
