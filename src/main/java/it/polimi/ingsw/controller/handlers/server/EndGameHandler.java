package it.polimi.ingsw.controller.handlers.server;

import it.polimi.ingsw.controller.OnlineGameController;
import it.polimi.ingsw.controller.handlers.EventHandler;
import it.polimi.ingsw.events.data.server.EndGameResult;
import it.polimi.ingsw.events.data.server.EndGameResultsEvent;
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
        OnlineGameController onlineGameController = OnlineGameController.getInstance();
        GameResult[] gameResults = onlineGameController.endGame();
        EndGameResult result = EndGameResult.DRAW;
        if(gameResults[0].getTotalPoints() > gameResults[1].getTotalPoints())
            result = EndGameResult.TEAM1;
        else if (gameResults[0].getTotalPoints() < gameResults[1].getTotalPoints())
            result = EndGameResult.TEAM2;
        try {
            Server.getInstance().getEventTransmitter().broadcast(
                    new EndGameResultsEvent(
                            gameResults[0],
                            gameResults[1],
                            result,
                            onlineGameController.getTeam1Points(),
                            onlineGameController.getTeam2Points()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
