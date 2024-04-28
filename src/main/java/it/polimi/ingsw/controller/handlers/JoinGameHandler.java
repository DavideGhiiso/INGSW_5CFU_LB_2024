package it.polimi.ingsw.controller.handlers;

import it.polimi.ingsw.controller.OnlineGameController;
import it.polimi.ingsw.events.EventHandler;
import it.polimi.ingsw.events.data.ConnectionEvent;
import it.polimi.ingsw.events.data.Event;
import it.polimi.ingsw.events.data.server.JoinGameResponseEvent;
import it.polimi.ingsw.events.data.client.ClientDisconnectedEvent;
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

        if(OnlineGameController.getInstance() == null)
            OnlineGameController.getInstance(new Game());
        connection.setConnectionID(joinGameEvent.getUsername());
        try {
            OnlineGameController.getInstance().addPlayer(joinGameEvent.getUsername());
        } catch (MaxPlayersReachedException e) {
            if(OnlineGameController.getInstance().botIsPlaying())
                response = Response.CAN_REPLACE_BOT; // a bot is playing
            else
                response = Response.GAME_FULL; // game is full, client cannot join
        } catch (UsernameTakenException e) {
            response = Response.USERNAME_TAKEN;
            connection.setConnectionID(handleUsernameChange(joinGameEvent));
        }

        try {
            event.getConnection().send(new JoinGameResponseEvent(response));
            if(OnlineGameController.getInstance().canStartGame())
                new StartGameServerHandler().handle(new StartGameEvent());
        } catch (IOException e) {
            new ClientDisconnectedHandler().handle(new ConnectionEvent(new ClientDisconnectedEvent(), event.getConnection()));
        }
    }

    private String handleUsernameChange(JoinGameEvent joinGameEvent) {
        int index = 1;
        String newUsername = joinGameEvent.getUsername() + "_" + index;
        while(true) {
            try {
                OnlineGameController.getInstance().addPlayer(newUsername);
                break;
            } catch (UsernameTakenException ex) {
                newUsername = joinGameEvent.getUsername() + "_" + ++index;
            } catch (MaxPlayersReachedException ex) {
                break;
            }
        }
        return newUsername;
    }
}
