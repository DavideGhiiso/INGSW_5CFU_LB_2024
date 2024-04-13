package it.polimi.ingsw.events.data;

import it.polimi.ingsw.networking.Connection;

import java.io.Serializable;

/**
 * Class that represents an event that occurs inside the Client or the Server: it contains all data necessary to handle it.
 * Represents an ornament in the Decorator pattern
 */
public abstract class Event implements Serializable {
    protected String ID;
    protected Connection connection;
    protected boolean local = false;

    public String getID() {
        return ID;
    }
    public abstract Connection getConnection();
    public abstract Event getEvent();
    public boolean isLocal() {
        return local;
    }
    @Override
    public String toString() {
        return ID;
    }
}
