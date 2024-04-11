package it.polimi.ingsw.networking;

import it.polimi.ingsw.events.EventReceiver;
import it.polimi.ingsw.events.data.BaseEvent;
import it.polimi.ingsw.events.data.ConnectionEvent;
import it.polimi.ingsw.events.data.Event;

import java.io.IOException;
import java.util.Queue;

/**
 * Runnable class that listens for a connection and adds every Event that arrives to the passedQueue
 */
public class EventListener implements Runnable {
    /**
     * Connection that the class listens to
     */
    private final Connection listeningConnection;
    private final Queue<Event> eventsQueue;
    public EventListener(Connection connection, Queue<Event> eventsQueue) {
        this.listeningConnection = connection;
        this.eventsQueue = eventsQueue;
    }


    @Override
    public void run() {
        BaseEvent receivedBaseEvent;
        Event receivedEvent;
        while (true) {
            try {
                receivedBaseEvent = listeningConnection.receive();
            } catch (IOException e) {
                // TODO: logging purpose (remove b4 release)
                System.out.println("Event Listener with "+ listeningConnection.getConnectionID() +" closed");
                break;
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

            if(receivedBaseEvent.requiresConnection())
                receivedEvent = new ConnectionEvent(receivedBaseEvent, listeningConnection);
            else
                receivedEvent = receivedBaseEvent;

            synchronized (eventsQueue) {
                eventsQueue.add(receivedEvent);
            }
            synchronized (EventReceiver.class) {
                EventReceiver.class.notifyAll();
            }
        }
    }
}
