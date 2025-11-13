package edu.utsa.cs3443.projectdemo;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
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

        if(username.isEmpty() || password.isEmpty()) {
            System.out.println("Username and password are empty");
        }
        User foundUser = userManager.findUser(username, password);
        if(foundUser != null) {
            System.out.println("Login successful! Welcome, "  + foundUser.getFirstName());
        }

    }

}
