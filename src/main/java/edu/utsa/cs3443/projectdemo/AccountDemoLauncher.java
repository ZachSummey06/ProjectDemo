package edu.utsa.cs3443.projectdemo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AccountDemoLauncher extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        // loads MainAccount.fxml screen
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MainAccount.fxml"));
        Scene scene = new Scene(loader.load(), 600, 600);

        MainAccountController controller = loader.getController();

        // loads demo user data
        controller.loadDemoUser(
                "DemoFirst",
                "DemoLast",
                "DemoUsername",
                "DemoPassword"
        );

        stage.setTitle("Main Account Demo");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
