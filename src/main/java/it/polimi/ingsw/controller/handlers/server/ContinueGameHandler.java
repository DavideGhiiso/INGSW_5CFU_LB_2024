package it.polimi.ingsw.controller.handlers.server;

import it.polimi.ingsw.controller.OnlineGameController;
import it.polimi.ingsw.controller.handlers.EventHandler;
import it.polimi.ingsw.events.EventTransmitter;
import it.polimi.ingsw.events.data.Event;
import it.polimi.ingsw.events.data.client.ContinueGameEvent;
import it.polimi.ingsw.events.data.client.StartGameEvent;
import it.polimi.ingsw.events.data.server.BotTurnEvent;
import it.polimi.ingsw.events.data.server.GameResumingWarningEvent;
import it.polimi.ingsw.events.data.server.NewTurnEvent;
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
            Thread.sleep(WAIT_TIME);
        } catch (InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }
        OnlineGameController onlineGameController = OnlineGameController.getInstance();
        EventTransmitter eventTransmitter = Server.getInstance().getEventTransmitter();
        onlineGameController.continueGame();
        try { // notify players
            String[] fistTeamNames = onlineGameController.getFirstTeamNames();
            String[] secondTeamNames = onlineGameController.getSecondTeamNames();
            int team1Points = onlineGameController.getTeam1Points();
            int team2Points = onlineGameController.getTeam2Points();
            eventTransmitter.broadcast(new StartGameEvent(fistTeamNames, secondTeamNames, team1Points, team2Points));
            eventTransmitter.broadcast(new NewTurnEvent(onlineGameController.getCurrentPlayer().getName()));
            if(onlineGameController.getCurrentPlayer().isBot())
                new Thread(() -> new BotTurnHandler().handle(new BotTurnEvent())).start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
