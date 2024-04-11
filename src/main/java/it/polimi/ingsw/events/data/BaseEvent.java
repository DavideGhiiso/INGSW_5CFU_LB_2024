package it.polimi.ingsw.events.data;

import it.polimi.ingsw.networking.Connection;

/**
 * Base class in pattern Decorator. Represent an event that is not decorated.
 * By default, a BaseEvent object is not decorated unless the {@code requiresConnection} is set to true
 */
public abstract class BaseEvent extends Event {
    protected boolean requiresConnection = false;

    @Override
    public Connection getConnection() {
        return null;
    }

    public boolean requiresConnection() {
        return requiresConnection;
    }
}
