package it.polimi.ingsw.controller.handlers.server;

import it.polimi.ingsw.controller.OnlineGameController;
import it.polimi.ingsw.events.EventHandler;
import it.polimi.ingsw.events.EventTransmitter;
import it.polimi.ingsw.events.data.Event;
import it.polimi.ingsw.events.data.client.StartGameEvent;
import it.polimi.ingsw.events.data.server.NewTurnEvent;
import it.polimi.ingsw.model.exceptions.MaxPlayersReachedException;
import it.polimi.ingsw.networking.Server;

import java.io.IOException;

/**
 * @see it.polimi.ingsw.events.data.client.StartGameEvent
 */
public class StartGameServerHandler implements EventHandler {
    @Override
    public void handle(Event event) {
        OnlineGameController onlineGameController = OnlineGameController.getInstance();
        if(!onlineGameController.canStartGame()) // game is not full, fill with bots
            while(true) {
                try {
                    OnlineGameController.getInstance().addBot();
                } catch (MaxPlayersReachedException e) {
                    break;
                }
            }
        onlineGameController.startGame();
        EventTransmitter eventTransmitter = Server.getInstance().getEventTransmitter();
        try { // notify players
            String[] fistTeamNames = onlineGameController.getFirstTeamNames();
            String[] secondTeamNames = onlineGameController.getSecondTeamNames();
            eventTransmitter.broadcast(new StartGameEvent(fistTeamNames, secondTeamNames));
            eventTransmitter.broadcast(new NewTurnEvent(onlineGameController.getCurrentPlayer().getName()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
