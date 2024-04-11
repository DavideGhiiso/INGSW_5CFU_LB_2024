package it.polimi.ingsw.model.exceptions;

public class NonexistentPlayerException extends Exception {
    private String username;
    public NonexistentPlayerException(String username) {
        this.username = username;
    }
    @Override
    public String getMessage() {
        return "no Player with username '"+username+"' found in current game";
    }
}
