package it.polimi.ingsw.controller.handlers.server;

import it.polimi.ingsw.controller.OnlineGameController;
import it.polimi.ingsw.events.EventHandler;
import it.polimi.ingsw.events.data.ConnectionEvent;
import it.polimi.ingsw.events.data.Event;
import it.polimi.ingsw.events.data.server.JoinGameResponseEvent;
import it.polimi.ingsw.events.data.server.ClientDisconnectedEvent;
import it.polimi.ingsw.events.data.client.JoinGameEvent;
import it.polimi.ingsw.events.data.client.StartGameEvent;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.exceptions.MaxPlayersReachedException;
import it.polimi.ingsw.model.exceptions.UsernameTakenException;
import it.polimi.ingsw.networking.Connection;
import it.polimi.ingsw.events.Response;

import java.io.IOException;

/**
 * Handles a JoinGameEvent by
 * <ul>
 *     <li>assigning given username to corresponding connection</li>
 *     <li>trying to add a new Player inside Game object</li>
 *     <li>return to the client the response to his join request</li>
 * </ul>
 * @see it.polimi.ingsw.events.data.client.JoinGameEvent
 * @see JoinGameResponseEvent
 */
public class JoinGameHandler implements EventHandler {
    @Override
    public void handle(Event event) {
        Response response = Response.OK;
        JoinGameEvent joinGameEvent = (JoinGameEvent) event.getEvent();
        Connection connection = event.getConnection();
        OnlineGameController onlineGameController = OnlineGameController.getInstance();

        if(onlineGameController == null)
            onlineGameController = OnlineGameController.getInstance(new Game());

        if(onlineGameController.isPlayerPresent(joinGameEvent.getUsername())) {
            response = Response.USERNAME_TAKEN;
        } else {
            connection.setConnectionID(joinGameEvent.getUsername());
            try {
                onlineGameController.addPlayer(joinGameEvent.getUsername());
            } catch (MaxPlayersReachedException e) {
                if (onlineGameController.botIsPlaying())
                    response = Response.CAN_REPLACE_BOT; // a bot is playing
                else
                    response = Response.GAME_FULL; // game is full, client cannot join
            } catch (UsernameTakenException e) {
                response = Response.USERNAME_TAKEN;
            }
        }

        try {
            event.getConnection().send(new JoinGameResponseEvent(response));
            if(OnlineGameController.getInstance().canStartGame() && !onlineGameController.isGameStarted())
                new StartGameServerHandler().handle(new StartGameEvent());
        } catch (IOException e) {
            new ClientDisconnectedHandler().handle(new ConnectionEvent(new ClientDisconnectedEvent(), event.getConnection()));
        }
    }
}
