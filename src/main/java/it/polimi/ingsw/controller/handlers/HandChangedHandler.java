package it.polimi.ingsw.controller.handlers;

import it.polimi.ingsw.events.EventHandler;
import it.polimi.ingsw.events.data.Event;
import it.polimi.ingsw.events.data.HandChangedEvent;
import it.polimi.ingsw.view.SceneLoader;

/**
 * @see it.polimi.ingsw.events.data.HandChangedEvent
 */
public class HandChangedHandler implements EventHandler {
    @Override
    public void handle(Event event) {
        HandChangedEvent handChangedEvent = (HandChangedEvent) event.getEvent();
        SceneLoader.getPlayerView().setHand(handChangedEvent.getHand());
    }
}
