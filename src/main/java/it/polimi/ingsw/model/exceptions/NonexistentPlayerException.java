package it.polimi.ingsw.model.exceptions;

/**
 * Exception thrown when a non-existent player is searched
 */
public class NonexistentPlayerException extends Exception {
    private final String username;
    public NonexistentPlayerException(String username) {
        this.username = username;
    }
    @Override
    public String getMessage() {
        return "no Player with username '"+username+"' found in current game";
    }
}
