package it.polimi.ingsw.controller.handlers;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.controller.OnlineGameController;
import it.polimi.ingsw.events.EventHandler;
import it.polimi.ingsw.events.EventTransmitter;
import it.polimi.ingsw.events.data.*;
import it.polimi.ingsw.events.data.client.PlaceCardEvent;
import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.model.EndGameException;
import it.polimi.ingsw.model.Table;
import it.polimi.ingsw.networking.Connection;
import it.polimi.ingsw.networking.Host;
import it.polimi.ingsw.networking.Server;

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
        eventTransmitter.broadcast(new TableChangedEvent(cardOnTable)); // sends new table to everyone
        eventTransmitter.sendTo(
                event.getConnection().getConnectionID(),
                new HandChangedEvent(onlineGameController.getCurrentPlayerHand())); // sends new hand to current player
        try {
            onlineGameController.nextTurn();
        } catch (EndGameException e) {
            new EndGameHandler().handle(new EndGameEvent(false));
        }
    }
}
