package it.polimi.ingsw.model.bot;

import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.model.Suit;
import it.polimi.ingsw.networking.Server;
import it.polimi.ingsw.utils.CardListUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Abstract class representing a bot difficulty. It contains all weights associated to the choosing criteria
 */
public abstract class Difficulty {
    protected final static int IN_HAND_VALUE_WEIGHT = 5; // Prioritizes low number cards with more intensity in the first turns
    protected final static int TAKEN_CARDS_WEIGHT = 20; // Prioritizes cards that takes card
    protected final static int DOES_SCOPA_WEIGHT = 300; // Prioritizes cards that does Scopa
    protected final static int SEVEN_PROFICIENCY_WEIGHT = 70; // prioritizes its placement only if it takes at least a card
    protected final static int TAKES_SEVEN_WEIGHT = 60; // Prioritizes its placement if it takes a 7
    protected final static int GOLD_PROFICIENCY_WEIGHT = 60; // If it's a GOLDS, prioritizes its placement only if it takes at least a card
    protected final static int TAKES_GOLDS_WEIGHT = 25; // Prioritizes its placement if it takes a GOLDS card
    protected final static int SCOPA_RISK_WEIGHT = 80; // Calculates the risk that by placing this card the next player might do scopa
    protected final static int SEVEN_RISK_WEIGHT = 70; // Calculates the risk that by placing this card the next player might take a seven.
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
        finalOnTableList.add(card);
        return finalOnTableList;
    }

    protected Card getCardWithMaxWeight(List<Card> inHandList, List<Card> onTableList, List<Card> playedCards) {
        Card returnCard = inHandList.getFirst();
        double maxWeight = -10000;

        for(Card card: inHandList) {
            double currentWeight = calculateWeight(card, inHandList, onTableList, playedCards);
            if(Server.DEBUG)
                System.out.println(card+": "+currentWeight);
            if (currentWeight > maxWeight) {
                maxWeight = currentWeight;
                returnCard = card;
            }
        }
        if(Server.DEBUG)
            System.out.println("Selected: " + returnCard +"\n");
        return returnCard;
    }

    protected abstract double calculateWeight(Card card, List<Card> inHandList, List<Card> onTableList, List<Card> playedCards);

    /**
     * Prioritizes low number cards with more intensity in the first turns
     * This weight is always positive
     */
    protected static double calculateInHandValueProficiency(Card card, List<Card> inHandList) {
        // the intensity is max in the first turn and goes down rapidly with exp decay
        return (11 - card.getNumber()) * Math.exp((double) (inHandList.size() - 10) / 4) * IN_HAND_VALUE_WEIGHT;
    }

    /**
     * Prioritizes cards that takes card.
     * This weight is always positive or 0
     */
    protected static double calculateTakenCardsProficiency(Card card, List<Card> onTableList, List<Card> onTableListIfPlaced) {
        double result = (onTableList.size() - onTableListIfPlaced.size()) * TAKEN_CARDS_WEIGHT;
        return result >= 0 ? result : 0;
    }

    /**
     * Prioritizes cards that does Scopa
     */
    protected static double calculateDoesScopaProficiency(List<Card> onTableListIfPlaced) {
        return (onTableListIfPlaced.isEmpty() ? 1:0) * DOES_SCOPA_WEIGHT;
    }
    /**
     * If it's a 7, prioritizes its placement only if it takes at least a card, else reduces its weight
     */
    protected static double calculateSevenProficiency(Card card, List<Card> onTableList, List<Card> onTableListIfPlaced) {
        return (card.getNumber() == 7 ? 1:0)
                * (hasTakenACard(onTableList, onTableListIfPlaced) ? 1:-1)
                * SEVEN_PROFICIENCY_WEIGHT;
    }
    /**
     * Prioritizes its placement if it takes a 7.
     * This weight is always positive or 0
     */
    protected static double calculateTakesSevenProficiency(Card card, List<Card> onTableList, List<Card> onTableListIfPlaced) {
        int sevenBefore = CardListUtils.numbersCount(onTableList, 7);
        int sevenAfter = CardListUtils.numbersCount(onTableListIfPlaced, 7);
        return (sevenBefore > sevenAfter ? 1:0) * TAKES_SEVEN_WEIGHT;

    }
    /**
     * If it's a GOLDS, prioritizes its placement only if it takes at least a card, else reduces its weight
     */
    protected static double calculateGoldProficiency(Card card, List<Card> onTableList, List<Card> onTableListIfPlaced) {
        return (card.getSuit() == Suit.GOLDS ? 1:0)
                * (hasTakenACard(onTableList, onTableListIfPlaced) ? 1:-1)
                * GOLD_PROFICIENCY_WEIGHT;
    }

    /**
     * Prioritizes its placement if it takes a GOLDS.
     * This weight is always positive or 0 and proportional to number of taken
     */
    protected static double calculateTakesGoldProficiency(Card card, List<Card> onTableList, List<Card> onTableListIfPlaced) {
        int goldsBefore = CardListUtils.suitsCount(onTableList, Suit.GOLDS);
        int goldsAfter = CardListUtils.suitsCount(onTableListIfPlaced,Suit.GOLDS);
        int takenGolds = goldsBefore - goldsAfter;
        return (Math.max(takenGolds, 0)) * TAKES_GOLDS_WEIGHT;

    }

    /**
     * Calculates the risk that by placing this card the next player might do scopa.
     * If scopa has been made or remaining cards adds up to more than 10 there's no risk:
     * else the risk depends on how many cards can still scopa.
     * This weight is always negative or 0.
     * @param inHandAndPlayedCards in hand + already played cards
     */
    protected static double calculateScopaRisk(List<Card> onTableList, List<Card> onTableListIfPlaced, List<Card> inHandAndPlayedCards) {
        if(onTableListIfPlaced.isEmpty()) return 0; // no risk
        int sumOfCardsIfPlaced = CardListUtils.sumOfNumbers(onTableListIfPlaced); // sum of number on table if card is placed
        if(sumOfCardsIfPlaced > 10) return 0; // no risk
        int nOfCardsThatCanStillScopa = 4 - CardListUtils.numbersCount(Stream.concat(inHandAndPlayedCards.stream(), onTableList.stream()).toList(), sumOfCardsIfPlaced);
        return -(nOfCardsThatCanStillScopa * SCOPA_RISK_WEIGHT);
    }

    /**
     * Calculates the risk that by placing this card the next player might take a seven.
     * If opponents have no sevens there is no risk: else the risk depends on how many sevens remains and if there is a
     * seven combination on table.
     * This weight is always negative or 0.
     * @param inHandAndPlayedCards in hand + already played cards
     */
    protected static double calculateSevenRisk(List<Card> onTableListIfPlaced, List<Card> inHandAndPlayedCards) {
        int remainingSeven = 4 - CardListUtils.numbersCount(inHandAndPlayedCards, 7);
        if(remainingSeven == 0) return 0;
        List<List<Card>> combinations = CardListUtils.getAllCombinations(onTableListIfPlaced, 3);
        boolean sevenCombinationPresent = combinations.stream()
                                            .map(CardListUtils::sumOfNumbers)
                                            .anyMatch(sum -> sum == 7);
        return -(remainingSeven * (sevenCombinationPresent ? 1:0) * SEVEN_RISK_WEIGHT);
    }

    /**
     * Returns true if the onTableListIfPlaced has fewer cards than onTableList
     */
    protected static boolean hasTakenACard(List<Card> onTableList, List<Card> onTableListIfPlaced) {
        return onTableList.size() > onTableListIfPlaced.size();
    }

    protected static double getSumOfWeight(Card card, List<Card> inHandList, List<Card> onTableList, List<Card> onTableListIfPlaced) {
        return calculateInHandValueProficiency(card, inHandList) +
                calculateTakenCardsProficiency(card, onTableList, onTableListIfPlaced) +
                calculateDoesScopaProficiency(onTableListIfPlaced) +
                calculateSevenProficiency(card, onTableList, onTableListIfPlaced) +
                calculateGoldProficiency(card, onTableList, onTableListIfPlaced) +
                calculateTakesGoldProficiency(card, onTableList, onTableListIfPlaced) +
                calculateScopaRisk(onTableList, onTableListIfPlaced, inHandList) +
                calculateSevenRisk(onTableListIfPlaced, inHandList) +
                calculateTakesSevenProficiency(card, onTableList, onTableListIfPlaced);
    }

    protected static double getSumOfWeight(Card card, List<Card> inHandList, List<Card> onTableList, List<Card> onTableListIfPlaced, List<Card> inHandAndPlayedCards) {
        return calculateInHandValueProficiency(card, inHandList) +
                calculateTakenCardsProficiency(card, onTableList, onTableListIfPlaced) +
                calculateDoesScopaProficiency(onTableListIfPlaced) +
                calculateSevenProficiency(card, onTableList, onTableListIfPlaced) +
                calculateGoldProficiency(card, onTableList, onTableListIfPlaced) +
                calculateTakesGoldProficiency(card, onTableList, onTableListIfPlaced) +
                calculateScopaRisk(onTableList, onTableListIfPlaced, inHandAndPlayedCards) +
                calculateSevenRisk(onTableListIfPlaced, inHandAndPlayedCards) +
                calculateTakesSevenProficiency(card, onTableList, onTableListIfPlaced);
    }
}
