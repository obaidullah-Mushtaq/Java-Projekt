module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires junit;
    requires java.base;
    requires java.sql;

    opens at.htlleonding.java.projekt.game to javafx.fxml;
    exports at.htlleonding.java.projekt.game;
    exports controller;
    opens controller to javafx.fxml;
    exports Models to junit;
}