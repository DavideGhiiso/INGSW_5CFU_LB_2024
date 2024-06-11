package it.polimi.ingsw.networking;

import it.polimi.ingsw.events.data.Event;
import it.polimi.ingsw.view.SceneLoader;
import javafx.application.Platform;

import java.io.IOException;
import java.util.Queue;

public class ClientEventListener extends EventListener implements Runnable {
    public ClientEventListener(Connection connection, Queue<Event> eventsQueue) {
        super(connection, eventsQueue);
    }

    @Override
    public void run() {
        try {
            super.run();
        } catch (IOException e) {
            System.out.println("Event Listener with "+ listeningConnection.getConnectionID() +" closed");
            if(e.getMessage().contains("Connection reset"))
                Platform.runLater(() -> SceneLoader.changeScene("fxml/unexpectedDisconnection.fxml"));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
