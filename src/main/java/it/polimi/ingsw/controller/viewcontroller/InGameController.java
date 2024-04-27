package it.polimi.ingsw.controller.viewcontroller;

import it.polimi.ingsw.events.data.Event;
import it.polimi.ingsw.events.data.GameInfo;
import it.polimi.ingsw.events.data.HandChangedEvent;
import it.polimi.ingsw.events.data.client.RequestGameInfoEvent;
import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.networking.Client;
import it.polimi.ingsw.view.PlayerView;
import it.polimi.ingsw.view.SceneLoader;
import it.polimi.ingsw.view.View;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class InGameController implements ViewController, Initializable {
    @FXML
    HBox bottomPane;
    @FXML
    VBox popup;
    @FXML
    Label topLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SceneLoader.getPlayerView().setObserver(this);
        requestCardsHand();
    }

    /**
     * @see it.polimi.ingsw.events.data.HandChangedEvent
     * @param event
     */
    @Override
    public void handle(Event event) {
        if(event.getID().equals("HAND_CHANGED_EVENT")) {
            HandChangedEvent handChangedEvent = (HandChangedEvent) event.getEvent();
            System.out.println(handChangedEvent.getHand());
            Platform.runLater(() ->
                    bottomPane.getChildren().addAll(getCardsImageViews(SceneLoader.getPlayerView().getHand())));
        }
    }

    public void onMenuButtonClicked(ActionEvent actionEvent) {
        if(((Button)actionEvent.getSource()).getStyleClass().contains("button-non-clickable"))
            return;
        popup.setVisible(true);
    }

    public void onClosePopupClick(ActionEvent actionEvent) {
        if(((Button)actionEvent.getSource()).getStyleClass().contains("button-non-clickable"))
            return;
        popup.setVisible(false);
    }

    private void requestCardsHand() {
        Client.getInstance().send(new RequestGameInfoEvent(GameInfo.CURRENT_HAND));
    }

    private List<ImageView> getCardsImageViews(List<Card> cards) {
        List<ImageView> result = new ArrayList<>();
        for(Card card: cards) {
            URL path = View.class.getResource("images/" + card.getNumber()+card.getSuit().toString() + ".png");
            ImageView imageView = new ImageView(new Image(String.valueOf(path)));
            imageView.getStyleClass().add("in-hand-card");
            imageView.setFitHeight(200);
            imageView.setPreserveRatio(true);
            result.add(imageView);
        }
        return result;
    }


}
