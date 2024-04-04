package it.polimi.ingsw.messages.data;

import it.polimi.ingsw.model.Card;

public class PlaceCardMessage extends Message {
    private final Card placedCard;
    public PlaceCardMessage(Card card) {
        ID = "PLACE_CARD";
        this.placedCard = card;
    }

    public Card getPlacedCard() {
        return this.placedCard;
    }
}
