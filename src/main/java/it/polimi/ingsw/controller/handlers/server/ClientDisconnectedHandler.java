package it.polimi.ingsw.controller.handlers.server;

import it.polimi.ingsw.controller.OnlineGameController;
import it.polimi.ingsw.controller.handlers.EventHandler;
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
            Server server = Server.getInstance();
            server.removeClient(connection);
            OnlineGameController onlineGameController = OnlineGameController.getInstance();
            onlineGameController.handleClientExit(connection.getConnectionID());

            if(onlineGameController.isGameStarted()) {
                if(server.isEmpty()) {
                    onlineGameController.reset();
                    return;
                }
                EventHandler.broadcastScore(onlineGameController, server.getEventTransmitter());
                String currentPlayerName = onlineGameController.getCurrentPlayer().getName();
                if(currentPlayerName.equals(connection.getConnectionID() + " (BOT)")) // if disconnecting player is playing his turn
                    new BotTurnHandler().handle(new BotTurnEvent());
            }
            connection.close();
        } catch (IOException ignored) {
        }
    }


}
