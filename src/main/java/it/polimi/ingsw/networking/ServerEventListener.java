package it.polimi.ingsw.networking;

import it.polimi.ingsw.controller.handlers.server.ClientDisconnectedHandler;
import it.polimi.ingsw.events.data.ConnectionEvent;
import it.polimi.ingsw.events.data.Event;
import it.polimi.ingsw.events.data.server.ClientDisconnectedEvent;

import java.io.IOException;
import java.util.Queue;

public class ServerEventListener extends EventListener implements Runnable {
    public ServerEventListener(Connection connection, Queue<Event> eventsQueue) {
        super(connection, eventsQueue);
    }

    @Override
    public void run() {
        try {
            super.run();
        } catch (IOException e) {
            new ClientDisconnectedHandler().handle(new ConnectionEvent(new ClientDisconnectedEvent(), listeningConnection));
            System.out.println("Event Listener with "+ listeningConnection.getConnectionID() +" closed");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
