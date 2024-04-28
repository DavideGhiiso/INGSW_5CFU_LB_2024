package it.polimi.ingsw.controller.handlers;

import it.polimi.ingsw.controller.OnlineGameController;
import it.polimi.ingsw.events.EventHandler;
import it.polimi.ingsw.events.data.Event;
import it.polimi.ingsw.events.data.GameInfo;
import it.polimi.ingsw.events.data.server.HandChangedEvent;
import it.polimi.ingsw.events.data.server.UpdatePlayerCountEvent;
import it.polimi.ingsw.events.data.client.RequestGameInfoEvent;
import it.polimi.ingsw.events.data.server.ScoreEvent;
import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.model.exceptions.NonexistentPlayerException;
import it.polimi.ingsw.networking.Connection;

import java.io.IOException;
import java.util.List;

/**
 * Handler that sends different events according to queried information
 * @see it.polimi.ingsw.events.data.client.RequestGameInfoEvent
 */
public class RequestGameInfoHandler implements EventHandler {
    @Override
    public void handle(Event event) {
        Connection connection = event.getConnection();
        RequestGameInfoEvent gameInfoEvent = (RequestGameInfoEvent) event.getEvent();
        GameInfo gameInfo = gameInfoEvent.getRequestedInfo();
        OnlineGameController onlineGameController = OnlineGameController.getInstance();
        switch (gameInfo) {
            case CONNECTED_PLAYERS -> { // number of connected players
                int connectedPlayers = onlineGameController.getPlayersCount();
                if (gameInfoEvent.getOldValue().equals(connectedPlayers))
                    break;
                try {
                    connection.send(new UpdatePlayerCountEvent(connectedPlayers));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            case CURRENT_HAND -> { // current connection Cards in hand
                List<Card> currentHand;
                try {
                    currentHand = onlineGameController.getPlayerCards(connection.getConnectionID());
                } catch (NonexistentPlayerException e) {
                    break;
                }
                try {
                    connection.send(new HandChangedEvent(currentHand));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            case SCORE -> { // current play score
                String[] fistTeamNames = onlineGameController.getFirstTeamNames();
                String[] secondTeamNames = onlineGameController.getSecondTeamNames();
                int firstTeamPoints = onlineGameController.getTeam1Points();
                int secondTeamPoints = onlineGameController.getTeam2Points();
                try {
                    connection.send(new ScoreEvent(fistTeamNames, secondTeamNames, firstTeamPoints, secondTeamPoints));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
