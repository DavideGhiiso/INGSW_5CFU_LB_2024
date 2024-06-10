package it.polimi.ingsw.model.bot;

import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.utils.CardListUtils;

import java.util.List;

/**
 * Class representing the easy difficulty of the bot. The player only knows his cards and adds a level of randomness
 * (uncertainty) to its choice
 */
public class EasyDifficulty extends Difficulty {
    private static final int UNCERTAINTY_WEIGHT = 20; // Weight that adds a certain level of uncertainty to the bot choice
    public EasyDifficulty() {
    }
    @Override
    public Card chooseCard(List<Card> inHandList, List<Card> onTableList, List<Card> playedCards) {
        // table is empty: play the card you have the most (starting from the lowest by number)
        if(onTableList.isEmpty()) {
            return CardListUtils.cardWithHighestCount(inHandList);
        }

        return getCardWithMaxWeight(inHandList, onTableList, playedCards);
    }

     protected double calculateWeight(Card card, List<Card> inHandList, List<Card> onTableList, List<Card> playedCards) {
        List<Card> onTableListIfPlaced = Difficulty.simulatePlacement(card, onTableList);
        //debugPrint(card, inHandList, onTableList, onTableListIfPlaced);
        int uncertainty = (int)((Math.random() - 0.5)*UNCERTAINTY_WEIGHT);
         System.out.println("Unc: "+uncertainty);
        return getSumOfWeight(card, inHandList, onTableList, onTableListIfPlaced) + uncertainty;
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
