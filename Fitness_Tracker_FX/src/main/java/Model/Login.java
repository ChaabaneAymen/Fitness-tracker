package Model;
import org.mindrot.jbcrypt.BCrypt;
import java.sql.*;

public class Login {
    private static String username;

    public static void setUsername(String user) {
        username = user;
    }

    public static String getUsername() {
        return username;
    }

private static final String DB_URL = "jdbc:sqlite:UserDB.sqlite";

public static String verifyLogin(String username, String plainPassword) {
    if (username == null || username.trim().isEmpty() || plainPassword == null || plainPassword.trim().isEmpty()) {
        return "empty_fields";
    }

    try (Connection conn = DriverManager.getConnection(DB_URL)) {
        String query = "SELECT password_hash FROM users WHERE username = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, username);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            String hashedPassword = rs.getString("password_hash");

            if (BCrypt.checkpw(plainPassword, hashedPassword)) {
                return "success";
            } else {
                return "wrong_credentials";
            }
        } else {
            return "wrong_credentials";
        }

    } catch (SQLException e) {
        e.printStackTrace();
        return "db_error";
    }
}}
