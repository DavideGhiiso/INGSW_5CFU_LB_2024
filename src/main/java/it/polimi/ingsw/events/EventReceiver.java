package it.polimi.ingsw.events;

import it.polimi.ingsw.events.data.Event;

import java.util.*;

/**
 * Class that receives the Events from the socket through a Queue and sort them to the appropriate EventHandler.
 * @see it.polimi.ingsw.networking.EventListener
 */
public class EventReceiver implements Runnable {
    public final Map<String, EventHandler> eventHandlerMap;
    private final Queue<Event> eventsQueue;
    private boolean stop = false;

    public EventReceiver() {
        this.eventHandlerMap = new HashMap<>();
        this.eventsQueue = new LinkedList<>();
    }

    public Queue<Event> getEventsQueue() {
        return eventsQueue;
    }

    /**
     * Method that attaches an EventHandler to this receiver
     * @param eventID ID of event to add
     * @param eventHandler EventHandler that contains the correct method to handle the Event with passed ID
     */
    public void attachEventHandler(String eventID,EventHandler eventHandler) {
        eventHandlerMap.put(eventID, eventHandler);
    }

    /**
     * Method used to send passed event to the correct EventHandler
     * @param event Event to execute
     */
    private synchronized void sortEvent(Event event) {
        if(eventHandlerMap.containsKey(event.getID()))
            try {
                eventHandlerMap.get(event.getID()).handle(event);
            } catch (NullPointerException e) {
                System.out.println("Handler for " + event.getID() + " is null!");
            }
    }

    /**
     * Method that allows the reception of events. The thread sleeps until an Event arrives: when an Event arrives, the
     * EventReceiver is woken up and can receive the event by reading it from the {@code eventsQueue}.
     */
    @Override
    public void run() {
        stop = false;
        while (true) {
            while(eventsQueue.isEmpty()) {
                if(stop)
                    return;
                try {
                    synchronized (EventReceiver.class) {
                        EventReceiver.class.wait();
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            Event receivedEvent;
            // event received
            synchronized (eventsQueue) {
                receivedEvent = eventsQueue.remove();
            }
            // TODO: remove println
            System.out.println("Received " + receivedEvent.getID());
            sortEvent(receivedEvent);
        }
    }

    /**
     * Method used to stop the EventReceiver
     */
    public synchronized void stop() {
        this.stop = true;
        eventsQueue.clear();
        synchronized (EventReceiver.class) {
            EventReceiver.class.notifyAll();
        }
    }
}
