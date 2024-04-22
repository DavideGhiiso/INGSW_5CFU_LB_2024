package it.polimi.ingsw.controller.view;

import it.polimi.ingsw.events.data.Event;
import it.polimi.ingsw.view.SceneLoader;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class MenuController implements ViewController {

    public void onExitButtonClick(ActionEvent actionEvent) {
        Platform.exit();
        System.exit(0);
    }

    public void onOnlineButtonClick(ActionEvent actionEvent) {
        Platform.runLater(() -> SceneLoader.changeScene("fxml/onlineLobby.fxml"));
    }

    public void onOfflineButtonClick(ActionEvent actionEvent) {
        System.out.println("Offline");
    }

    public void onOptionsButtonClick(ActionEvent actionEvent) {
        Platform.runLater(() -> SceneLoader.changeScene("fxml/options.fxml"));
    }

    @Override
    public void handle(Event event) {

    }
}
