package it.polimi.ingsw.events.data.server;

import it.polimi.ingsw.events.data.BaseEvent;
import it.polimi.ingsw.model.Card;

import java.util.List;

/**
 * Event that contains the new state of the table that must be displayed
 */
public class TableChangedEvent extends BaseEvent {
    private final List<Card> cards;
    private final Card playedCard;
    public TableChangedEvent(List<Card> currentPlacedCards, Card playedCard) {
        ID = "TABLE_CHANGED_EVENT";
        cards = currentPlacedCards;
        this.playedCard = playedCard;
    }

    public List<Card> getCards() {
        return cards;
    }

    public Card getPlayedCard() {
        return playedCard;
    }
}
