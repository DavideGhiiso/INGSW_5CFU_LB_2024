package it.polimi.ingsw.view.viewcontroller;

import it.polimi.ingsw.events.data.Event;

public interface ViewController {
    void handle(Event event);
}
