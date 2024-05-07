package it.polimi.ingsw.view.viewcontroller;

import it.polimi.ingsw.events.data.server.EndGameResult;
import it.polimi.ingsw.events.data.server.EndGameResultsEvent;
import it.polimi.ingsw.events.data.Event;
import it.polimi.ingsw.model.GameResult;
import it.polimi.ingsw.model.Points;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.util.Objects;

public class EndGameController implements ViewController {
    private GameResult firstTeamResult;
    private GameResult secondTeamResult;
    private static final String TICK_SYMBOL = "✓";
    private static final String DEUCE_SYMBOL = "-";
    @FXML
    GridPane resultTable;
    @FXML
    Label team1Points;
    @FXML
    Label team2Points;
    @FXML
    Label team2Header;
    @FXML
    Label team1Header;
    @FXML
    Label winnersLabel;

    /**
     * @see EndGameResultsEvent
     */
    @Override
    public void handle(Event event) {
        if(!Objects.equals(event.getID(), "END_GAME_RESULTS_EVENT"))
            return;
        EndGameResultsEvent endGameResultsEvent = (EndGameResultsEvent) event.getEvent();
        firstTeamResult = endGameResultsEvent.getFirstTeamResult();
        secondTeamResult = endGameResultsEvent.getSecondTeamResult();
        EndGameResult result = endGameResultsEvent.getResult();
        setWinnersLabel(result, result.equals(EndGameResult.TEAM1) ? firstTeamResult:secondTeamResult);
        setPoints();
        setTeamHeaders();
        populateTable();
    }

    private void setWinnersLabel(EndGameResult result, GameResult winningTeam) {
        if(result.equals(EndGameResult.DRAW)) {
            winnersLabel.setText("Pareggio");
            return;
        }
        winnersLabel.setText("Vincono " + winningTeam.getFirstPlayer() + " e " + winningTeam.getSecondPlayer());
    }

    private void setPoints() {
        team1Points.setText(String.valueOf(firstTeamResult.getTotalPoints()));
        team2Points.setText(String.valueOf(secondTeamResult.getTotalPoints()));
    }
    private void setTeamHeaders() {
        team1Header.setText(firstTeamResult.getFirstPlayer() + " e " + firstTeamResult.getSecondPlayer());
        team2Header.setText(secondTeamResult.getFirstPlayer() + " e " + secondTeamResult.getSecondPlayer());
    }

    private void populateTable() {
        System.out.println(firstTeamResult.toString());
        System.out.println(secondTeamResult.toString());
        Label label = new Label(String.valueOf(firstTeamResult.getScopa()));
        GridPane.setHalignment(label, HPos.CENTER);
        resultTable.add(label, 1,1); // col row
        label = new Label(String.valueOf(secondTeamResult.getScopa()));
        GridPane.setHalignment(label, HPos.CENTER);
        resultTable.add(label, 2,1);
        int row = 2;
        for(Points point: Points.values()) {
            label = new Label();
            GridPane.setHalignment(label, HPos.CENTER);
            if(firstTeamResult.getPointsMade().contains(point)) {
                label.setText(TICK_SYMBOL);
                resultTable.add(label, 1,row);
            }
            else if(secondTeamResult.getPointsMade().contains(point)) {
                label.setText(TICK_SYMBOL);
                resultTable.add(label, 2,row);
            }
            else {
                resultTable.add(new Label(DEUCE_SYMBOL), 1,row);
                resultTable.add(new Label(DEUCE_SYMBOL), 2,row);
            }
            row++;
        }
    }

    /**
     * Creates a Label that can go into a GridPane element
     */
}
