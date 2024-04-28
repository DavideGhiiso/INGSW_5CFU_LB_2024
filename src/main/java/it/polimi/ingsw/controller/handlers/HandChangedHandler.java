package it.polimi.ingsw.controller.handlers;

import it.polimi.ingsw.events.EventHandler;
import it.polimi.ingsw.events.data.Event;
import it.polimi.ingsw.events.data.server.HandChangedEvent;
import it.polimi.ingsw.view.SceneLoader;

/**
 * @see HandChangedEvent
 */
public class HandChangedHandler implements EventHandler {
    @Override
    public void handle(Event event) {
        HandChangedEvent handChangedEvent = (HandChangedEvent) event.getEvent();
        SceneLoader.getPlayerView().setHand(handChangedEvent.getHand());
    }
}
