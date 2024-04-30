package it.polimi.ingsw.controller.handlers;

import it.polimi.ingsw.controller.OnlineGameController;
import it.polimi.ingsw.events.EventHandler;
import it.polimi.ingsw.events.EventTransmitter;
import it.polimi.ingsw.events.data.Event;
import it.polimi.ingsw.events.data.server.ScoreEvent;
import it.polimi.ingsw.networking.Connection;
import it.polimi.ingsw.networking.Server;

import java.io.IOException;

/**
 * Handles a ClientDisconnectedEvent by closing passed connection and by
 * @see it.polimi.ingsw.events.data.client.ClientDisconnectedEvent
 */
public class ClientDisconnectedHandler implements EventHandler {
    @Override
    public void handle(Event event) {
        try {
            Connection connection = event.getConnection();
            Server.getInstance().removeClient(connection);
            OnlineGameController onlineGameController = OnlineGameController.getInstance();
            onlineGameController.handleClientExit(connection.getConnectionID());
            if(onlineGameController.isGameStarted()) {
                EventHandler.broadcastScore(onlineGameController, Server.getInstance().getEventTransmitter());
            }
            connection.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
