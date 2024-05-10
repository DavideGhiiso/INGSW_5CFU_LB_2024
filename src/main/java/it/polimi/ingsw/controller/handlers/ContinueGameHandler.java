package it.polimi.ingsw.controller.handlers;

import it.polimi.ingsw.controller.OnlineGameController;
import it.polimi.ingsw.events.EventHandler;
import it.polimi.ingsw.events.data.Event;
import it.polimi.ingsw.events.data.client.ContinueGameEvent;
import it.polimi.ingsw.events.data.server.GameResumeEvent;
import it.polimi.ingsw.events.data.server.GameResumingWarningEvent;
import it.polimi.ingsw.networking.Server;

import java.io.IOException;

/**
 * @see ContinueGameEvent
 */
public class ContinueGameHandler implements EventHandler {
    private final static int WAIT_TIME = 3000;
    @Override
    public void handle(Event event) {
        try {
            Server.getInstance().getEventTransmitter().broadcast(new GameResumingWarningEvent());
            Thread.sleep(3000);
        } catch (InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }
        OnlineGameController.getInstance().continueGame();
        try {
            Server.getInstance().getEventTransmitter().broadcast(new GameResumeEvent());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
