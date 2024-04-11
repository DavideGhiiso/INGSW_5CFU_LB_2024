package it.polimi.ingsw.networking;

import it.polimi.ingsw.controller.handlers.ClientDisconnectedHandler;
import it.polimi.ingsw.events.data.ConnectionEvent;
import it.polimi.ingsw.events.data.PingEvent;
import it.polimi.ingsw.events.data.client.ClientDisconnectedEvent;

import java.io.IOException;
import java.util.TimerTask;

/**
 * Timer task that sends a PingEvent to passed connection
 * @see NetworkListener
 */
public class PingSender extends TimerTask {
    /**
     * Delay interval in ms to execute a ping
     */
    public final static int PING_PERIOD = 2000;
    private final Connection connection;
    public PingSender(Connection connection) {
        this.connection = connection;
    }
    @Override
    public void run() {
        try {
            connection.send(new PingEvent());
            connection.decrementPingFailure();

            if(connection.isFailed()) {
                new ClientDisconnectedHandler().handle(new ConnectionEvent(new ClientDisconnectedEvent(), connection));
            }

        } catch (IOException e) {
            cancel();
        }
    }
}
