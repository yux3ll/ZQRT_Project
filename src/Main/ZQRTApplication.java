package Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;

public class ZQRTApplication extends Application {
    private double x, y;
    //TODO edit the item fxml according to sql queries(might need multiple fxmls for different items)
    //TODO add a new fxml for the add item page
    //TODO add a new fxml for the delete item page
    //TODO edit overview page to show the items when clicked
    //TODO edit mainMenu to add statistics page
    //TODO edit mainMenu to add settings page
    //TODO edit mainMenu to fix sign out page
    //TODO edit mainMenu to add a new page for miscellaneous


    @Override
    public void start(Stage primaryStage) throws Exception {
        URL url = getClass().getResource("/resources/FXML/logInPage.fxml");
        Parent root = FXMLLoader.load(url);
        primaryStage.setScene(new Scene(root));
        primaryStage.initStyle(StageStyle.UNDECORATED);
        root.setOnMousePressed(event -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });
        root.setOnMouseDragged(event -> {

            primaryStage.setX(event.getScreenX() - x);
            primaryStage.setY(event.getScreenY() - y);

        });
        primaryStage.show();
    }

    public void changeScene(Stage primaryStage, String fxml) throws IOException {
        URL url = getClass().getResource("/resources/FXML/" + fxml);
        Parent pane = FXMLLoader.load(url);
        primaryStage.getScene().setRoot(pane);
    }

    public static void main(String[] args) {
        launch(args);
    }



    public void changeSceneInStage(Stage primaryStage, URL url) throws Exception {

           Parent pane = FXMLLoader.load(url);
           primaryStage.getScene().setRoot(pane);
        pane.setOnMousePressed(event -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });
        pane.setOnMouseDragged(event -> {

            primaryStage.setX(event.getScreenX() - x);
            primaryStage.setY(event.getScreenY() - y);

        });


    }
}
/* Parent root = FXMLLoader.load(url);
        primaryStage.setScene(new Scene(root));
        primaryStage.initStyle(StageStyle.UNDECORATED);
        root.setOnMousePressed(event -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });
        root.setOnMouseDragged(event -> {

            primaryStage.setX(event.getScreenX() - x);
            primaryStage.setY(event.getScreenY() - y);

        });
        primaryStage.show();*/


