package it.polimi.ingsw.controller.handlers;

import it.polimi.ingsw.controller.OnlineGameController;
import it.polimi.ingsw.events.EventHandler;
import it.polimi.ingsw.events.EventTransmitter;
import it.polimi.ingsw.events.data.Event;
import it.polimi.ingsw.events.data.client.StartGameEvent;
import it.polimi.ingsw.events.data.server.YourTurnEvent;
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
        try { // send new game and notify current player
            eventTransmitter.broadcast(new StartGameEvent());
            eventTransmitter.sendTo(onlineGameController.getCurrentPlayer().getName(), new YourTurnEvent());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
