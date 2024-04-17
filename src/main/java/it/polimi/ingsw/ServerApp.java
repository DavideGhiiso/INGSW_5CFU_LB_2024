package it.polimi.ingsw;

import it.polimi.ingsw.controller.OnlineGameController;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.networking.Server;

public class ServerApp {
    public static void main(String[] args) {
        Server server = Server.getInstance();
        server.start();
    }
}
