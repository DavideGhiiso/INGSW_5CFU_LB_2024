package it.polimi.ingsw.model;

import it.polimi.ingsw.model.Card;
import it.polimi.ingsw.model.Dealer;
import org.junit.jupiter.api.Test;

import java.util.List;

public class DealerTest {
    @Test
    public void correctHandTest() {
        Dealer dealer = new Dealer();
        List<Card> cards = dealer.getCardsHand();
        System.out.println(cards.toString());
    }
}
