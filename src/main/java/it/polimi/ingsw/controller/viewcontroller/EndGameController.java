package it.polimi.ingsw.controller.viewcontroller;

import it.polimi.ingsw.events.data.EndGameResultsEvent;
import it.polimi.ingsw.events.data.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.Objects;

public class EndGameController implements ViewController {
    @FXML
    Label team2Header;
    @FXML
    Label team1Header;
    @FXML
    Label winnersLabel;

    /**
     * @see it.polimi.ingsw.events.data.EndGameResultsEvent
     */
    @Override
    public void handle(Event event) {
        if(!Objects.equals(event.getID(), "END_GAME_RESULTS_EVENT"))
            return;
        EndGameResultsEvent endGameResultsEvent = (EndGameResultsEvent) event.getEvent();

    }
}
