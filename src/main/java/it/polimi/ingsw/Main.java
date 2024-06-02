package it.polimi.ingsw;

import it.polimi.ingsw.view.View;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            View.main(args);
        } else if (args[0].equals("server"))
            ServerApp.main(args);
        else
            printHelp();
    }

    private static void printHelp() {
        System.out.println("Help: no arguments\n" +
                "\t no arguments\t start the client" +
                "\t server <port>\t starts the server");
    }
}
