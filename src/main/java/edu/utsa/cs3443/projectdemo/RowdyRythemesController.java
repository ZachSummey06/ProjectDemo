package edu.utsa.cs3443.projectdemo;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import javafx.scene.Node;

import java.io.IOException;

public class RowdyRythemesController {

    private UserManager userManager;
    @FXML
    private PasswordField passwordTextField;

    @FXML
    private Button signInButton;

    @FXML
    private Button signUpButton;

    @FXML
    private TextField usernameTextField;

    @FXML
    private void initialize() {
        userManager = new UserManager();
        userManager.loadUsersFromFile();
    }

    @FXML
    private void handleSignInButtonAction(ActionEvent event) throws IOException {
        String username = usernameTextField.getText().trim();
        String password = passwordTextField.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            System.out.println("Username and password are empty");
            return;
        }

        User foundUser = userManager.findUser(username, password);

        if (foundUser != null) {
            System.out.println("Login successful! Welcome, " + foundUser.getFirstName());

            // Load MainAccount.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainAccount.fxml"));
            Parent root = loader.load();

            // Get the controller so we can pass user data
            MainAccountController controller = loader.getController();

            // Pass information to the controller
            controller.loadDemoUser(
                    foundUser.getFirstName(),
                    foundUser.getLastName(),
                    foundUser.getUsername(),
                    foundUser.getPassword()
            );

            // Switch scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        }
        else {
            System.out.println("Invalid username or password.");
        }
    }
    @FXML
    void goToSignUp(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("SignUpScreen.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

}
