package Controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.awt.event.MouseEvent;
import java.io.IOException;

public class HomePageController {

        @FXML
        private Button CreateWorkoutButton;

        @FXML
        private Button ProfileButton;

        @FXML
        private Button ProgressButton;

        @FXML
        private Button TrackCaloriesButton;


        @FXML
        private void handleCreateWorkoutRoutine(ActionEvent event) {
            try {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("WorkoutRoutine.fxml"));
                Parent root = loader.load();

                Stage stage = (Stage) CreateWorkoutButton.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    @FXML
    private void handleProfileButton (ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("Profile.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ProfileButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleProgressButton(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("Progress.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ProgressButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleTrackCaloriesButton(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("Calories.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) TrackCaloriesButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }













    }

