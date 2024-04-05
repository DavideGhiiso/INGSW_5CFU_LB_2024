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
        debugPrint(card, inHandList, onTableList, onTableListIfPlaced);

        return  calculateInHandValueProfitability(card, inHandList) +
                calculateTakenCardsProfitability(card, onTableList, onTableListIfPlaced) +
                calculateDoesScopaProfitability(onTableListIfPlaced) +
                calculateSevenProfitability(card, onTableList, onTableListIfPlaced) +
                calculateGoldProfitability(card, onTableList, onTableListIfPlaced) +
                calculateScopaRisk(onTableListIfPlaced, inHandList) +
                calculateSevenRisk(onTableListIfPlaced, inHandList);
    }

    /**
     * TODO: REMOVE
     */
    private static void debugPrint(Card card, List<Card> inHandList, List<Card> onTableList, List<Card> onTableListIfPlaced) {
        System.out.println("\n" + card.toString());
        System.out.println("\tin hand \t" + calculateInHandValueProfitability(card, inHandList));
        System.out.println("\ttaken cards\t" + calculateTakenCardsProfitability(card, onTableList, onTableListIfPlaced));
        System.out.println("\tdoes scopa\t" + calculateDoesScopaProfitability(onTableListIfPlaced));
        System.out.println("\tis seven\t" + calculateSevenProfitability(card, onTableList, onTableListIfPlaced));
        System.out.println("\tis gold \t" + calculateGoldProfitability(card, onTableList, onTableListIfPlaced));
        System.out.println("\tscopa risk\t" + calculateScopaRisk(onTableListIfPlaced, inHandList));
        System.out.println("\tseven risk\t" + calculateSevenRisk(onTableListIfPlaced, inHandList));
        System.out.println("Simulated placement: " + onTableListIfPlaced.toString());
    }
}
