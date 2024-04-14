package it.polimi.ingsw.events.data.client;

import it.polimi.ingsw.events.data.BaseEvent;
import it.polimi.ingsw.model.Card;
/**
 * Event that represent a player placing a card on the table
 */
public class PlaceCardEvent extends BaseEvent {
    private final Card card;
    public PlaceCardEvent(Card card, boolean isLocal) {
        this.ID = "PLACE_CARD_EVENT";
        this.card = card;
        requiresConnection = true;
        local = isLocal;
    }

    public Card getCard() {
        return card;
    }
}
