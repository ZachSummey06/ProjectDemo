package edu.utsa.cs3443.projectdemo;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MainAccountController {

    @FXML
    private Label firstNameLabel;

    @FXML
    private Label lastNameLabel;

    @FXML
    private Label usernameLabel;

    @FXML
    private Label passwordLabel;

    @FXML
    private Label favoriteSongLabel;

    @FXML
    private ImageView profileImageView;

    @FXML
    private ListView<String> listeningListView;

    public void loadDemoUser(String first, String last, String username, String password) {

        firstNameLabel.setText(first);
        lastNameLabel.setText(last);
        usernameLabel.setText(username);
        passwordLabel.setText(password);

        // demo favorite song
        if (username.equals("FreeThrowMerchant2025")) {
            favoriteSongLabel.setText("Listen List");
            listeningListView.getItems().addAll("Song 1", "Song 2", "Song 3");
        } else if (username.equals("Zach")) {
            favoriteSongLabel.setText("Listen List");
            listeningListView.getItems().addAll("Song 1", "Song 2", "Song 3");
        } else if (username.equals("C")) {
            favoriteSongLabel.setText("Listen List");
            listeningListView.getItems().addAll("Song 1", "Song 2");
        } else {
            favoriteSongLabel.setText("Listen List");
            listeningListView.getItems().addAll("Song 1", "Song 2");
        }

        // load pfp
        try {
            Image img = new Image(getClass().getResourceAsStream("/images/default.png"));
            profileImageView.setImage(img);
        } catch (Exception e) {
            System.out.println("No image found.");
        }
    }
}
