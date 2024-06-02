package it.polimi.ingsw.controller.handlers.server;

import it.polimi.ingsw.controller.OnlineGameController;
import it.polimi.ingsw.controller.handlers.EventHandler;
import it.polimi.ingsw.events.data.Event;
import it.polimi.ingsw.events.data.GameInfo;
import it.polimi.ingsw.events.data.server.*;
import it.polimi.ingsw.events.data.client.RequestGameInfoEvent;
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
            case CONNECTED_PLAYERS -> sendConnectedPlayers(onlineGameController, gameInfoEvent, connection);
            case CURRENT_HAND -> sendCurrentPlayerHand(onlineGameController, connection);
            case CURRENT_TABLE -> sendCurrentTable(connection, onlineGameController);
            case SCORE -> sendCurrentScore(onlineGameController, connection);
            case USERNAME -> sendCurrentUsername(connection);
        }
    }

    private static void sendCurrentTable(Connection connection, OnlineGameController onlineGameController) {
        try {
            connection.send(new TableChangedEvent(onlineGameController.getTableCards(), null));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void sendCurrentUsername(Connection connection) {
        // current username
        try {
            connection.send(new UsernameChangedEvent(connection.getConnectionID()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void sendCurrentScore(OnlineGameController onlineGameController, Connection connection) {
        // current play score
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

    private static void sendCurrentPlayerHand(OnlineGameController onlineGameController, Connection connection) {
        // current connection Cards in hand
        List<Card> currentHand;
        try {
            currentHand = onlineGameController.getPlayerCards(connection.getConnectionID());
        } catch (NonexistentPlayerException e) {
            return;
        }
        try {
            connection.send(new HandChangedEvent(currentHand));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void sendConnectedPlayers(OnlineGameController onlineGameController, RequestGameInfoEvent gameInfoEvent, Connection connection) {
        // number of connected players
        int connectedPlayers = onlineGameController.getPlayersCount();
        if (gameInfoEvent.getOldValue().equals(connectedPlayers))
            return;
        try {
            connection.send(new UpdatePlayerCountEvent(connectedPlayers));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
