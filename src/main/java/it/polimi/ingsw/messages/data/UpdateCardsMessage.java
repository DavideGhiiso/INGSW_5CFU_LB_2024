package it.polimi.ingsw.messages.data;

import it.polimi.ingsw.model.Card;

import java.util.List;

public class UpdateCardsMessage extends Message {
    private final List<Card> updatedCards;
    public UpdateCardsMessage(List<Card> cards) {
        ID = "UPDATE_CARDS";
        this.updatedCards = cards;
    }
    public List<Card> getUpdatedCards() {
        return this.updatedCards;
    }
}

