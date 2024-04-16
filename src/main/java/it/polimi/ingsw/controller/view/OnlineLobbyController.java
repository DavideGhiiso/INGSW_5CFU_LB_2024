package it.polimi.ingsw.controller.view;

import it.polimi.ingsw.view.SceneLoader;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class OnlineLobbyController implements ViewController {
    private static final int MIN_USERNAME_LENGTH = 3;
    private static final int MAX_USERNAME_LENGTH = 10;
    @FXML
    Label usernameErrorText;
    @FXML
    TextField usernameField;
    @FXML
    Button enterButton;

    /**
     * Handles both the click of enter key inside the usernameField TextField and the click of the enterButton Button.
     * If the length of the username is between MAX and MIN length, accepts it and empties the field
     */
    public void onTextFieldEnter(ActionEvent actionEvent) {
        if(enterButton.getStyleClass().contains("button-clickable")) {
            System.out.println(usernameField.getText());
            usernameField.setText("");
            enterButton.getStyleClass().add("button-non-clickable");
            enterButton.getStyleClass().remove("button-clickable");
        }
    }

    /**
     * Toggles the enterButton class according to the length of the string inside the usernameField
     */
    public void toggleEnterButtonClass(KeyEvent keyEvent) {
        System.out.println(usernameField.getText().length() + " " + enterButton.getStyleClass().toString());
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
            }
        }
    }

    public void onBackButtonClick(ActionEvent actionEvent) {
        Platform.runLater(() -> SceneLoader.changeScene("fxml/menu.fxml"));
    }
}
