package it.polimi.ingsw;

import it.polimi.ingsw.networking.Server;

/**
 * Server entry point
 */
public class ServerApp {
    public static void main(String[] args) {
        Server server;
        switch (args.length) {
            case 2: {
                server = Server.getInstance(Integer.parseInt(args[0]));
                break;
            }
            case 3: {
                Server.DEBUG = true;
            }
            default: {
                server = Server.getInstance();
            }
        }
        server.start();
    }
}
