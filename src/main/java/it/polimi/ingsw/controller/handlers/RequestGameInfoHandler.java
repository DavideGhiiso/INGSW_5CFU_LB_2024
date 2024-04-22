package it.polimi.ingsw.controller.handlers;

import it.polimi.ingsw.controller.OnlineGameController;
import it.polimi.ingsw.events.EventHandler;
import it.polimi.ingsw.events.data.Event;
import it.polimi.ingsw.events.data.GameInfo;
import it.polimi.ingsw.events.data.UpdatePlayerCountEvent;
import it.polimi.ingsw.events.data.client.RequestGameInfoEvent;
import it.polimi.ingsw.networking.Connection;

import java.io.IOException;

/**
 * @see it.polimi.ingsw.events.data.client.RequestGameInfoEvent
 */
public class RequestGameInfoHandler implements EventHandler {
    @Override
    public void handle(Event event) {
        Connection connection = event.getConnection();
        GameInfo gameInfo = ((RequestGameInfoEvent) event.getEvent()).getRequestedInfo();
        switch (gameInfo) {
            case CONNECTED_PLAYERS -> {
                int connectedPlayers = OnlineGameController.getInstance().getPlayersCount();
                try {
                    connection.send(new UpdatePlayerCountEvent(connectedPlayers));
                    break;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
