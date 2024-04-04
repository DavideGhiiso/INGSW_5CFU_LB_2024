package it.polimi.ingsw.model.bot;

import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.utils.CardListUtils;

import java.util.Comparator;
import java.util.List;

public class EasyDifficulty extends Difficulty {
    public EasyDifficulty() {
        IN_HAND_COUNT_WEIGHT = 10;
    }
    @Override
    public Card chooseCard(List<Card> inHandList, List<Card> onTableList, List<Card> playedCards) {
        Card returnCard = inHandList.getFirst();
        double maxWeight = 0;

        // table is empty: play the card you have the most (starting from lowest by number)
        if(onTableList.isEmpty()) {
            return cardWithHighestCount(inHandList);
        }

        for(Card card: inHandList) {
            double currentWeight = calculateWeight(card, inHandList, onTableList, playedCards);
            if (currentWeight > maxWeight) {
                maxWeight = currentWeight;
                returnCard = card;
            }
        }

        return returnCard;
    }

    /**
     * TODO
     */
    private static double calculateWeight(Card card, List<Card> inHandList, List<Card> onTableList, List<Card> playedCards) {
        List<Card> onTableListIfPlaced = Difficulty.simulatePlacement(card, onTableList);
        return 0;
    }

    private static Card cardWithHighestCount(List<Card> inHandList) {
        Card cardWithHighestCount = inHandList.getLast();
        int maxCount = 0;
        for(Card card: inHandList.reversed()) {
            int nOfSameCardsInHand = CardListUtils.numbersCount(inHandList, card.getNumber());
            if(nOfSameCardsInHand > maxCount) {
                maxCount = nOfSameCardsInHand;
                cardWithHighestCount = card;
            }
        }
        return cardWithHighestCount;
    }


}
