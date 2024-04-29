package it.polimi.ingsw.events.data.server;

import it.polimi.ingsw.events.data.BaseEvent;
import it.polimi.ingsw.model.Card;

import java.util.List;

/**
 * Event that contains the new state of the table that must be displayed
 */
public class TableChangedEvent extends BaseEvent {
    private final List<Card> cards;
    public TableChangedEvent(List<Card> currentPlacedCards) {
        ID = "TABLE_CHANGED_EVENT";
        cards = currentPlacedCards;
    }

    public List<Card> getCards() {
        return cards;
    }
}
