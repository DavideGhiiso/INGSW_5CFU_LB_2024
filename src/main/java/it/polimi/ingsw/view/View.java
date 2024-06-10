package it.polimi.ingsw.view;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Entry point of the client
 */
public class View extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Scopone");
        SceneLoader.initialize();
        SceneLoader.drawMenu(primaryStage, "fxml/menu.fxml");
        primaryStage.show();
    }
}
