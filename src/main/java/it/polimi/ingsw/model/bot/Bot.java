package it.polimi.ingsw.model.bot;

import it.polimi.ingsw.model.Card;

import java.util.List;
import java.util.stream.Stream;

/**
 * Class that represent a player controlled by the CPU
 */
public class Bot {
    private final static int THINK_TIME = 2200;
    private Difficulty difficulty;
    private final List<Card> team1Cards;
    private final List<Card> team2Cards;
    private final List<Card> onTableList;

    public Bot(Difficulty difficulty, List<Card> team1Cards, List<Card> team2Cards, List<Card> onTableList) {
        this.difficulty = difficulty;
        this.team1Cards = team1Cards;
        this.team2Cards = team2Cards;
        this.onTableList = onTableList;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    /**
     * Return a card contained in passed list based on the selected difficulty
     * @param inHandList cards belonging to a bot player
     * @return a card contained in passed list
     */
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
