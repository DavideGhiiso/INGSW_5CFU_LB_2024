package it.polimi.ingsw.model;

import java.util.List;

public class Player {
    private final String name;
    private final List<Card> hand;
    private boolean isBot;

    public Player(String username, List<Card> generatedHand) {
        this.name = username;
        this.hand = generatedHand;
        this.isBot = false;
    }

    public void playCard(Card card) {
        this.hand.remove(card);
    }

    public String getName() {
        return name;
    }

    public List<Card> getHand() {
        return hand;
    }

    public boolean isBot() {
        return isBot;
    }

    public void setBot(boolean bot) {
        isBot = bot;
    }
}
