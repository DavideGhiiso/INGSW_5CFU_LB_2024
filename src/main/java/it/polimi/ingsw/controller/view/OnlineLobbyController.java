package it.polimi.ingsw.controller.view;

import it.polimi.ingsw.events.EventHandler;
import it.polimi.ingsw.events.Response;
import it.polimi.ingsw.events.data.Event;
import it.polimi.ingsw.events.data.JoinGameResponseEvent;
import it.polimi.ingsw.events.data.client.ClientDisconnectedEvent;
import it.polimi.ingsw.events.data.client.JoinGameEvent;
import it.polimi.ingsw.events.data.client.JoinOnGoingGameEvent;
import it.polimi.ingsw.networking.Client;
import it.polimi.ingsw.view.SceneLoader;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Class that controls the interactive elements in the onlineLobby scene and the reception of a JoinGameResponseEvent
 */
public class OnlineLobbyController implements ViewController, EventHandler, Initializable {
    private static OnlineLobbyController instance;
    private static final int MIN_USERNAME_LENGTH = 3;
    private static final int MAX_USERNAME_LENGTH = 10;
    public VBox popup;
    public Button closePopupButton;
    public Label popupContent;
    @FXML
    Button backButton;
    @FXML
    Label usernameErrorText;
    @FXML
    TextField usernameField;
    @FXML
    Button enterButton;

    public static OnlineLobbyController getInstance() {
        return instance;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        instance = this;
    }

    /**
     * Handles both the click of enter key inside the usernameField TextField and the click of the enterButton Button.
     * If the length of the username is between MAX and MIN length, accepts it and empties the field
     */
    public void onTextFieldEnter(ActionEvent actionEvent) {
        if(enterButton.getStyleClass().contains("button-clickable")) {
            popup.setVisible(true);
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
    public void toggleEnterButtonClass(KeyEvent keyEvent) {
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

    public void onBackButtonClick(ActionEvent actionEvent) {
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
        Platform.runLater(() -> {
            JoinGameResponseEvent joinGameResponseEvent = (JoinGameResponseEvent) event.getEvent();
            Response response = joinGameResponseEvent.getResponse();
            System.out.println("Response: "+ response);
            switch (response) {
                case OK -> {
                    popupContent.setText("Joining game...");
                    Platform.runLater(() -> SceneLoader.changeScene("fxml/ingame.fxml"));
                }
                case CAN_REPLACE_BOT -> {
                    Client.getInstance().send(new JoinOnGoingGameEvent(usernameField.getText()));
                }
                case GAME_FULL -> {
                    popupContent.setText("This server is already full!");
                    Client.getInstance().stop();
                    closePopupButton.getStyleClass().add("button-clickable");
                    closePopupButton.getStyleClass().remove("button-non-clickable");
                }
                case USERNAME_TAKEN -> {
                    popupContent.setText("The username " + usernameField.getText() + " is already in use!");
                }
            }
        });
    }

    public void onClosePopupClick(ActionEvent actionEvent) {
        if (closePopupButton.getStyleClass().contains("button-non-clickable"))
            return;
        popup.setVisible(false);
        popupContent.setText("Connecting to server...");
    }
}
