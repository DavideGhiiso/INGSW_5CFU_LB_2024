package it.polimi.ingsw.networking;

import it.polimi.ingsw.events.EventReceiver;
import it.polimi.ingsw.events.data.BaseEvent;
import it.polimi.ingsw.controller.handlers.PingHandler;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Singleton class that contains all fundamental methods to handle a client
 */
public class Client extends Host {
    private static Client instance = null;
    private final EventReceiver eventReceiver;
    private Connection serverConnection;
    private Thread serverComunicationThread;
    public static final Logger LOGGER = Logger.getLogger(Client.class.getName());
    private Client() {
        eventReceiver = new EventReceiver();
        attachEventHandlers();
    }

    public static Client getInstance() {
        if(instance == null)
            instance = new Client();
        return instance;
    }

    /**
     * Starts the client by trying to connect to a server at passed address:port. If it connects successfully, it
     * then proceeds to start a EventListener thread and an EventReceiver thread
     * @param address server address
     * @param port server port
     */
    public void start(String address, int port) {
        Client.LOGGER.info("Starting client...");
        try {
            serverConnection = new Connection(address, port);
            Client.LOGGER.info("Connected to server!");
        } catch (IOException e) {
            Client.LOGGER.severe("Connection with server" + address + " on port " + port + " could not be established");
            throw new RuntimeException(e);
        }

        serverComunicationThread = new Thread(new EventListener(serverConnection, eventReceiver.getEventsQueue()), "EventListener");
        serverComunicationThread.start();
        new Thread(eventReceiver, "EventReceiver").start();
    }

    /**
     * Stops the Client by bringing it offline and by stopping all running threads
     */
    public void stop() {
        try {
            serverConnection.close();
        } catch (IOException e) {
            Client.LOGGER.severe("Connection with client hasn't closed properly");
            throw new RuntimeException(e);
        }
        serverComunicationThread.interrupt();
        eventReceiver.stop();
    }

    private void attachEventHandlers() {
        eventReceiver.attachEventHandler("PING_EVENT", new PingHandler());
    }
    public void send(BaseEvent event) {
        try {
            serverConnection.send(event);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
