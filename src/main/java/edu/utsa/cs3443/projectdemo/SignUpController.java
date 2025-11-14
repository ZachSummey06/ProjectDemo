package edu.utsa.cs3443.projectdemo;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Node;

import java.io.FileWriter;
import java.io.IOException;

public class SignUpController {

    @FXML
    private PasswordField confirmPasswordTextField;

    @FXML
    private PasswordField enterPasswordTextField;

    @FXML
    private TextField firstNametextField;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private Button signUpButton;

    @FXML
    private TextField usernameTextField;

    @FXML
    void confirmSignUp(ActionEvent event) throws IOException {

        String username = usernameTextField.getText();
        String confirm = confirmPasswordTextField.getText();
        String firstName = firstNametextField.getText();
        String lastName = lastNameTextField.getText();
        String password = enterPasswordTextField.getText();

        if(username.isEmpty() || confirm.isEmpty() || firstName.isEmpty() || lastName.isEmpty()) {
            System.out.println("Please fill all the fields");
            return;
        }

        if(!password.equals(confirm)) {
            System.out.println("Passwords do not match");
            return;
        }

        //Change in the future!
        String newline = System.lineSeparator();
        FileWriter writer = new FileWriter("Data/users.csv", true);
        writer.write(firstName + "," + lastName + "," + username + "," + password + newline);
        writer.close();

        System.out.println("User created successfully");

        Parent root = FXMLLoader.load(getClass().getResource("RowdyRhythemes.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();

    }

}

