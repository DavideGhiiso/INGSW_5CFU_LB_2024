package it.polimi.ingsw.controller.handlers;

import it.polimi.ingsw.controller.OnlineGameController;
import it.polimi.ingsw.events.EventHandler;
import it.polimi.ingsw.events.EventTransmitter;
import it.polimi.ingsw.events.data.Event;
import it.polimi.ingsw.events.data.HandChangedEvent;
import it.polimi.ingsw.events.data.client.StartGameEvent;
import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.model.exceptions.MaxPlayersReachedException;
import it.polimi.ingsw.networking.Server;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @see it.polimi.ingsw.events.data.client.StartGameEvent
 */
public class StartGameServerHandler implements EventHandler {
    @Override
    public void handle(Event event) {
        OnlineGameController onlineGameController = OnlineGameController.getInstance();
        if(!onlineGameController.canStartGame()) // game is not full
            while(true) {
                try {
                    OnlineGameController.getInstance().addBot();
                } catch (MaxPlayersReachedException e) {
                    break;
                }
            }
        onlineGameController.startGame();
        try {
            Server.getInstance().getEventTransmitter().broadcast(new StartGameEvent());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
