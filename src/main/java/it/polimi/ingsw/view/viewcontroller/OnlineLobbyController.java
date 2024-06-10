package it.polimi.ingsw.view.viewcontroller;

import it.polimi.ingsw.events.Response;
import it.polimi.ingsw.events.data.Event;
import it.polimi.ingsw.events.data.server.JoinGameResponseEvent;
import it.polimi.ingsw.events.data.client.JoinGameEvent;
import it.polimi.ingsw.events.data.client.JoinOnGoingGameEvent;
import it.polimi.ingsw.networking.Client;
import it.polimi.ingsw.view.SceneLoader;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.Objects;

/**
 * Class that controls the interactive elements in the onlineLobby scene and the reception of a JoinGameResponseEvent
 */
public class OnlineLobbyController implements ViewController {
    private static final int MIN_USERNAME_LENGTH = 3;
    private static final int MAX_USERNAME_LENGTH = 10;
    private boolean connected = false;
    @FXML
    Button okPopupButton;
    @FXML
    VBox popup;
    @FXML
    Button closePopupButton;
    @FXML
    Label popupContent;
    @FXML
    Button backButton;
    @FXML
    TextField usernameField;
    @FXML
    Button enterButton;

    /**
     * Handles both the click of enter key inside the usernameField TextField and the click of the enterButton Button.
     * If the length of the username is between MAX and MIN length, accepts it and empties the field
     */
    public void onTextFieldEnter() {
        if(enterButton.getStyleClass().contains("button-clickable")) {
            popup.setVisible(true);
            SceneLoader.getPlayerView().setUsername(usernameField.getText());
            try {
                connect(usernameField.getText());
            } catch (IOException e) {
                Client.LOGGER.severe("Connection with server could not be established.");
                closePopupButton.getStyleClass().add("button-clickable");
                closePopupButton.getStyleClass().remove("button-non-clickable");
                popupContent.setText("Connection with server could not be established.");
            }
        }
    }

    private void connect(String username) throws IOException {
        int port = OptionsController.getPort();
        String address = OptionsController.getAddress();
        Client client = Client.getInstance();
        client.connect(address, port);
        client.send(new JoinGameEvent(username));
    }

    /**
     * Toggles the enterButton class according to the length of the string inside the usernameField
     */
    public void toggleEnterButtonClass() {
        if(usernameField.getText().length() >= MIN_USERNAME_LENGTH && usernameField.getText().length() <= MAX_USERNAME_LENGTH) {
            if (enterButton.getStyleClass().contains("button-non-clickable")) {
                enterButton.getStyleClass().add("button-clickable");
                enterButton.getStyleClass().remove("button-non-clickable");
            }
        }
        else {
            if (enterButton.getStyleClass().contains("button-clickable")) {
                enterButton.getStyleClass().add("button-non-clickable");
                enterButton.getStyleClass().remove("button-clickable");
                popupContent.setText("Connection with server could not be established.");
            }
        }
    }

    public void onBackButtonClick() {
        if(backButton.getStyleClass().contains("button-non-clickable"))
            return;
        Platform.runLater(() -> SceneLoader.changeScene("fxml/menu.fxml"));
    }

    /**
     * Handles the reception of a JoinGameResponseEvent by either changing the scene to ingame scene, asking to join as
     * a bot replacement or by
     * @param event event to handle
     */
    @Override
    public void handle(Event event) {
        if(!Objects.equals(event.getID(), "JOIN_GAME_RESPONSE_EVENT"))
            return;
        Platform.runLater(() -> {
            JoinGameResponseEvent joinGameResponseEvent = (JoinGameResponseEvent) event.getEvent();
            Response response = joinGameResponseEvent.getResponse();
            popupContent.setText("Stai per entrare nella partita...");
            okPopupButton.setVisible(false);
            switch (response) {
                case OK -> Platform.runLater(() -> SceneLoader.changeScene("fxml/waitingRoom.fxml"));

                case CAN_REPLACE_BOT -> {
                    closePopupButton.getStyleClass().add("button-clickable");
                    closePopupButton.getStyleClass().remove("button-non-clickable");
                    popupContent.setText("La partita è già iniziata.\nVuoi entrare comunque?");
                    okPopupButton.setVisible(true);
                    connected = true;
                }
                case GAME_FULL -> {
                    popupContent.setText("Questo server è già pieno!");
                    closePopupButton.getStyleClass().add("button-clickable");
                    closePopupButton.getStyleClass().remove("button-non-clickable");
                    Client.getInstance().stop();
                }
                case USERNAME_TAKEN -> {
                    popupContent.setText("Lo username " + usernameField.getText() + " è già in uso!");
                    closePopupButton.getStyleClass().add("button-clickable");
                    closePopupButton.getStyleClass().remove("button-non-clickable");
                    Client.getInstance().stop();
                }

                case OK_ONGOING -> Platform.runLater(() -> SceneLoader.changeScene("fxml/ingame.fxml"));
            }
        });
    }

    public void onClosePopupClick() {
        if (closePopupButton.getStyleClass().contains("button-non-clickable"))
            return;
        popup.setVisible(false);
        popup.getStyleClass().add("button-non-clickable");
        popup.getStyleClass().remove("button-clickable");
        if(connected)
            Client.getInstance().stop();
    }

    public void onOkPopupButtonClick() {
        if(okPopupButton.getStyleClass().contains("button-clickable"))
            Client.getInstance().send(new JoinOnGoingGameEvent(usernameField.getText()));
    }
}
