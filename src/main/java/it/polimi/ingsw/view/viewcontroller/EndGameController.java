package it.polimi.ingsw.view.viewcontroller;

import it.polimi.ingsw.controller.OfflineGameController;
import it.polimi.ingsw.events.data.client.ContinueGameEvent;
import it.polimi.ingsw.events.data.server.EndGameResult;
import it.polimi.ingsw.events.data.server.EndGameResultsEvent;
import it.polimi.ingsw.events.data.Event;
import it.polimi.ingsw.model.GameResult;
import it.polimi.ingsw.model.Points;
import it.polimi.ingsw.networking.Client;
import it.polimi.ingsw.view.SceneLoader;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class EndGameController implements ViewController {
    private GameResult firstTeamResult;
    private GameResult secondTeamResult;
    private int firstTeamTotalPoints;
    private int secondTeamTotalPoints;
    private static final String TICK_SYMBOL = "✓";
    private static final String X_SYMBOL = "✕";
    private static final String DEUCE_SYMBOL = "-";
    @FXML
    GridPane pointsGridPane;
    @FXML
    Button continueButton;
    @FXML
    VBox popup;
    @FXML
    GridPane resultTable;
    @FXML
    Label team1Points;
    @FXML
    Label team2Points;
    @FXML
    Label team1PointsTotal;
    @FXML
    Label team2PointsTotal;
    @FXML
    Label team2Header;
    @FXML
    Label team1Header;
    @FXML
    Label winnersLabel;

    /**
     * @see EndGameResultsEvent
     * @see it.polimi.ingsw.events.data.server.GameResumingWarningEvent
     */
    @Override
    public void handle(Event event) {
        switch (event.getID()) {
            case "END_GAME_RESULTS_EVENT" -> onEndGameResultsEvent(event);
            case "GAME_RESUMING_WARNING_EVENT" -> {
                continueButton.getStyleClass().remove("button-clickable");
                continueButton.getStyleClass().add("button-non-clickable");
                popup.setVisible(true);
            }
        }
    }

    private void onEndGameResultsEvent(Event event) {
        EndGameResultsEvent endGameResultsEvent = (EndGameResultsEvent) event.getEvent();
        firstTeamResult = endGameResultsEvent.getFirstTeamResult();
        secondTeamResult = endGameResultsEvent.getSecondTeamResult();
        firstTeamTotalPoints = endGameResultsEvent.getFirstTeamTotalPoints();
        secondTeamTotalPoints = endGameResultsEvent.getSecondTeamTotalPoints();
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
        if(SceneLoader.getPlayerView().isOffline()) {
            if(winningTeam.getFirstPlayer().equals(OfflineGameController.DEFAULT_OFFLINE_GAME) || winningTeam.getSecondPlayer().equals(OfflineGameController.DEFAULT_OFFLINE_GAME))
               winnersLabel.setText("Hai vinto!");
            else
                winnersLabel.setText("Hai perso!");
        } else
            winnersLabel.setText("Vincono " + winningTeam.getFirstPlayer() + " e " + winningTeam.getSecondPlayer() + "!");
    }

    private void setPoints() {
        if(firstTeamTotalPoints >= 11 || secondTeamTotalPoints >= 11)
            definitiveWinRoutine();
        team1Points.setText(String.valueOf(firstTeamResult.getTotalPoints()));
        team2Points.setText(String.valueOf(secondTeamResult.getTotalPoints()));
        team1PointsTotal.setText(String.valueOf(firstTeamTotalPoints));
        team2PointsTotal.setText(String.valueOf(secondTeamTotalPoints));
    }

    private void definitiveWinRoutine() {
        if(!SceneLoader.getPlayerView().isOffline())
            Client.getInstance().stop();
        if(firstTeamTotalPoints >= 11)
            team1PointsTotal.getStyleClass().add("winning-points");
        if(secondTeamTotalPoints >= 11)
            team2PointsTotal.getStyleClass().add("winning-points");
        HBox buttonContainer = (HBox)continueButton.getParent();
        buttonContainer.getChildren().remove(continueButton);
        Button exitButton = (Button) buttonContainer.getChildren().getFirst();
        exitButton.setText("Vedi risultati");
        exitButton.setOnAction(e -> onSeeResultsButtonClick(exitButton));
    }

    private void onSeeResultsButtonClick(Button exitButton) {
        exitButton.setText("Esci");
        exitButton.setOnAction(this::onExitButtonClick);
        ((VBox)resultTable.getParent()).getChildren().remove(resultTable);
        GameResult winningTeam = null;
        pointsGridPane.getChildren().removeIf(node -> GridPane.getRowIndex(node) == 0);
        if(firstTeamTotalPoints > secondTeamTotalPoints)
            winningTeam = firstTeamResult;
        else if (firstTeamTotalPoints < secondTeamTotalPoints)
            winningTeam = secondTeamResult;
        if(winningTeam != null)
            winnersLabel.setText("Vincono " + winningTeam.getFirstPlayer() + " e " + winningTeam.getSecondPlayer() + "!");
        else
            winnersLabel.setText("Pareggio!");
    }

    private void setTeamHeaders() {
        team1Header.setText(firstTeamResult.getFirstPlayer() + " e " + firstTeamResult.getSecondPlayer());
        team2Header.setText(secondTeamResult.getFirstPlayer() + " e " + secondTeamResult.getSecondPlayer());
    }

    private void populateTable() {
        resultTable.add(getGridPaneLabel(String.valueOf(firstTeamResult.getScopa())), 1,1); // col row
        resultTable.add(getGridPaneLabel(String.valueOf(secondTeamResult.getScopa())), 2,1);
        int row = 2;
        for(Points point: Points.values()) {
            if(firstTeamResult.getPointsMade().contains(point)) {
                resultTable.add(getGridPaneLabel(TICK_SYMBOL), 1,row);
                resultTable.add(getGridPaneLabel(X_SYMBOL), 2,row);
            }
            else if(secondTeamResult.getPointsMade().contains(point)) {
                resultTable.add(getGridPaneLabel(TICK_SYMBOL), 2,row);
                resultTable.add(getGridPaneLabel(X_SYMBOL), 1,row);
            }
            else {
                resultTable.add(getGridPaneLabel(DEUCE_SYMBOL), 1,row);
                resultTable.add(getGridPaneLabel(DEUCE_SYMBOL), 2,row);
            }
            row++;
        }
    }

    private Label getGridPaneLabel(String content) {
        Label label = new Label(content);
        GridPane.setHalignment(label, HPos.CENTER);
        return label;
    }

    public void onContinueButtonClick(ActionEvent actionEvent) {
        Button continueButton = (Button)actionEvent.getSource();
        continueButton.getStyleClass().remove("button-clickable");
        continueButton.getStyleClass().add("button-non-clickable");
        if(SceneLoader.getPlayerView().isOffline()) {
            SceneLoader.changeScene("fxml/ingame.fxml");
            OfflineGameController.getInstance().continueGame();
        }
        else
            Client.getInstance().send(new ContinueGameEvent());
    }

    public void onExitButtonClick(ActionEvent actionEvent) {
        Platform.runLater(() -> SceneLoader.changeScene("fxml/menu.fxml"));
        if(!SceneLoader.getPlayerView().isOffline())
            try {
                Client.getInstance().stop();
            } catch (RuntimeException ignored) {
            }
    }
}
