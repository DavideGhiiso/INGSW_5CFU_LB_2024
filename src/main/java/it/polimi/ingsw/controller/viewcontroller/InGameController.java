package it.polimi.ingsw.controller.viewcontroller;

import it.polimi.ingsw.events.data.Event;
import it.polimi.ingsw.events.data.GameInfo;
import it.polimi.ingsw.events.data.client.PlaceCardEvent;
import it.polimi.ingsw.events.data.server.HandChangedEvent;
import it.polimi.ingsw.events.data.server.ScoreEvent;
import it.polimi.ingsw.events.data.server.TableChangedEvent;
import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.model.IllegalCardConstructionException;
import it.polimi.ingsw.model.Suit;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class InGameController implements ViewController, Initializable {
    private ImageView selectedCard = null;
    @FXML
    HBox centralPane;
    @FXML
    Button playCardButton;
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
     * @see ScoreEvent
     * @see TableChangedEvent
     */
    @Override
    public void handle(Event event) {
        Platform.runLater(() -> {
            switch (event.getID()) {
                case "HAND_CHANGED_EVENT" -> updateHand((HandChangedEvent) event);
                case "SCORE_EVENT" -> updateScore((ScoreEvent) event);
                case "TABLE_CHANGED_EVENT" -> updateTable((TableChangedEvent) event);
            }
        });
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

    private void onImageClick(MouseEvent mouseEvent) {
        if(!SceneLoader.getPlayerView().isYourTurn())
            return;
        ImageView clickedImage = (ImageView) mouseEvent.getSource();
        if(selectedCard == clickedImage) { // click on already selected card => toggle
            selectedCard.getStyleClass().remove("selected-card");
            selectedCard = null;
            playCardButton.setVisible(false);
        } else { // change selected card
            if(selectedCard != null) {
                selectedCard.getStyleClass().remove("selected-card");
                selectedCard.setViewOrder(0.0);
            }
            selectedCard = clickedImage;
            selectedCard.getStyleClass().add("selected-card");
            playCardButton.setVisible(true);
        }
    }

    /**
     * Gets a list of ImageView that each represent a card. A style is added through passed method
     * @param cards list of cards to convert
     * @param style method to stylyze every card
     * @return
     */
    private List<ImageView> getCardsImageViews(List<Card> cards, Method style) {
        List<ImageView> result = new ArrayList<>();
        for(Card card: cards) {
            URL path = View.class.getResource("images/" + card.getNumber()+ "_" +card.getSuit().toString() + ".png");
            ImageView imageView = new ImageView(new Image(String.valueOf(path)));
            try {
                style.invoke(this, imageView);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
            result.add(imageView);
        }
        return result;
    }

    public void inHandStyle(ImageView imageView) {
        imageView.getStyleClass().add("in-hand-card");
        imageView.setOnMouseClicked(this::onImageClick);
        imageView.setOnMouseEntered(this::onMouseEntered);
        imageView.setOnMouseExited(this::onMouseExited);
        imageView.setFitHeight(200);
        imageView.setPreserveRatio(true);
    }

    public void onTableStyle(ImageView imageView) {
        imageView.getStyleClass().add("in-hand-card");
        imageView.setOnMouseClicked(this::onImageClick);
        imageView.setOnMouseEntered(this::onMouseEntered);
        imageView.setOnMouseExited(this::onMouseExited);
        imageView.setFitHeight(200);
        imageView.setPreserveRatio(true);
    }
    private void onMouseEntered(MouseEvent mouseEvent) {
        ImageView clickedImage = (ImageView) mouseEvent.getSource();
        clickedImage.setViewOrder(-1.0);
    }

    private void onMouseExited(MouseEvent mouseEvent) {
        ImageView clickedImage = (ImageView) mouseEvent.getSource();
        if(!clickedImage.equals(selectedCard))
            clickedImage.setViewOrder(0.0);
    }
    private void updateHand(HandChangedEvent event) {
        Method inHandStyle;
        try {
            inHandStyle = InGameController.class.getMethod("inHandStyle", ImageView.class);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        bottomPane.getChildren().clear();
        bottomPane.getChildren().addAll(getCardsImageViews(event.getHand(), inHandStyle));
    }

    private void updateScore(ScoreEvent event) {
        String[] teamNames = event.getFistTeamNames();
        team1Label.setText(teamNames[0] + "\n" + teamNames[1]);
        teamNames = event.getSecondTeamNames();
        team2Label.setText(teamNames[0] + "\n" + teamNames[1]);
        scoreLabel.setText(event.getFirstTeamPoints() + " - " + event.getSecondTeamPoints());
    }
    private void updateTable(TableChangedEvent event) {
        Method onTableStyle;
        try {
            onTableStyle = InGameController.class.getMethod("onTableStyle", ImageView.class);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        centralPane.getChildren().clear();
        centralPane.getChildren().addAll(getCardsImageViews(event.getCards(), onTableStyle));
    }

    public void onPlayCardButtonClick(ActionEvent actionEvent) {
        playCardButton.setVisible(false);
        SceneLoader.getPlayerView().setYourTurn(false);
        Client.getInstance().send(new PlaceCardEvent(urlToCard(selectedCard.getImage().getUrl())));
    }

    private Card urlToCard(String url) {
        String[] args = url.split("/");
        String name = args[args.length-1];
        args = name.split("_");
        try {
            return new Card(Suit.valueOf(args[1].replaceAll(".png", "")), Integer.parseInt(args[0]));
        } catch (IllegalCardConstructionException e) {
            throw new RuntimeException(e);
        }
    }
}
