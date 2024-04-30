package it.polimi.ingsw.model;

import java.util.List;

public class Player {
    private String name;
    private final List<Card> hand;
    private boolean isBot;

    public Player(String username, List<Card> generatedHand, boolean isBot) {
        this.name = username;
        this.hand = generatedHand;
        this.isBot = isBot;
    }

    public void playCard(Card card) {
        System.out.println("Removed:"+ this.hand.remove(card));
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
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name + " " + (isBot? "BOT":"");
    }
}
