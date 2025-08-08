package eaterymanagementapps;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;

public class CreateAccountController implements Initializable {

    @FXML private TextField su_username;
    @FXML private PasswordField su_password;
    @FXML private ComboBox<String> su_question;
    @FXML private TextField su_answer;
    @FXML private Button su_createbtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        su_question.getItems().addAll(
            "What is your pet's name?",
            "What is your mother's maiden name?",
            "What is your first school?",
            "What city were you born in?"
        );
    }

    @FXML
    private void handleSignUpAction(ActionEvent event) {
        // Optional: Validate input before returning
        if (su_username.getText().isEmpty() || su_password.getText().isEmpty() ||
            su_question.getValue() == null || su_answer.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Missing Fields", "Please complete all fields.");
            return;
        }

        // Optionally save account here (see next section)

        // Return to login screen
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginUI.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Login");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
