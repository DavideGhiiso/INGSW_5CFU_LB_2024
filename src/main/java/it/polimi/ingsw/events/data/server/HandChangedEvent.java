package it.polimi.ingsw.events.data.server;

import it.polimi.ingsw.events.data.BaseEvent;
import it.polimi.ingsw.model.Card;

import java.util.List;

public class HandChangedEvent extends BaseEvent {
    List<Card> cards;
    public HandChangedEvent(List<Card> hand) {
        ID = "HAND_CHANGED_EVENT";
        cards = hand;
    }

    public List<Card> getHand() {
        return cards;
    }
}
