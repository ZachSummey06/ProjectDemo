package edu.utsa.cs3443.projectdemo.controller;
import edu.utsa.cs3443.projectdemo.MainAccountController;
import edu.utsa.cs3443.projectdemo.User;
import edu.utsa.cs3443.projectdemo.UserManager;
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

public class LoginController {

    private UserManager userManager;
    @FXML
    private PasswordField passwordField;

    @FXML
    private Button signInButton;

    @FXML
    private Button signUpButton;

    @FXML
    private TextField usernameField;

    @FXML
    private void initialize() {
        userManager = new UserManager();
        userManager.loadUsersFromFile();
    }

    @FXML
    private void handleSignInButtonAction(ActionEvent event) throws IOException {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            System.out.println("Username and password are empty");
            return;
        }

        User foundUser = userManager.findUser(username, password);

        if (foundUser != null) {
            System.out.println("Login successful! Welcome, " + foundUser.getFirstName());

            // Go up one level from controller package to projectdemo
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/edu/utsa/cs3443/projectdemo/main-account.fxml"));
            Parent root = loader.load();

            MainAccountController controller = loader.getController();
            controller.loadDemoUser(
                    foundUser.getFirstName(),
                    foundUser.getLastName(),
                    foundUser.getUsername(),
                    foundUser.getPassword()
            );

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 800, 800);
            stage.setScene(new Scene(root));
            stage.show();
        }
        else {
            System.out.println("Invalid username or password.");
        }
    }

    @FXML
    void goToSignUp(ActionEvent event) throws IOException {
        // Go up one level from controller package to projectdemo
        Parent root = FXMLLoader.load(getClass().getResource("/edu/utsa/cs3443/projectdemo/sign-up.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }



}
