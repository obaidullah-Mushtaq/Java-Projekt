module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires junit;
    requires java.base;

    opens at.htlleonding.java.projekt.game to javafx.fxml;
    exports at.htlleonding.java.projekt.game;
    exports at.htlleonding.java.projekt.game.controller;
    opens at.htlleonding.java.projekt.game.controller to javafx.fxml;
    exports at.htlleonding.java.projekt.game.Models to junit;
}