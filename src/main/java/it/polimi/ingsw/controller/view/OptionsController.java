package it.polimi.ingsw.controller.view;

import it.polimi.ingsw.networking.Server;
import it.polimi.ingsw.view.SceneLoader;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class OptionsController implements ViewController, Initializable {
    private static int port = Server.DEFAULT_PORT;
    private static String address = "localhost";
    @FXML
    TextField serverPort;
    @FXML
    TextField serverAddress;

    public void onBackButtonClick(ActionEvent actionEvent) {
        Platform.runLater(() -> SceneLoader.changeScene("fxml/menu.fxml"));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        serverAddress.setText(address);
        serverPort.setText(port + "");
    }

    public static int getPort() {
        return port;
    }

    public static String getAddress() {
        return address;
    }

    public void onServerAddressChange(KeyEvent keyEvent) {
        address = serverAddress.getText();
        System.out.println(address+" "+port);
    }

    public void onServerPortChange(KeyEvent keyEvent) {
        port = Integer.parseInt(serverPort.getText());
        System.out.println(address+" "+port);
    }
}
