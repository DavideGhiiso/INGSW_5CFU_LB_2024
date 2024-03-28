package it.polimi.ingsw.model;

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
