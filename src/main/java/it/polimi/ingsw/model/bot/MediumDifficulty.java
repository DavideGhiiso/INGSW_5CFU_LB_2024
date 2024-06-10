package it.polimi.ingsw.model.bot;

import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.networking.Server;
import it.polimi.ingsw.utils.CardListUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
/**
 * Class representing the medium difficulty of the bot. The player knows his cards and some already played cards
 */
public class MediumDifficulty extends Difficulty {
    @Override
    public Card chooseCard(List<Card> inHandList, List<Card> onTableList, List<Card> playedCards) {
        List<Card> playerCardsPartial = new ArrayList<>(playedCards);
        removeRandomCards(playerCardsPartial);
        if(Server.DEBUG)
            System.out.println("Remembered list: "+playerCardsPartial);
        if(onTableList.isEmpty()) {
            return CardListUtils.cardWithHighestCount(inHandList, playerCardsPartial);
        }
        return getCardWithMaxWeight(inHandList, onTableList, playerCardsPartial);
    }

    @Override
    protected double calculateWeight(Card card, List<Card> inHandList, List<Card> onTableList, List<Card> playedCards) {
        List<Card> onTableListIfPlaced = Difficulty.simulatePlacement(card, onTableList);
        List<Card> inHandAndPlayedCards = Stream.concat(inHandList.stream(), playedCards.stream()).toList();
        return getSumOfWeight(card, inHandList, onTableList, onTableListIfPlaced, inHandAndPlayedCards);
    }

    private void removeRandomCards(List<Card> cards) {
        int cardToRemove = (int)((Math.random()*(cards.size()-1)));
        int nOfCardsToRemove = (int)((Math.random()*(cards.size()-1)));
        for(int i=0;i < nOfCardsToRemove;i++) {
            cards.remove(cardToRemove);
            cardToRemove = (int)((Math.random()*(cards.size()-1)));
        }
    }
}


