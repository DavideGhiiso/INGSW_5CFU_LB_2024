package it.polimi.ingsw.view;

import it.polimi.ingsw.view.viewcontroller.ViewController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Class that contains all static method to set and swap stages
 */
public class SceneLoader {
    private static Stage currentStage = new Stage();
    private static ViewController controller;
    public static Scene currentScene;
    private static PlayerView playerView;

    public static void initialize() {
        playerView = new PlayerView();
    }

    /**
     * Draws the first Stage that represents the game menu
     * @param stage menu Stage
     * @param sceneFXML menu FXML scene to load
     */
    public static void drawMenu(Stage stage, String sceneFXML) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(View.class.getResource(sceneFXML));

        Scene scene = new Scene(fxmlLoader.load());
        controller = fxmlLoader.getController();
        currentStage = stage;
        currentScene = scene;
        stage.setScene(scene);
    }

    public static ViewController getCurrentController() {
        return controller;
    }

    public static PlayerView getPlayerView() {
        return playerView;
    }

    /**
     * Changes the scene to the passed FXML scene
     * @param sceneFXML the Scene to load
     */
    public static void changeScene(String sceneFXML) {
        FXMLLoader fxmlLoader = new FXMLLoader(View.class.getResource(sceneFXML));
        try {
            Parent root = fxmlLoader.load();
            controller = fxmlLoader.getController();
            currentScene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        currentStage.setOnCloseRequest(event -> System.exit(0));
        currentStage.show();
    }

}
