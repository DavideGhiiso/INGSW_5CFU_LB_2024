package it.polimi.ingsw.model.exceptions;

import it.polimi.ingsw.model.Suit;

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
