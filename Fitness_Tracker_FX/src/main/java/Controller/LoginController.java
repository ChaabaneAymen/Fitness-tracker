package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import Model.Login;



public class LoginController {

    @FXML
    private Button cancelButton;

    @FXML
    private ImageView logoImageView;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button signInButton;

    @FXML
    private Label wrongusernameorpassworderrorlable;

    @FXML
    private Label fillthetextlable;

    @FXML
    private TextField usernameField;


    @FXML
    private void handleLogin(ActionEvent event) {


        fillthetextlable.setText("");
        wrongusernameorpassworderrorlable.setText("");

        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        String result = Login.verifyLogin(username, password);

        switch (result) {
            case "success":
                Login.setUsername(username);

                try {
                    Parent root = FXMLLoader.load(getClass().getResource("Main menu.fxml"));
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch (Exception e) {
                    e.printStackTrace();
                    showWrongError("Failed to load main menu.");
                }
                break;

            case "empty_fields":
                showFillError("Please fill in all fields.");
                break;

            case "wrong_credentials":
                showWrongError("Wrong username or password.");
                break;


        }

        System.out.println("Session username now is: " + Login.getUsername());

    }


    private void showFillError(String message) {
        fillthetextlable.setTextFill(Color.RED);
        fillthetextlable.setText(message);
    }

    private void showWrongError(String message) {
        wrongusernameorpassworderrorlable.setTextFill(Color.RED);
        wrongusernameorpassworderrorlable.setText(message);
    }

}

