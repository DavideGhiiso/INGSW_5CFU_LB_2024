module org.example.ingsw5cfu {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens org.example.ingsw5cfu to javafx.fxml;
}