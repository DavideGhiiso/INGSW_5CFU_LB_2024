package it.polimi.ingsw.events.data;

import it.polimi.ingsw.events.Response;

/**
 * Event sent by the server to notify the client about the status of his joining request
 */
public class JoinGameResponseEvent extends BaseEvent {
    private final Response response;
    public JoinGameResponseEvent(Response response) {
        ID = "JOIN_GAME_RESPONSE_EVENT";
        this.response = response;
    }

    /**
     * Method used client side to check if the client has been accepted in the game
     * @return {@code true} if the player has joined successfully, {@code false} otherwise
     */
    public Response getResponse() {
        return response;
    }
}
