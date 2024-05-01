package it.polimi.ingsw.view.viewcontroller;

import it.polimi.ingsw.events.data.Event;
import it.polimi.ingsw.events.data.GameInfo;
import it.polimi.ingsw.events.data.client.PlaceCardEvent;
import it.polimi.ingsw.events.data.server.HandChangedEvent;
import it.polimi.ingsw.events.data.server.NewTurnEvent;
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
import java.util.*;

public class InGameController implements ViewController, Initializable {
    private ImageView selectedCard = null;
    @FXML
    Label usernameLabel;
    @FXML
    HBox centralPane;
    @FXML
    Button playCardButton;
    @FXML
    Label northLabel;
    @FXML
    Label westLabel;
    @FXML
    Label southLabel;
    @FXML
    Label eastLabel;
    @FXML
    Label team1Label;
    @FXML
    Label team2Label;
    @FXML
    HBox bottomPane;
    @FXML
    VBox popup;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Client client = Client.getInstance();
        client.requestInfo(GameInfo.CURRENT_TABLE);
        client.requestInfo(GameInfo.CURRENT_HAND);
        //client.requestInfo(GameInfo.SCORE);
        usernameLabel.setText(SceneLoader.getPlayerView().getUsername());
    }

    /**
     * @see HandChangedEvent
     * @see ScoreEvent
     * @see TableChangedEvent
     * @see NewTurnEvent
     */
    @Override
    public void handle(Event event) {
        Platform.runLater(() -> {
            switch (event.getID()) {
                case "HAND_CHANGED_EVENT" -> updateHand((HandChangedEvent) event);
                case "SCORE_EVENT" -> updateScore((ScoreEvent) event);
                case "TABLE_CHANGED_EVENT" -> updateTable((TableChangedEvent) event);
                case "NEW_TURN_EVENT" -> updateCurrentPlayer((NewTurnEvent) event);
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
        String[] classes = {"team1Label", "team2Label"};
        List<String> teamNames = new ArrayList<>();
        teamNames.add(event.getFistTeamNames()[0]);
        teamNames.add(event.getSecondTeamNames()[0]);
        teamNames.add(event.getFistTeamNames()[1]);
        teamNames.add(event.getSecondTeamNames()[1]);
        int selfIndex = teamNames.indexOf(SceneLoader.getPlayerView().getUsername());
        teamNames = sortPlayers(teamNames,selfIndex);
        southLabel.setText(teamNames.getFirst());
        eastLabel.setText(teamNames.get(1));
        northLabel.setText(teamNames.get(2));
        westLabel.setText(teamNames.get(3));
        team1Label.setText(event.getFirstTeamPoints() +"");
        team1Label.setText(event.getSecondTeamPoints() +"");
        if((selfIndex%2)!=0)
            classes = new String[]{"team2Label", "team1Label"};
        southLabel.getStyleClass().add(classes[0]);
        northLabel.getStyleClass().add(classes[0]);
        eastLabel.getStyleClass().add(classes[1]);
        westLabel.getStyleClass().add(classes[1]);
    }

    private List<String> sortPlayers(List<String> players, int selfIndex) {
        List<String> result = new ArrayList<>();

        for(int idx=0; idx < players.size(); idx++) {
            result.add(idx, players.get((idx + selfIndex) % players.size())); // TODO: handle IndexOutOfBounds
        }
        return result;
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
    private void updateCurrentPlayer(NewTurnEvent event) {
        List<Label> labels = new ArrayList<>();
        labels.add(southLabel);
        labels.add(eastLabel);
        labels.add(northLabel);
        labels.add(westLabel);
        for(Label l: labels) {
            l.getStyleClass().remove("current-player-label");
            if(l.getText().equals(event.getUsername()))
                l.getStyleClass().add("current-player-label");
        }
    }

    public void onPlayCardButtonClick(ActionEvent actionEvent) {
        playCardButton.setVisible(false);
        SceneLoader.getPlayerView().setYourTurn(false);
        Client.getInstance().send(new PlaceCardEvent(urlToCard(selectedCard.getImage().getUrl())));
    }

    public void onExitGameButtonClick(ActionEvent actionEvent) {
        Platform.runLater(() -> SceneLoader.changeScene("fxml/menu.fxml"));
        Client.getInstance().stop();
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
