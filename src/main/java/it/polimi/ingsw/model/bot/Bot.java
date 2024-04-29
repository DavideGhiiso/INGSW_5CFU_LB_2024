package it.polimi.ingsw.model.bot;

import it.polimi.ingsw.model.Card;

public class Bot {
    private Difficulty difficulty;

    public Bot(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public Card playCard() {
        return difficulty.chooseCard();
    }
}
