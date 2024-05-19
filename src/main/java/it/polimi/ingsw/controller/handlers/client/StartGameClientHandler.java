package it.polimi.ingsw.controller.handlers.client;

import it.polimi.ingsw.events.EventHandler;
import it.polimi.ingsw.events.data.Event;
import it.polimi.ingsw.events.data.client.StartGameEvent;
import it.polimi.ingsw.events.data.server.ScoreEvent;
import it.polimi.ingsw.view.SceneLoader;
import javafx.application.Platform;

/**
 * @see it.polimi.ingsw.events.data.client.StartGameEvent
 */
public class StartGameClientHandler implements EventHandler {
    @Override
    public void handle(Event event) {
        Platform.runLater(() -> {
            SceneLoader.changeScene("fxml/ingame.fxml");
            StartGameEvent startGameEvent = (StartGameEvent) event.getEvent();

            SceneLoader.getCurrentController().handle(new ScoreEvent(
                    startGameEvent.getFistTeamNames(),
                    startGameEvent.getSecondTeamNames(),
                    startGameEvent.getTeam1Points(),
                    startGameEvent.getTeam2Points()));
        });

    }
}
