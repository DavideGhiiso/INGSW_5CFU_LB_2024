package it.polimi.ingsw;

import it.polimi.ingsw.controller.OnlineGameController;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.networking.Server;

public class ServerApp {
    public static void main(String[] args) {
        Server server;
        if(args.length == 2)
            server = Server.getInstance(Integer.parseInt(args[0]));
        else
            server = Server.getInstance();
        server.start();
    }
}
