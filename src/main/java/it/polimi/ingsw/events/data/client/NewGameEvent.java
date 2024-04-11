package it.polimi.ingsw.events.data.client;

import it.polimi.ingsw.events.data.BaseEvent;

/**
 * Class Event that represent the creation of a game by a player. It contains the new game name
 */
public class NewGameEvent extends BaseEvent {
    /**
     * name of new game, decided by the player. It's the name that will be displayed in the menu
     */
    private final String gameName;
    public NewGameEvent(String gameName) {
        this.ID = "NEW_GAME_EVENT";
        this.gameName = gameName;
        requiresConnection = true;
    }

    public String getGameName() {
        return gameName;
    }
}
