package it.polimi.ingsw.controller.viewcontroller;

import it.polimi.ingsw.events.data.Event;
import it.polimi.ingsw.events.data.GameInfo;
import it.polimi.ingsw.events.data.server.UpdatePlayerCountEvent;
import it.polimi.ingsw.events.data.client.RequestGameInfoEvent;
import it.polimi.ingsw.events.data.client.StartGameEvent;
import it.polimi.ingsw.networking.Client;
import it.polimi.ingsw.view.SceneLoader;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class WaitingRoomController implements ViewController, Initializable {
    private static final int REQUEST_PERIOD = 1000;
    private int oldPlayersNumber;
    @FXML
    Label topLabel;
    @FXML
    Button startAnywayButton;
    @FXML
    VBox popup;
    @FXML
    Button closePopupButton;
    @FXML
    Label playerCounter;


    /**
     * Initialize scene by starting a Timer that periodically checks if the current players in the Server have changed
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String defaultString = "In attesa di altri giocatori...";
        topLabel.setText(SceneLoader.getPlayerView().getUsername() + "\n" + defaultString);
        startRequestConnectedPlayersTimer();
    }

    private void startRequestConnectedPlayersTimer() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    if(SceneLoader.getCurrentController().getClass() != WaitingRoomController.class)
                        cancel();
                    requestConnectedPlayers();
                } catch (RuntimeException e) {
                    cancel();
                }
            }
        }, 0, REQUEST_PERIOD);
    }

    private void requestConnectedPlayers() {
        Client.getInstance().send(new RequestGameInfoEvent(GameInfo.CONNECTED_PLAYERS, oldPlayersNumber));
    }
    public void onClosePopupClick(ActionEvent actionEvent) {
        if(((Button) actionEvent.getSource()).getStyleClass().contains("button-non-clickable"))
            return;
        popup.setVisible(false);
    }

    public void onStartGameButtonClick(ActionEvent actionEvent) {
        if(startAnywayButton.getStyleClass().contains("button-non-clickable"))
            return;
        popup.setVisible(true);
    }

    public void onExitButtonClick(ActionEvent actionEvent) {
        Platform.runLater(() -> SceneLoader.changeScene("fxml/menu.fxml"));
        Client.getInstance().stop();
    }

    public void onStartAnywayButtonClick(ActionEvent actionEvent) {
        Client.getInstance().send(new StartGameEvent());
    }

    public void updateCounter(int newValue) {
        playerCounter.setText("Giocatori: "+ newValue+"/4");
    }


    /**
     * @see UpdatePlayerCountEvent
     */
    public void handle(Event event) {
        if(!Objects.equals(event.getID(), "UPDATE_PLAYER_COUNT_EVENT"))
            return;
        UpdatePlayerCountEvent updatePlayerCountEvent = (UpdatePlayerCountEvent) event.getEvent();
        Platform.runLater(() -> {
            int newValue = updatePlayerCountEvent.getPlayerCount();
            this.oldPlayersNumber = newValue;
            updateCounter(newValue);
            if(updatePlayerCountEvent.getPlayerCount() >= 2) {
                startAnywayButton.getStyleClass().add("button-clickable");
                startAnywayButton.getStyleClass().remove("button-non-clickable");
            }
            else {
                if(!startAnywayButton.getStyleClass().contains("button-non-clickable"))
                    startAnywayButton.getStyleClass().add("button-non-clickable");
                startAnywayButton.getStyleClass().remove("button-clickable");
            }
        });
    }
}
