package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.viewcontroller.InGameController;
import it.polimi.ingsw.events.data.Event;
import it.polimi.ingsw.events.data.server.HandChangedEvent;
import it.polimi.ingsw.events.data.server.TableChangedEvent;
import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.model.Table;

import java.util.ArrayList;
import java.util.List;

public class PlayerView {
    private boolean yourTurn;
    private List<Card> hand;
    private List<Card> tableCards;
    private InGameController observer;

    public PlayerView() {
        this.yourTurn = false;
        this.hand = new ArrayList<>();
        this.tableCards = new ArrayList<>();
        this.observer = null;
    }

    public boolean isYourTurn() {
        return yourTurn;
    }

    public void setYourTurn(boolean yourTurn) {
        this.yourTurn = yourTurn;
    }

    public List<Card> getHand() {
        return hand;
    }

    public void setHand(List<Card> hand) {
        this.hand = hand;
        notify(new HandChangedEvent(this.hand));
    }

    public List<Card> getTableCards() {
        return tableCards;
    }

    public void setTableCards(List<Card> tableCards) {
        this.tableCards = tableCards;
        notify(new TableChangedEvent(this.tableCards));
    }

    public void setObserver(InGameController observer) {
        this.observer = observer;
    }

    private void notify(Event event) {
        if(observer == null)
            return;
        observer.handle(event);
    }
}
