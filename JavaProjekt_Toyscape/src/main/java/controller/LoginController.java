package controller;

import database.UserDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

public class LoginController {

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    private Stage stage;
    private boolean authenticated = false;

    @FXML
    private void handleLogin() {
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();

        if (firstName.isEmpty() || lastName.isEmpty()) {
            showAlert("Error", "Both fields must be filled!");
        } else {
            try {
                // Assuming saveUser() requires additional parameters like username and password
                UserDAO userDAO = new UserDAO();
                // Replace the following line with appropriate parameters
                userDAO.saveUser(firstName, lastName);
                authenticated = true;
                stage.close();
            } catch (SQLException e) {
                showAlert("Database Error", "Could not save user to the database.");
                e.printStackTrace(); // Consider logging the exception instead of printing stack trace
            }
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public boolean isAuthenticated() {
        return authenticated;
    }
}
