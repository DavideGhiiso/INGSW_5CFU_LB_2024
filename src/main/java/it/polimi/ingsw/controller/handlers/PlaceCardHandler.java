package it.polimi.ingsw.controller.handlers;

import it.polimi.ingsw.controller.GameController;
import it.polimi.ingsw.events.EventHandler;
import it.polimi.ingsw.events.data.ConnectionEvent;
import it.polimi.ingsw.events.data.Event;
import it.polimi.ingsw.events.data.client.PlaceCardEvent;
import it.polimi.ingsw.networking.Host;

/**
 * @see it.polimi.ingsw.events.data.client.PlaceCardEvent
 */
public class PlaceCardHandler implements EventHandler {
    @Override
    public void handle(Event event) {
        PlaceCardEvent placeCardEvent = (PlaceCardEvent) ((ConnectionEvent) event).getUndecoratedEvent();
        GameController gameController = Host.getGameControllerInstance();
        gameController.placeCard(placeCardEvent.getCard());
    }
}
