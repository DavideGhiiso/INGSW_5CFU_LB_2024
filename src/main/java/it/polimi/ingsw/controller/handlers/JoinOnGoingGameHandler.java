package it.polimi.ingsw.controller.handlers;

import it.polimi.ingsw.controller.OnlineGameController;
import it.polimi.ingsw.events.EventHandler;
import it.polimi.ingsw.events.EventTransmitter;
import it.polimi.ingsw.events.data.ConnectionEvent;
import it.polimi.ingsw.events.data.Event;
import it.polimi.ingsw.events.data.server.JoinGameResponseEvent;
import it.polimi.ingsw.events.data.client.JoinOnGoingGameEvent;
import it.polimi.ingsw.events.data.server.ScoreEvent;
import it.polimi.ingsw.events.data.server.TableChangedEvent;
import it.polimi.ingsw.model.exceptions.MaxPlayersReachedException;
import it.polimi.ingsw.networking.Connection;
import it.polimi.ingsw.events.Response;
import it.polimi.ingsw.networking.Server;

import java.io.IOException;

/**
 * Handler that tries to connect a player knowing that a bot was playing. It is still possible that another player
 * replaced the bot in the meantime
 * @see it.polimi.ingsw.events.data.client.JoinOnGoingGameEvent
 */
public class JoinOnGoingGameHandler implements EventHandler {
    @Override
    public void handle(Event event) {
        Response response = Response.OK_ONGOING;
        if(!(((ConnectionEvent) event).getEvent() instanceof JoinOnGoingGameEvent joinOnGoingGameEvent))
            throw new ClassCastException();

        Connection connection = event.getConnection();
        OnlineGameController onlineGameController = OnlineGameController.getInstance();
        String username = joinOnGoingGameEvent.getUsername();
        try {
            OnlineGameController.getInstance().replaceBotWithClient(username); // replace a bot
        } catch (MaxPlayersReachedException ex) {
            response = Response.GAME_FULL; // another client joined in the meantime
        }

        try {
            connection.send(new JoinGameResponseEvent(response));
            connection.send(new TableChangedEvent(onlineGameController.getTableCards(), null));
            EventHandler.broadcastScore(onlineGameController, Server.getInstance().getEventTransmitter());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
