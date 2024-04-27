package it.polimi.ingsw.view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

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
