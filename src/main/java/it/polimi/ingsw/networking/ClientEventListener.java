package it.polimi.ingsw.networking;

import it.polimi.ingsw.events.data.Event;

import java.io.IOException;
import java.util.Queue;

public class ClientEventListener extends EventListener implements Runnable {
    public ClientEventListener(Connection connection, Queue<Event> eventsQueue) {
        super(connection, eventsQueue);
    }

    @Override
    public void run() {
        try {
            super.run();
        } catch (IOException e) {
            System.out.println("Error: "+e.getMessage());
            // TODO: logging purpose (remove b4 release)
            System.out.println("Event Listener with "+ listeningConnection.getConnectionID() +" closed");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
