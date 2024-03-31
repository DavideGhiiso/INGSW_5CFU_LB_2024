module it.polimi.ingsw {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    exports it.polimi.ingsw.view;
    opens it.polimi.ingsw.view to javafx.fxml;
}