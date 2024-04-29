package it.polimi.ingsw.controller.handlers;

import it.polimi.ingsw.events.EventHandler;
import it.polimi.ingsw.events.data.Event;
import it.polimi.ingsw.events.data.server.HandChangedEvent;
import it.polimi.ingsw.events.data.server.TableChangedEvent;
import it.polimi.ingsw.view.SceneLoader;

public class TableChangedHandler implements EventHandler {
    @Override
    public void handle(Event event) {
        TableChangedEvent handChangedEvent = (TableChangedEvent) event.getEvent();
//        SceneLoader.getPlayerView().setTableCards(handChangedEvent.getCards());
        SceneLoader.getCurrentController().handle(event);
    }
}
