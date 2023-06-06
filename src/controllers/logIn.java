package controllers;

import Main.ZQRTApplication;
import dataCarrier.SQLConnection;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.DriverManager;
import java.sql.SQLException;


public class logIn {

    @FXML
    public Button button;
    @FXML
    public Label wrongLogIn;
    @FXML
    public TextField username;
    @FXML
    public PasswordField password;

    SQLConnection credentials = SQLConnection.getInstance();

    public void userLogIn() {
        checkLogin();

    }

    private void checkLogin() {
        try {
            credentials.resetCredentials();
            credentials.setUsername(username.getText());
            credentials.setPassword(password.getText());
            DriverManager.getConnection("jdbc:mysql://localhost:3306/ZQRT", credentials.getUsername(), credentials.getPassword());
            wrongLogIn.setText("Success!");
            URL url = getClass().getResource("/resources/FXML/mainMenu.fxml");
            Stage stage = (Stage) button.getScene().getWindow();
            ZQRTApplication z = new ZQRTApplication();
            z.changeSceneInStage(stage, url);
        } catch (SQLException e) {
            if (username.getText().isEmpty() && password.getText().isEmpty()) {
                wrongLogIn.setText("Please enter your credentials.");
            } else {
                wrongLogIn.setText("Login Failed: Wrong username or password!");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void closeApplication(){
        System.exit(0);
    }
}