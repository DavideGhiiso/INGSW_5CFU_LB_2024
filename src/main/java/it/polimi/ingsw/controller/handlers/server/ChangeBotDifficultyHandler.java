package it.polimi.ingsw.controller.handlers.server;

import it.polimi.ingsw.controller.OnlineGameController;
import it.polimi.ingsw.events.EventHandler;
import it.polimi.ingsw.events.data.Event;
import it.polimi.ingsw.events.data.client.ChangeBotDifficultyEvent;
import it.polimi.ingsw.model.bot.Difficulties;
import it.polimi.ingsw.networking.Server;
import it.polimi.ingsw.view.SceneLoader;

import java.io.IOException;

/**
 * @see ChangeBotDifficultyEvent
 */
public class ChangeBotDifficultyHandler implements EventHandler {
    private final boolean clientSide;
    public ChangeBotDifficultyHandler(boolean clientSide) {
        this.clientSide = clientSide;
    }
    @Override
    public void handle(Event event) {
        Difficulties newDifficulty = ((ChangeBotDifficultyEvent) event).getDifficulty();
        if(clientSide) {
            SceneLoader.getPlayerView().setCurrentDifficulty(newDifficulty);
            SceneLoader.getCurrentController().handle(event);
            return;
        }
        OnlineGameController.getInstance().setBotDifficulty(newDifficulty);
        try {
            if(OnlineGameController.getInstance().botIsPlaying())
                Server.getInstance().getEventTransmitter().broadcast(new ChangeBotDifficultyEvent(newDifficulty));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
