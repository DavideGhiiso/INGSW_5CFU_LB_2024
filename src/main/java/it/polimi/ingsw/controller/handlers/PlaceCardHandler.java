package it.polimi.ingsw.controller.handlers;

import it.polimi.ingsw.controller.OnlineGameController;
import it.polimi.ingsw.events.EventHandler;
import it.polimi.ingsw.events.EventTransmitter;
import it.polimi.ingsw.events.data.*;
import it.polimi.ingsw.events.data.client.PlaceCardEvent;
import it.polimi.ingsw.events.data.server.HandChangedEvent;
import it.polimi.ingsw.events.data.server.TableChangedEvent;
import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.model.EndGameException;
import it.polimi.ingsw.networking.Connection;
import it.polimi.ingsw.networking.Server;

import java.io.IOException;
import java.util.List;

/**
 * @see it.polimi.ingsw.events.data.client.PlaceCardEvent
 */
public class PlaceCardHandler implements EventHandler {
    @Override
    public void handle(Event event) {

        if(event.isLocal())
            handleOfflineEvent((PlaceCardEvent) event.getEvent());
        else
            handleOnlineEvent((PlaceCardEvent) event.getEvent());

    }

    private void handleOfflineEvent(PlaceCardEvent event) {

    }

    /**
     * Places card on table and notifies all clients, sends the new hand to current player and advances turn.
     * If the game is over, starts the end game routine
     */
    private void handleOnlineEvent(PlaceCardEvent event) {
        OnlineGameController onlineGameController = OnlineGameController.getInstance();
        EventTransmitter eventTransmitter = Server.getInstance().getEventTransmitter();
        Connection clientConnection = event.getConnection();

        List<Card> cardOnTable = onlineGameController.placeCard(event.getCard());
        try {
            eventTransmitter.broadcast(new TableChangedEvent(cardOnTable)); // sends new table to everyone
            event.getConnection().send(new HandChangedEvent(onlineGameController.getCurrentPlayer().getHand())); // sends new hand to current player
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            onlineGameController.nextTurn();
        } catch (EndGameException e) {
            new EndGameHandler().handle(new EndGameEvent(false));
        }
    }
}
