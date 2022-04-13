module game.application {
    requires javafx.controls;
    requires javafx.fxml;

    opens game.application to javafx.fxml;
    exports game.application;
}