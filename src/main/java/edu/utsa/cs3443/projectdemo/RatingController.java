package edu.utsa.cs3443.projectdemo;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class RatingController {

    // Form Fields
    @FXML private TextField usernameField;
    @FXML private TextField songNameField;
    @FXML private TextField albumNameField;
    @FXML private TextField artistNameField;
    @FXML private Spinner<Integer> ratingSpinner;
    @FXML private TextArea commentsArea;
    @FXML private Label statusLabel;

    // Table and Search
    @FXML private TableView<SongRating> ratingsTable;
    @FXML private TableColumn<SongRating, String> usernameColumn;
    @FXML private TableColumn<SongRating, String> songColumn;
    @FXML private TableColumn<SongRating, String> albumColumn;
    @FXML private TableColumn<SongRating, String> artistColumn;
    @FXML private TableColumn<SongRating, Integer> ratingColumn;
    @FXML private TableColumn<SongRating, String> commentsColumn;
    @FXML private TextField searchField;
    @FXML private Label recordCountLabel;

    // Data Manager
    private RatingManager ratingManager;
    private ObservableList<SongRating> ratingsObservableList;

    /**
     * Initialize the controller - called automatically after FXML is loaded
     */
    @FXML
    public void initialize() {
        // Initialize the rating manager
        ratingManager = new RatingManager();
        ratingsObservableList = FXCollections.observableArrayList();

        // Set up table columns
        setupTableColumns();

        // Load initial data
        loadAllRatings();

        // Clear any status messages
        statusLabel.setText("");
    }

    /**
     * Configure table columns to display SongRating properties
     */
    private void setupTableColumns() {
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        songColumn.setCellValueFactory(new PropertyValueFactory<>("songName"));
        albumColumn.setCellValueFactory(new PropertyValueFactory<>("albumName"));
        artistColumn.setCellValueFactory(new PropertyValueFactory<>("artistName"));
        ratingColumn.setCellValueFactory(new PropertyValueFactory<>("rating"));
        commentsColumn.setCellValueFactory(new PropertyValueFactory<>("comments"));

        // Make comments column wrap text
        commentsColumn.setCellFactory(tc -> {
            TableCell<SongRating, String> cell = new TableCell<>();
            Text text = new Text();
            cell.setGraphic(text);
            cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
            text.wrappingWidthProperty().bind(commentsColumn.widthProperty());
            text.textProperty().bind(cell.itemProperty());
            return cell;
        });

        ratingsTable.setItems(ratingsObservableList);
    }

    /**
     * Load all ratings from file and display in table
     */
    private void loadAllRatings() {
        ratingManager.loadRatingsFromFile();
        ArrayList<SongRating> allRatings = ratingManager.getAllRatings();
        ratingsObservableList.clear();
        ratingsObservableList.addAll(allRatings);
        updateRecordCount();
    }

    /**
     * Handle Add Rating button click
     */
    @FXML
    private void handleAddRating() {
        // Validate inputs
        if (!validateInputs()) {
            return;
        }

        // Get values from form
        String username = usernameField.getText().trim();
        String songName = songNameField.getText().trim();
        String albumName = albumNameField.getText().trim();
        String artistName = artistNameField.getText().trim();
        int rating = ratingSpinner.getValue();
        String comments = commentsArea.getText().trim();

        // Create new rating
        SongRating newRating = new SongRating(username, songName, albumName,
                artistName, rating, comments);

        // Save to file
        ratingManager.saveRating(newRating);

        // Add to table
        ratingsObservableList.add(newRating);

        // Clear form
        clearForm();

        // Show success message
        showStatus("✓ Rating added successfully!", true);
        updateRecordCount();
    }

    /**
     * Validate form inputs before saving
     */
    private boolean validateInputs() {
        if (usernameField.getText().trim().isEmpty()) {
            showStatus("⚠ Please enter a username", false);
            usernameField.requestFocus();
            return false;
        }

        if (songNameField.getText().trim().isEmpty()) {
            showStatus("⚠ Please enter a song name", false);
            songNameField.requestFocus();
            return false;
        }

        if (albumNameField.getText().trim().isEmpty()) {
            showStatus("⚠ Please enter an album name", false);
            albumNameField.requestFocus();
            return false;
        }

        if (artistNameField.getText().trim().isEmpty()) {
            showStatus("⚠ Please enter an artist name", false);
            artistNameField.requestFocus();
            return false;
        }

        return true;
    }

    /**
     * Handle Clear Form button click
     */
    @FXML
    private void handleClearForm() {
        clearForm();
        showStatus("Form cleared", true);
    }

    /**
     * Clear all form fields
     */
    private void clearForm() {
        usernameField.clear();
        songNameField.clear();
        albumNameField.clear();
        artistNameField.clear();
        ratingSpinner.getValueFactory().setValue(5);
        commentsArea.clear();
        usernameField.requestFocus();
    }

    /**
     * Handle Search button click - filter by username
     */
    @FXML
    private void handleSearchUser() {
        String searchUsername = searchField.getText().trim();

        if (searchUsername.isEmpty()) {
            showStatus("⚠ Please enter a username to search", false);
            return;
        }

        ArrayList<SongRating> userRatings = ratingManager.getRatingsByUser(searchUsername);
        ratingsObservableList.clear();
        ratingsObservableList.addAll(userRatings);

        if (userRatings.isEmpty()) {
            showStatus("No ratings found for user: " + searchUsername, false);
        } else {
            showStatus("Found " + userRatings.size() + " rating(s) for: " + searchUsername, true);
        }
        updateRecordCount();
    }

    /**
     * Handle Show All button click - display all ratings
     */
    @FXML
    private void handleShowAll() {
        loadAllRatings();
        searchField.clear();
        showStatus("Showing all ratings", true);
    }

    /**
     * Handle Refresh button click - reload from file
     */
    @FXML
    private void handleRefresh() {
        loadAllRatings();
        searchField.clear();
        showStatus("Data refreshed from file", true);
    }

    /**
     * Display a status message to the user
     */
    private void showStatus(String message, boolean isSuccess) {
        statusLabel.setText(message);
        if (isSuccess) {
            statusLabel.setStyle("-fx-text-fill: #2d8e2d; -fx-font-weight: bold;");
        } else {
            statusLabel.setStyle("-fx-text-fill: #d32f2f; -fx-font-weight: bold;");
        }

        // Clear message after 3 seconds
        new Thread(() -> {
            try {
                Thread.sleep(3000);
                javafx.application.Platform.runLater(() -> statusLabel.setText(""));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * Update the record count label
     */
    private void updateRecordCount() {
        recordCountLabel.setText("Total Ratings: " + ratingsObservableList.size());
    }
}