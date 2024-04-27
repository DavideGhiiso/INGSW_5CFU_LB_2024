package it.polimi.ingsw.controller.viewcontroller;

import it.polimi.ingsw.events.data.Event;

public interface ViewController {
    void handle(Event event);
}
