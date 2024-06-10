package it.polimi.ingsw.events.data.server;

import it.polimi.ingsw.events.data.BaseEvent;
import it.polimi.ingsw.model.Card;

import java.util.List;

/**
 * Event used by the server to notify a client that his hand is changed
 */
public class HandChangedEvent extends BaseEvent {
    private final List<Card> cards;
    public HandChangedEvent(List<Card> hand) {
        ID = "HAND_CHANGED_EVENT";
        cards = hand;
    }

    public List<Card> getHand() {
        return cards;
    }
}
