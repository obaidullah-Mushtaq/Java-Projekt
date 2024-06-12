package at.htlleonding.java.projekt.game;

import controller.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        if (showLoginDialog()) {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Hello!");
            stage.setScene(scene);
            stage.show();
        } else {
            System.exit(0);
        }
    }
    private boolean showLoginDialog() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login-view.fxml"));
        Stage loginStage = new Stage();
        loginStage.initModality(Modality.APPLICATION_MODAL);
        loginStage.setTitle("Login");
        loginStage.setScene(new Scene(loader.load()));

        LoginController controller = loader.getController();
        controller.setStage(loginStage);
        loginStage.showAndWait();

        return controller.isAuthenticated();
    }

    public static void main(String[] args) {
        launch();
    }
}
