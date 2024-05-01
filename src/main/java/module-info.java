module it.polimi.ingsw {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.logging;
    exports it.polimi.ingsw.view;
    exports it.polimi.ingsw.view.viewcontroller;
    exports it.polimi.ingsw.events.data;
    opens it.polimi.ingsw.view.viewcontroller to javafx.fxml;
    exports it.polimi.ingsw.events.data.server;
}