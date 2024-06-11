package it.polimi.ingsw.networking;

import it.polimi.ingsw.events.data.BaseEvent;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Class that handles a Connection between two hosts at socket level
 */
public class Connection {
    private final Socket foreignHostSocket;
    private String connectionID;
    private final ObjectInputStream inputStream;
    private final ObjectOutputStream outputStream;
    private static final int TIMEOUT = 2000;
    private final int PING_FAILURE_THRESHOLD = 5;
    private int currentPingFailure = PING_FAILURE_THRESHOLD;


    /**
     * Used to create a new Client side connection
     * @param address address to connect to
     * @param port port to connect to
     */
    public Connection(String address, int port) throws IOException {
        this.foreignHostSocket = new Socket();
        foreignHostSocket.connect(new InetSocketAddress(address, port), TIMEOUT);
        outputStream = new ObjectOutputStream(foreignHostSocket.getOutputStream());
        inputStream = new ObjectInputStream(foreignHostSocket.getInputStream());
        connectionID = "server";
    }

    /**
     * Used to create a new Server side connection
     */
    public Connection(Socket socket) throws IOException {
        this.foreignHostSocket = socket;
        outputStream = new ObjectOutputStream(foreignHostSocket.getOutputStream());
        inputStream = new ObjectInputStream(foreignHostSocket.getInputStream());
    }

    /**
     * Method that allows to send an Event through this Connection socket
     * @param event an Event object to send
     */
    public void send(BaseEvent event) throws IOException {
        synchronized (outputStream) {
            outputStream.writeObject(event);
            outputStream.flush();
            outputStream.reset();
        }
    }

    /**
     * Method that allows to receive an Event from the socket
     * @return an Event received through the Socket
     */
    public BaseEvent receive() throws IOException, ClassNotFoundException {
        return (BaseEvent) inputStream.readObject();
    }

    public void close() throws IOException {
        synchronized (outputStream) {
            outputStream.close();
        }
        inputStream.close();
        foreignHostSocket.close();
    }

    public void decrementPingFailure() {
        currentPingFailure--;
    }

    public void resetPingFailure() {
        currentPingFailure = PING_FAILURE_THRESHOLD;
    }
    public boolean isFailed() {
        return currentPingFailure == 0;
    }

    public String getConnectionID() {
        return connectionID;
    }

    public void setConnectionID(String connectionID) {
        this.connectionID = connectionID;
    }

    @Override
    public String toString() {
        return connectionID;
    }
}
