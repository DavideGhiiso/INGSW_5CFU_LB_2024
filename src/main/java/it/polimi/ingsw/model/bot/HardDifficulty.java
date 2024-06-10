package it.polimi.ingsw.model.bot;

import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.utils.CardListUtils;

import java.util.List;
import java.util.stream.Stream;
/**
 * Class representing the medium difficulty of the bot. The player knows his cards and all the already played cards
 */
public class HardDifficulty extends Difficulty{
    @Override
    public Card chooseCard(List<Card> inHandList, List<Card> onTableList, List<Card> playedCards) {
        if(onTableList.isEmpty()) {
            return CardListUtils.cardWithHighestCount(inHandList, playedCards);
        }
        return getCardWithMaxWeight(inHandList, onTableList, playedCards);
    }

    @Override
    protected double calculateWeight(Card card, List<Card> inHandList, List<Card> onTableList, List<Card> playedCards) {
        List<Card> onTableListIfPlaced = Difficulty.simulatePlacement(card, onTableList);
        List<Card> inHandAndPlayedCards = Stream.concat(inHandList.stream(), playedCards.stream()).toList();

        return getSumOfWeight(card, inHandList, onTableList, onTableListIfPlaced, inHandAndPlayedCards);
    }
}
