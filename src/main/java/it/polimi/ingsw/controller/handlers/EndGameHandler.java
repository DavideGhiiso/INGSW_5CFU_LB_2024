package it.polimi.ingsw.controller.handlers;

import it.polimi.ingsw.controller.OnlineGameController;
import it.polimi.ingsw.events.EventHandler;
import it.polimi.ingsw.events.data.EndGameResultsEvent;
import it.polimi.ingsw.events.data.Event;
import it.polimi.ingsw.model.GameResult;
import it.polimi.ingsw.networking.Server;

import java.io.IOException;

/**
 * @see it.polimi.ingsw.events.data.EndGameEvent
 * @see  EndGameResultsEvent
 */
public class EndGameHandler implements EventHandler {
    @Override
    public void handle(Event event) {
        GameResult[] gameResults = OnlineGameController.getInstance().endGame();
        try {
            Server.getInstance().getEventTransmitter().broadcast(new EndGameResultsEvent(gameResults[0], gameResults[1]));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
