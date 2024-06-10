package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.exceptions.MaxPlayersReachedException;
import it.polimi.ingsw.model.exceptions.UsernameTakenException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GameControllerTest {
    @Test
    public void rotatePlayersTest() {
        OnlineGameController onlineGameController = OnlineGameController.getInstance(new Game());
        try {
            onlineGameController.addPlayer("P1");
            onlineGameController.addPlayer("P2");
            onlineGameController.addPlayer("P3");
            onlineGameController.addPlayer("P4");
        } catch (MaxPlayersReachedException | UsernameTakenException e) {
            throw new RuntimeException(e);
        }
        onlineGameController.startGame();
        onlineGameController.endGame();
        onlineGameController.startGame();

        Assertions.assertEquals("P2", onlineGameController.getCurrentPlayer().getName());
    }
}
