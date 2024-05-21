package it.polimi.ingsw.networking;

import it.polimi.ingsw.controller.handlers.server.*;
import it.polimi.ingsw.events.EventReceiver;
import it.polimi.ingsw.events.EventTransmitter;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.logging.Logger;
import java.util.Map;

/**
 * Singleton class that contains all fundamental methods to handle a server
 * TODO: maybe bring back connected clients as List if is not necessary to close the Threads
 */
public class Server extends Host {
    /**
     * Singleton server instance
     */
    private static Server instance = null;
    private final int port;
    private final ServerSocket welcomeSocket;
    private final NetworkListener networkListener;
    /**
     * map between clients and their dedicated thread
     */
    private final Map<Connection, Thread> connectedClients;
    private final EventTransmitter eventTransmitter;
    private final EventReceiver eventReceiver;

    public static final Logger LOGGER = Logger.getLogger(Server.class.getName());
    public static final int DEFAULT_PORT = 12000;

    private Server(int port) {
        connectedClients = new HashMap<>();
        eventReceiver = new EventReceiver();
        eventTransmitter = new EventTransmitter(connectedClients);
        attachEventHandlers();
        this.port = port;
        networkListener = new NetworkListener(this);
        try {
            welcomeSocket = new ServerSocket(port);
        } catch (IOException e) {
            Server.LOGGER.severe("Could not instantiate server on port " + port);
            throw new RuntimeException();
        }
    }

    /**
     * Returns the Singleton instance of this Server
     * @param port port on which the server will start
     * @return the Singleton server instance
     */
    public static Server getInstance(int port) {
        if(instance == null)
            instance = new Server(port);
        return instance;
    }

    public static Server getInstance() {
        if(instance == null)
            instance = new Server(Server.DEFAULT_PORT);
        return instance;
    }

    /**
     * Method that starts the server by starting to listen for incoming clients connections with the NetworkListener
     * and for incoming events with the EventReceiver
     */
    public void start() {
        new Thread(networkListener, "NetworkListener").start();
        new Thread(eventReceiver, "EventReceiver").start();
        Server.LOGGER.info("Server started on port " + port);
    }

    /**
     * Stops the server by closing all connections and by stopping all running threads
     */
    public void stop() {
        try {
            for(Connection connection: connectedClients.keySet()) {
                connection.close();
            }
            for(Thread thread: connectedClients.values()) {
                thread.interrupt();
            }
            eventReceiver.stop();
            welcomeSocket.close();
            Server.LOGGER.info("Server stopped correctly.");
        } catch (IOException e) {
            Server.LOGGER.severe("Server didn't stop correctly. " +
                    "There might still be some active threads or open connections:\n".concat(e.getMessage()));
        }
    }

    private void attachEventHandlers() {
        eventReceiver.attachEventHandler("PONG_EVENT", new PongHandler());
        eventReceiver.attachEventHandler("JOIN_GAME_EVENT", new JoinGameHandler());
        eventReceiver.attachEventHandler("JOIN_ONGOING_GAME_EVENT", new JoinOnGoingGameHandler());
        eventReceiver.attachEventHandler("CLIENT_DISCONNECTED_EVENT", new ClientDisconnectedHandler());
        eventReceiver.attachEventHandler("REQUEST_GAME_INFO_EVENT", new RequestGameInfoHandler());
        eventReceiver.attachEventHandler("START_GAME_EVENT", new StartGameServerHandler());
        eventReceiver.attachEventHandler("PLACE_CARD_EVENT", new PlaceCardHandler());
        eventReceiver.attachEventHandler("CONTINUE_GAME_EVENT", new ContinueGameHandler());
        eventReceiver.attachEventHandler("CHANGE_BOT_DIFFICULTY_EVENT", new ChangeBotDifficultyHandler(false));
    }

    public ServerSocket getWelcomeSocket() {
        return welcomeSocket;
    }
    public synchronized void addClient(Connection connection, Thread thread) {
        connectedClients.put(connection, thread);
    }
    public synchronized void removeClient(Connection connection) {
        if(connectedClients.containsKey(connection))
            connectedClients.get(connection).interrupt();
        connectedClients.remove(connection);
    }
    public EventReceiver getEventReceiver() {
        return eventReceiver;
    }
    public EventTransmitter getEventTransmitter() {return eventTransmitter;}



}
