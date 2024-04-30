package it.polimi.ingsw.model.bot;

import it.polimi.ingsw.model.Card;

import java.util.List;
import java.util.stream.Stream;

public class Bot {
    private final static int THINK_TIME = 3000;
    private Difficulty difficulty;
    private List<Card> team1Cards;
    private List<Card> team2Cards;
    private List<Card> onTableList;

    public Bot(Difficulty difficulty, List<Card> team1Cards, List<Card> team2Cards, List<Card> onTableList) {
        this.difficulty = difficulty;
        this.team1Cards = team1Cards;
        this.team2Cards = team2Cards;
        this.onTableList = onTableList;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public Card playCard(List<Card> inHandList) {
        try {
            Thread.sleep(THINK_TIME);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        List<Card> playedCards = Stream.concat(team1Cards.stream(), team2Cards.stream()).toList();
        return difficulty.chooseCard(inHandList, onTableList, playedCards);
    }
}
