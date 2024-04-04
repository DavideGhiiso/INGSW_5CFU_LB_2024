package it.polimi.ingsw.model.bot;

import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.model.Suit;
import it.polimi.ingsw.utils.CardListUtils;

import java.util.ArrayList;
import java.util.List;

public abstract class Difficulty {
    protected static int IN_HAND_COUNT_WEIGHT;
    protected static int IN_HAND_VALUE_WEIGHT;
    protected static int TAKEN_CARDS_WEIGHT;
    protected static int IS_SEVEN_WEIGHT;
    protected static int SCOPA_RISK_WEIGHT;
    protected static int DOES_SCOPA_WEIGHT;
    protected static int GOLD_PROFITABILITY_WEIGHT;
    protected static int SEVEN_PROFITABILITY_WEIGHT;
    protected static int GOLDS_WEIGHT;
    public abstract Card chooseCard(List<Card> inHandList, List<Card> onTableList, List<Card> playedCards);

    /**
     * Simulates the placement of a card by returning the table if the passed card is placed
     * @param card card to be placed
     * @param onTableList list of card on table
     * @return list representing the table after the card gets placed
     */
    public static List<Card> simulatePlacement(Card card, List<Card> onTableList) {
        List<Card> finalOnTableList = new ArrayList<>(onTableList);
        List<List<Card>> combinations = CardListUtils.getAllCombinations(onTableList,4);

        // if card can take 1 or more cards, remove from simulated list:
        for(List<Card> combination: combinations) {
            if(card.getNumber() == CardListUtils.sumOfNumbers(combination)) {
                finalOnTableList.remove(card);
                finalOnTableList.removeAll(combination);
                return finalOnTableList;
            }
        }
        return finalOnTableList;
    }

    /**
     * Prioritizes low number cards with more intensity in the first turns
     */
    protected static double calculateInHandValueWeight(Card card, List<Card> inHandList) {
        return (11 - card.getNumber()) * Math.exp((double) (inHandList.size() - 10) / 4) * IN_HAND_VALUE_WEIGHT; // the intensity is max in the first turn and goes down rapidly
    }

    /**
     * Prioritizes cards that takes card
     */
    protected static double calculateTakenCardsWeight(Card card, List<Card> onTableList, List<Card> onTableListIfPlaced) {
        return (onTableList.size() - onTableListIfPlaced.size()) * TAKEN_CARDS_WEIGHT;
    }

    /**
     * Prioritizes cards that does Scopa
     */
    protected static double calculateDoesScopaWeight(List<Card> onTableListIfPlaced) {
        return (onTableListIfPlaced.isEmpty() ? 1:0) * DOES_SCOPA_WEIGHT;
    }
    /**
     * If it's a 7, prioritizes its placement only if it takes at least a card, else reduces its weight
     */
    protected static double calculateSevenProfitability(Card card, List<Card> onTableList, List<Card> onTableListIfPlaced) {
        return (card.getNumber() == 7 ? 1:0)
                * (hasTakenACard(onTableList, onTableListIfPlaced) ? 1:-1)
                * SEVEN_PROFITABILITY_WEIGHT;
    }
    /**
     * If it's a GOLDS, prioritizes its placement only if it takes at least a card, else reduces its weight
     */
    protected static double calculateGoldProfitability(Card card, List<Card> onTableList, List<Card> onTableListIfPlaced) {
        return (card.getSuit() == Suit.GOLDS ? 1:0)
                * (hasTakenACard(onTableList, onTableListIfPlaced) ? 1:-1)
                * SEVEN_PROFITABILITY_WEIGHT;
    }

    /**
     * Returns true if the onTableListIfPlaced has fewer cards than onTableList
     */
    protected static boolean hasTakenACard(List<Card> onTableList, List<Card> onTableListIfPlaced) {
        return onTableList.size() > onTableListIfPlaced.size();
    }
}
