package it.polimi.ingsw.networking;

import it.polimi.ingsw.events.EventReceiver;
import it.polimi.ingsw.events.data.BaseEvent;
import it.polimi.ingsw.events.data.ConnectionEvent;
import it.polimi.ingsw.events.data.Event;

import java.io.IOException;
import java.io.StreamCorruptedException;
import java.util.Queue;

/**
 * Abstract class that listens for a connection and adds every Event that arrives to the passedQueue
 */
public abstract class EventListener {
    /**
     * Connection that the class listens to
     */
    protected final Connection listeningConnection;
    /**
     * Queue used to store received events
     */
    protected final Queue<Event> eventsQueue;
    protected EventListener(Connection connection, Queue<Event> eventsQueue) {
        this.listeningConnection = connection;
        this.eventsQueue = eventsQueue;
    }

    protected void run() throws IOException, ClassNotFoundException {
        BaseEvent receivedBaseEvent;
        Event receivedEvent;
        while (true) {
            try {
                receivedBaseEvent = listeningConnection.receive();
            } catch (StreamCorruptedException e) {
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
