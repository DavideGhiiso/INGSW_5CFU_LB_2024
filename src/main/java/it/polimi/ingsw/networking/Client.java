package it.polimi.ingsw.networking;

import it.polimi.ingsw.controller.handlers.client.*;
import it.polimi.ingsw.controller.handlers.server.ChangeBotDifficultyHandler;
import it.polimi.ingsw.events.EventReceiver;
import it.polimi.ingsw.events.data.BaseEvent;
import it.polimi.ingsw.events.data.GameInfo;
import it.polimi.ingsw.events.data.client.RequestGameInfoEvent;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * Singleton class that contains all fundamental methods to handle a client
 */
public class Client {
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
     * Method that tries to connect to a server at passed address:port. If it connects successfully, it
     * then proceeds to start a EventListener thread and an EventReceiver thread
     * @param address server address
     * @param port server port
     */
    public void connect(String address, int port) throws IOException {
        Client.LOGGER.info("Trying to connect to " + address + "...");
        serverConnection = new Connection(address, port);
        Client.LOGGER.info("Connected to server!");
        serverComunicationThread = new Thread(new ClientEventListener(serverConnection, eventReceiver.getEventsQueue()), "EventListener");
        serverComunicationThread.start();
        new Thread(eventReceiver, "EventReceiver").start();
        Client.LOGGER.info("Event listener and receiver started!");
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
        eventReceiver.attachEventHandler("JOIN_GAME_RESPONSE_EVENT", new JoinGameResponseHandler());
        eventReceiver.attachEventHandler("UPDATE_PLAYER_COUNT_EVENT", new UpdatePlayerCountHandler());
        eventReceiver.attachEventHandler("START_GAME_EVENT", new StartGameClientHandler());
        eventReceiver.attachEventHandler("HAND_CHANGED_EVENT", new HandChangedHandler());
        eventReceiver.attachEventHandler("TABLE_CHANGED_EVENT", new TableChangedHandler());
        eventReceiver.attachEventHandler("SCORE_EVENT", new ScoreEventHandler());
        eventReceiver.attachEventHandler("NEW_TURN_EVENT", new NewTurnEventHandler());
        eventReceiver.attachEventHandler("END_GAME_RESULTS_EVENT", new EndGameResultsHandler());
        eventReceiver.attachEventHandler("USERNAME_CHANGED_EVENT", new UsernameChangedHandler());
        eventReceiver.attachEventHandler("GAME_RESUME_EVENT", new GameResumeHandler());
        eventReceiver.attachEventHandler("GAME_RESUMING_WARNING_EVENT", new GameResumingWarningHandler());
        eventReceiver.attachEventHandler("CHANGE_BOT_DIFFICULTY_EVENT", new ChangeBotDifficultyHandler(true));
    }
    public void send(BaseEvent event) {
        try {
            serverConnection.send(event);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void requestInfo(GameInfo gameInfo) {
        send(new RequestGameInfoEvent(gameInfo));
    }
}
