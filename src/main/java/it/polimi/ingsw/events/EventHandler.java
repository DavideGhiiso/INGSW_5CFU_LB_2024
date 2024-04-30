package it.polimi.ingsw.events;

import it.polimi.ingsw.controller.OnlineGameController;
import it.polimi.ingsw.events.data.Event;
import it.polimi.ingsw.events.data.server.ScoreEvent;

import java.io.IOException;

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

    static void broadcastScore(OnlineGameController onlineGameController, EventTransmitter eventTransmitter) {
        // current play score
        String[] fistTeamNames = onlineGameController.getFirstTeamNames();
        String[] secondTeamNames = onlineGameController.getSecondTeamNames();
        int firstTeamPoints = onlineGameController.getTeam1Points();
        int secondTeamPoints = onlineGameController.getTeam2Points();
        try {
            eventTransmitter.broadcast(new ScoreEvent(fistTeamNames, secondTeamNames, firstTeamPoints, secondTeamPoints));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
