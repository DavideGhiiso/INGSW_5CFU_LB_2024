package it.polimi.ingsw.controller.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class OnlineLobbyController implements ViewController {
    @FXML
    TextField usernameField;
    public void onTextFieldEnter(ActionEvent actionEvent) {
        System.out.println(usernameField.getText());
    }
}
