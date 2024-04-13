package it.polimi.ingsw.events.data;

import it.polimi.ingsw.networking.Connection;

/**
 * Event that requires a connection. It can decorate a Basic event or another ConnectionEvent
 */
public class ConnectionEvent extends Event {
    private final Event event;

    public ConnectionEvent(Event event, Connection connection) {
        this.event = event;
        this.connection = connection;
    }

    @Override
    public Connection getConnection() {
        return connection;
    }
    @Override
    public Event getEvent(){
        return event;
    }

    public String getID() {
        return event.getID();
    }
}
