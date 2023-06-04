package Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class ZQRTApplication extends Application {
    private static Stage stg;

    @Override
    public void start(Stage primaryStage) throws Exception {
        stg = primaryStage;
        primaryStage.setResizable(true);
        URL url = getClass().getResource("/resources/FXML/loginPage.fxml");
        Parent root = FXMLLoader.load(url);
        primaryStage.setTitle("Falcon");
        primaryStage.setScene(new Scene(root, 1280, 720));
        primaryStage.show();
    }

    public void changeScene(String fxml) throws IOException {
        URL url = getClass().getResource("/resources/FXML/"+ fxml);
        Parent pane = FXMLLoader.load(url);
        stg.getScene().setRoot(pane);
    }


    public static void main(String[] args) {
        launch(args);
    }
}