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
    private String username;

    public PlayerView() {
        this.yourTurn = false;
        username = "";
    }

    public boolean isYourTurn() {
        return yourTurn;
    }

    public void setYourTurn(boolean yourTurn) {
        this.yourTurn = yourTurn;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
