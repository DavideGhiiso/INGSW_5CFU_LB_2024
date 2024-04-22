package it.polimi.ingsw.controller.view;

import it.polimi.ingsw.events.data.Event;

public interface ViewController {
    void handle(Event event);
}
