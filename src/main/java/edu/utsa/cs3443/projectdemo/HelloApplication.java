package edu.utsa.cs3443.projectdemo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("RowdyRhythemes.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 440);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }
}
