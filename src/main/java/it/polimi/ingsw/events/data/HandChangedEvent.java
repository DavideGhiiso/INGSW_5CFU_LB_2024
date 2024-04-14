package it.polimi.ingsw.events.data;

import it.polimi.ingsw.model.Card;

import java.util.List;

public class HandChangedEvent extends BaseEvent {
    List<Card> cards;
    public HandChangedEvent(List<Card> hand) {
        cards = hand;
    }

    public List<Card> getNewHand() {
        return cards;
    }
}
