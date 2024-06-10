package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserDAO {

    public void saveUser(String firstName, String lastName) throws SQLException {
        String query = "INSERT INTO USERS (first_name, last_name) VALUES (?, ?)";

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.executeUpdate();
        }
    }
}
