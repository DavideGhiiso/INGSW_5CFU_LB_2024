package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.viewcontroller.InGameController;
import it.polimi.ingsw.controller.viewcontroller.ViewController;
import it.polimi.ingsw.events.data.HandChangedEvent;
import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.model.Table;

import java.util.ArrayList;
import java.util.List;

public class PlayerView {
    private List<Card> hand;
    private List<Table> tableCards;
    private InGameController observer;

    public PlayerView() {
        this.hand = new ArrayList<>();
        this.tableCards = new ArrayList<>();
        this.observer = null;
    }

    public List<Card> getHand() {
        return hand;
    }

    public void setHand(List<Card> hand) {
        this.hand = hand;
        if(observer == null)
            return;
        observer.handle(new HandChangedEvent(this.hand));
    }

    public List<Table> getTableCards() {
        return tableCards;
    }

    public void setTableCards(List<Table> tableCards) {
        this.tableCards = tableCards;
    }

    public void setObserver(InGameController observer) {
        this.observer = observer;
    }
}
