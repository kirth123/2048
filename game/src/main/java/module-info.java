module com.reddy.game {
    requires javafx.controls;
    requires javafx.fxml;
	requires javafx.graphics;
	requires javafx.base;

    opens com.reddy.game to javafx.fxml;
    exports com.reddy.game;
}
