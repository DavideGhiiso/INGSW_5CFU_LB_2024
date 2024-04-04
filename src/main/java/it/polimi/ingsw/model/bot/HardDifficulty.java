package it.polimi.ingsw.model.bot;

import it.polimi.ingsw.model.Card;

import java.util.List;

public class HardDifficulty extends Difficulty{
    @Override
    public Card chooseCard(List<Card> inHandList, List<Card> onTableList, List<Card> playedCards) {
        return null;
    }
}
