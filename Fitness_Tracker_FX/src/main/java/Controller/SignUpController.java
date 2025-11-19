package Controller;


import Model.SignUp;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.sql.*;

public class SignUpController {

    @FXML
    private Label signInLabel;

    @FXML
    private ImageView logoImageView;

    @FXML
    private Button signInButton;

    @FXML
    private Button cancelButton;

    @FXML
    private TextField firstNameTextField;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField passwordConfirmationField;

    @FXML
    private Label messageLabel;

    @FXML


    private void showError(String message) {
        messageLabel.setTextFill(Color.RED);
        messageLabel.setText(message);
    }

    private void showSuccess(String message) {
        messageLabel.setTextFill(Color.GREEN);
        messageLabel.setText(message);
    }

    private void clearFields() {
        firstNameTextField.clear();
        lastNameTextField.clear();
        usernameTextField.clear();
        passwordField.clear();
        passwordConfirmationField.clear();
    }

    public void handleCancelB() {
        clearFields();
    }

    private static final String DB_URL = "jdbc:sqlite:UserDB.sqlite";

    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("SQLite JDBC driver not found", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(DB_URL);
        conn.setAutoCommit(true);
        createTables(conn);
        return conn;
    }

    private static void createTables(Connection conn) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS users (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "first_name TEXT NOT NULL," +
                "last_name TEXT NOT NULL," +
                "username TEXT UNIQUE NOT NULL," +
                "password_hash TEXT NOT NULL)";

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }
    public static boolean usernameExists(String username) {
        String sql = "SELECT 1 FROM users WHERE username = ?";
        try (Connection conn = SignUpController.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            return pstmt.executeQuery().next();
        } catch (SQLException e) {
            System.err.println("Error checking username: " + e.getMessage());
            return true;
        }
    }

    public static boolean isPasswordValid(String password) {
        return password != null && password.length() >= 8 &&
                password.matches(".*\\d.*") &&
                password.matches(".*[!@#$%^&*].*");
    }

    public static String createUser(SignUp user) {

        if (user.getFirstName() == null || user.getFirstName().trim().isEmpty() ||
                user.getLastName() == null || user.getLastName().trim().isEmpty()) {
            return "First and last name are required";
        }

        if (!isPasswordValid(user.getPasswordHash())) {
            return "Password must be 8+ chars with 1 number and 1 special character";
        }


        if (usernameExists(user.getUsername())) {
            return "Username already taken";
        }


        String sql = "INSERT INTO users (first_name, last_name, username, password_hash) VALUES (?, ?, ?, ?)";
        try (Connection conn = SignUpController.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getFirstName().trim());
            pstmt.setString(2, user.getLastName().trim());
            pstmt.setString(3, user.getUsername().trim());
            pstmt.setString(4, BCrypt.hashpw(user.getPasswordHash(), BCrypt.gensalt()));

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0 ? "success" : "Failed to create user";
        } catch (SQLException e) {
            return "Database error: " + e.getMessage();
        }
    }
    @FXML
    private void handleSignInB() {
        String firstName = firstNameTextField.getText().trim();
        String lastName = lastNameTextField.getText().trim();
        String username = usernameTextField.getText().trim();
        String password = passwordField.getText().trim();


        if (firstName.isEmpty() || lastName.isEmpty() || username.isEmpty() || password.isEmpty()) {
            showError("All fields are required!");
            return;
        }

        SignUp newUser = new SignUp(firstName, lastName, username, password);

        String result = SignUpController.createUser(newUser);

        if (result.equals("success")) {
            showSuccess("Registration successful!");
            clearFields();
        } else {
            showError(result);
        }

    }


}