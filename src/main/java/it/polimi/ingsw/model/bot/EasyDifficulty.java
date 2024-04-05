package it.polimi.ingsw.model.bot;

import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.utils.CardListUtils;

import java.util.List;

public class EasyDifficulty extends Difficulty {
    public EasyDifficulty() {
        IN_HAND_COUNT_WEIGHT = 1;
    }
    @Override
    public Card chooseCard(List<Card> inHandList, List<Card> onTableList, List<Card> playedCards) {
        // table is empty: play the card you have the most (starting from the lowest by number)
        if(onTableList.isEmpty()) {
            return CardListUtils.cardWithHighestCount(inHandList);
        }

        Card returnCard = inHandList.getFirst();
        double maxWeight = 0;

        for(Card card: inHandList) {
            double currentWeight = calculateWeight(card, inHandList, onTableList, playedCards);
            if (currentWeight > maxWeight) {
                maxWeight = currentWeight;
                returnCard = card;
            }
        }
        return returnCard;
    }

    private static double calculateWeight(Card card, List<Card> inHandList, List<Card> onTableList, List<Card> playedCards) {
        List<Card> onTableListIfPlaced = Difficulty.simulatePlacement(card, onTableList);
        //debugPrint(card, inHandList, onTableList, onTableListIfPlaced);

        return  calculateInHandValueProficiency(card, inHandList) +
                calculateTakenCardsProficiency(card, onTableList, onTableListIfPlaced) +
                calculateDoesScopaProficiency(onTableListIfPlaced) +
                calculateSevenProficiency(card, onTableList, onTableListIfPlaced) +
                calculateGoldProficiency(card, onTableList, onTableListIfPlaced) +
                calculateScopaRisk(onTableList, onTableListIfPlaced, inHandList) +
                calculateSevenRisk(onTableListIfPlaced, inHandList) +
                calculateTakesSevenProficiency(card, onTableList, onTableListIfPlaced);
    }

    /**
     * TODO: REMOVE
     */
    private static void debugPrint(Card card, List<Card> inHandList, List<Card> onTableList, List<Card> onTableListIfPlaced) {
        System.out.println("\n" + card.toString());
        System.out.println("\tin hand \t" + calculateInHandValueProficiency(card, inHandList));
        System.out.println("\ttaken cards\t" + calculateTakenCardsProficiency(card, onTableList, onTableListIfPlaced));
        System.out.println("\ttakes seven\t" + calculateTakesSevenProficiency(card, onTableList, onTableListIfPlaced));
        System.out.println("\tdoes scopa\t" + calculateDoesScopaProficiency(onTableListIfPlaced));
        System.out.println("\tis seven\t" + calculateSevenProficiency(card, onTableList, onTableListIfPlaced));
        System.out.println("\tis gold \t" + calculateGoldProficiency(card, onTableList, onTableListIfPlaced));
        System.out.println("\tscopa risk\t" + calculateScopaRisk(onTableList, onTableListIfPlaced, inHandList));
        System.out.println("\tseven risk\t" + calculateSevenRisk(onTableListIfPlaced, inHandList));
        System.out.println("Simulated placement: " + onTableListIfPlaced.toString());
    }
}
