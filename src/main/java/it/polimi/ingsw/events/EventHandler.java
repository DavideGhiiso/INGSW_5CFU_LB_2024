package it.polimi.ingsw.events;

import it.polimi.ingsw.events.data.Event;

/**
 * Interface that represent an EventHandler, that is a class that can cause updates in the system following the occurring
 * of an Event.
 */
public interface EventHandler {
    /**
     * Method that contains the code to handle passed Event
     * @param event event to handle
     */
    void handle(Event event);
}
