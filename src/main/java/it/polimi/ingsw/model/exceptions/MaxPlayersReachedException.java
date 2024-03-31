package it.polimi.ingsw.model.exceptions;

public class MaxPlayersReachedException extends Exception {
    @Override
    public String getMessage() {
        return "this game is already full!";
    }
}
