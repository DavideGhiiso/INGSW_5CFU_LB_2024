package it.polimi.ingsw.controller.handlers;

import it.polimi.ingsw.controller.OnlineGameController;
import it.polimi.ingsw.events.EventHandler;
import it.polimi.ingsw.events.data.Event;
import it.polimi.ingsw.events.data.server.BotTurnEvent;
import it.polimi.ingsw.events.data.server.ClientDisconnectedEvent;
import it.polimi.ingsw.networking.Connection;
import it.polimi.ingsw.networking.Server;

import java.io.IOException;

/**
 * Handles a ClientDisconnectedEvent by closing passed connection and by
 * @see ClientDisconnectedEvent
 */
public class ClientDisconnectedHandler implements EventHandler {
    @Override
    public void handle(Event event) {
        try {
            Connection connection = event.getConnection();
            Server.getInstance().removeClient(connection);
            OnlineGameController onlineGameController = OnlineGameController.getInstance();
            String currentPlayerName = onlineGameController.getCurrentPlayer().getName();
            onlineGameController.handleClientExit(connection.getConnectionID());
            if(onlineGameController.isGameStarted()) {
                EventHandler.broadcastScore(onlineGameController, Server.getInstance().getEventTransmitter());
                if(connection.getConnectionID().equals(currentPlayerName)) // if disconnecting player is playing his turn
                    new BotTurnHandler().handle(new BotTurnEvent());
            }
            connection.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
