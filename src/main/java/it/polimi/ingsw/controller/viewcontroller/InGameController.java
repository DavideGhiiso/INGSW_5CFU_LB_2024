package it.polimi.ingsw.controller.viewcontroller;

import it.polimi.ingsw.events.data.Event;
import it.polimi.ingsw.events.data.GameInfo;
import it.polimi.ingsw.events.data.server.HandChangedEvent;
import it.polimi.ingsw.events.data.server.ScoreEvent;
import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.networking.Client;
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
    Label team1Label;
    @FXML
    Label scoreLabel;
    @FXML
    Label team2Label;
    @FXML
    HBox bottomPane;
    @FXML
    VBox popup;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SceneLoader.getPlayerView().setObserver(this);
        Client client = Client.getInstance();
        client.requestInfo(GameInfo.CURRENT_HAND);
        client.requestInfo(GameInfo.SCORE);
    }

    /**
     * @see HandChangedEvent
     * @param event
     */
    @Override
    public void handle(Event event) {
        switch (event.getID()) {
            case "HAND_CHANGED_EVENT" ->
                Platform.runLater(() ->
                    bottomPane.getChildren().addAll(getCardsImageViews(SceneLoader.getPlayerView().getHand())));

            case "SCORE_EVENT" ->
                Platform.runLater(() ->
                    updateScore((ScoreEvent) event));

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
    private void updateScore(ScoreEvent event) {
        String[] teamNames = event.getFistTeamNames();
        team1Label.setText(teamNames[0] + "\n" + teamNames[1]);
        teamNames = event.getSecondTeamNames();
        team2Label.setText(teamNames[0] + "\n" + teamNames[1]);
        scoreLabel.setText(event.getFirstTeamPoints() + " - " + event.getSecondTeamPoints());
    }

}
