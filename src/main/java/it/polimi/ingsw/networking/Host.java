package it.polimi.ingsw.networking;

import it.polimi.ingsw.controller.GameController;

public abstract class Host {
    public static GameController currentGameControllerInstance = null;
    public static GameController getGameControllerInstance() {
        return currentGameControllerInstance;
    }
}
