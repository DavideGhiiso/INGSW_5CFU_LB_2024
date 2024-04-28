package it.polimi.ingsw.events.data.server;

import it.polimi.ingsw.events.data.BaseEvent;
import it.polimi.ingsw.model.Card;

import java.util.List;

/**
 * Event that contains the new state of the table that must be displayed
 */
public class TableChangedEvent extends BaseEvent {
    List<Card> cards;
    public TableChangedEvent(List<Card> currentPlacedCards) {
        cards = currentPlacedCards;
    }

    public List<Card> getCards() {
        return cards;
    }
}
