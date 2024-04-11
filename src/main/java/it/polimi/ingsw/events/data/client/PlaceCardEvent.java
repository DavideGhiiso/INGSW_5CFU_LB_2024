package it.polimi.ingsw.events.data.client;

import it.polimi.ingsw.events.data.BaseEvent;
import it.polimi.ingsw.model.Card;
/**
 * Event that represent a player drawing a covered PlaceableCard from a deck
 */
public class PlaceCardEvent extends BaseEvent {
    private final Card card;
    public PlaceCardEvent(Card card) {
        this.ID = "PLACE_CARD_EVENT";
        this.card = card;
        requiresConnection = true;
    }

    public Card getCard() {
        return card;
    }
}
