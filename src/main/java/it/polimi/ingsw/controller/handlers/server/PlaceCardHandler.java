package it.polimi.ingsw.controller.handlers.server;

import it.polimi.ingsw.controller.OnlineGameController;
import it.polimi.ingsw.events.EventHandler;
import it.polimi.ingsw.events.EventTransmitter;
import it.polimi.ingsw.events.data.*;
import it.polimi.ingsw.events.data.client.PlaceCardEvent;
import it.polimi.ingsw.events.data.server.BotTurnEvent;
import it.polimi.ingsw.events.data.server.HandChangedEvent;
import it.polimi.ingsw.events.data.server.TableChangedEvent;
import it.polimi.ingsw.events.data.server.NewTurnEvent;
import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.model.exceptions.EndGameException;
import it.polimi.ingsw.networking.Connection;
import it.polimi.ingsw.networking.Server;

import java.io.IOException;
import java.util.List;

/**
 * Places card on table and notifies all clients, sends the new hand to current player and advances turn.
 * If the game is over, starts the end game routine
 * @see PlaceCardEvent
 */
public class PlaceCardHandler implements EventHandler {
    @Override
    public void handle(Event event) {
        PlaceCardEvent placeCardEvent = (PlaceCardEvent) event.getEvent();
        OnlineGameController onlineGameController = OnlineGameController.getInstance();
        EventTransmitter eventTransmitter = Server.getInstance().getEventTransmitter();
        List<Card> cardOnTable = onlineGameController.placeCard(placeCardEvent.getCard());
        List<Card> currentPlayerHand = onlineGameController.getCurrentPlayer().getHand();
        try {
            // sends new table to everyone
            eventTransmitter.broadcast(new TableChangedEvent(cardOnTable, placeCardEvent.getCard()));
            // sends new hand to current player
            event.getConnection().send(new HandChangedEvent(currentPlayerHand));
            onlineGameController.nextTurn();
            if(onlineGameController.getCurrentPlayer().isBot()) {
                new BotTurnHandler().handle(new BotTurnEvent());
            } else // tell next player it's their turn
                eventTransmitter.broadcast(new NewTurnEvent(onlineGameController.getCurrentPlayer().getName()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (EndGameException e) {
            new EndGameHandler().handle(new EndGameEvent());
        }
    }
}
