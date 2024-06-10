package it.polimi.ingsw.model.exceptions;

import it.polimi.ingsw.model.Suit;
/**
 * Exception thrown when a built card does not exist
 */
public class IllegalCardConstructionException extends Exception {
    private String message;

    public IllegalCardConstructionException() {
        super();
    }
    public IllegalCardConstructionException(Suit suit, int number) {
        message = "The following Card does not exist: " + number + " of " + suit;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
