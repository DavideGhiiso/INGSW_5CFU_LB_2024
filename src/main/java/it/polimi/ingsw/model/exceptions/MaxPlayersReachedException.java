package it.polimi.ingsw.model.exceptions;
/**
 * Exception thrown when trying to add a player to an already full game
 */
public class MaxPlayersReachedException extends Exception {
    @Override
    public String getMessage() {
        return "this game is already full!";
    }
}
