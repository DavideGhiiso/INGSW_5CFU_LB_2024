package it.polimi.ingsw.controller.handlers.server;

import it.polimi.ingsw.controller.OnlineGameController;
import it.polimi.ingsw.controller.handlers.EventHandler;
import it.polimi.ingsw.events.EventTransmitter;
import it.polimi.ingsw.events.data.EndGameEvent;
import it.polimi.ingsw.events.data.Event;
import it.polimi.ingsw.events.data.server.TableChangedEvent;
import it.polimi.ingsw.events.data.server.NewTurnEvent;
import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.model.exceptions.EndGameException;
import it.polimi.ingsw.networking.Server;

import java.io.IOException;
import java.util.List;

/**
 * Internal event handler that handles all the possible consecutive bot turns
 */
public class BotTurnHandler implements EventHandler {
    @Override
    public void handle(Event event) {
        OnlineGameController onlineGameController = OnlineGameController.getInstance();
        EventTransmitter eventTransmitter = Server.getInstance().getEventTransmitter();
        try {
            eventTransmitter.broadcast(new NewTurnEvent(onlineGameController.getCurrentPlayer().getName()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        while(onlineGameController.getCurrentPlayer().isBot() && onlineGameController.isGameStarted()) {
            List<Card> currentPlayerHand = onlineGameController.getCurrentPlayer().getHand();
            Card botCard = onlineGameController.playCardBot(currentPlayerHand);
            List<Card> cardsOnTable = onlineGameController.placeCard(botCard);
            try {
                // sends new table to everyone
                eventTransmitter.broadcast(new TableChangedEvent(cardsOnTable, botCard));
                onlineGameController.nextTurn();
                eventTransmitter.broadcast(new NewTurnEvent(onlineGameController.getCurrentPlayer().getName()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (EndGameException e) {
                new EndGameHandler().handle(new EndGameEvent());
                break;
            }
        }
    }
}
