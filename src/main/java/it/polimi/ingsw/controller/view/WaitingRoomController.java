package it.polimi.ingsw.controller.view;

import it.polimi.ingsw.events.EventHandler;
import it.polimi.ingsw.events.data.Event;
import it.polimi.ingsw.events.data.GameInfo;
import it.polimi.ingsw.events.data.UpdatePlayerCountEvent;
import it.polimi.ingsw.events.data.client.ForceGameStartEvent;
import it.polimi.ingsw.events.data.client.RequestGameInfoEvent;
import it.polimi.ingsw.networking.Client;
import it.polimi.ingsw.networking.PingSender;
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
    private static final int REQUEST_PERIOD = 5000;

    @FXML
    Button startAnywayButton;
    @FXML
    VBox popup;
    @FXML
    Button closePopupButton;
    @FXML
    Label playerCounter;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    requestConnectedPlayers();
                } catch (RuntimeException e) {
                    cancel();
                }
            }
        }, 0, REQUEST_PERIOD);
    }
    private void requestConnectedPlayers() {
        Client.getInstance().send(new RequestGameInfoEvent(GameInfo.CONNECTED_PLAYERS));
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
        Client.getInstance().stop();
        Platform.runLater(() -> SceneLoader.changeScene("fxml/menu.fxml"));
    }

    public void onStartAnywayButtonClick(ActionEvent actionEvent) {
        Client.getInstance().send(new ForceGameStartEvent());
    }

    public void updateCounter(int newValue) {
        playerCounter.setText("Giocatori: "+ newValue+"/4");
    }

    /**
     * @see it.polimi.ingsw.events.data.UpdatePlayerCountEvent
     */
    public void handle(Event event) {
        if(!Objects.equals(event.getID(), "UPDATE_PLAYER_COUNT_EVENT"))
            return;
        UpdatePlayerCountEvent updatePlayerCountEvent = (UpdatePlayerCountEvent) event.getEvent();
        Platform.runLater(() -> {
            updateCounter(updatePlayerCountEvent.getPlayerCount());
            if(updatePlayerCountEvent.getPlayerCount() >= 2) {
                startAnywayButton.getStyleClass().add("button-clickable");
                startAnywayButton.getStyleClass().remove("button-non-clickable");
            }
        });
    }
}
