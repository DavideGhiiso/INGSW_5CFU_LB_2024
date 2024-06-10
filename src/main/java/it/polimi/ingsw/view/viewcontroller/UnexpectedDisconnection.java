package it.polimi.ingsw.view.viewcontroller;

import it.polimi.ingsw.events.data.Event;
import it.polimi.ingsw.view.SceneLoader;
import javafx.event.ActionEvent;

public class UnexpectedDisconnection implements ViewController {
    @Override
    public void handle(Event event) {

    }

    public void onButtonClick() {
        SceneLoader.changeScene("fxml/menu.fxml");
    }
}
