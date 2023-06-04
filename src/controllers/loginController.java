package controllers;

import Main.ZQRTApplication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class loginController {

    @FXML
    public Button button;
    @FXML
    public Label wrongLogIn;
    @FXML
    public TextField username;
    @FXML
    public PasswordField password;

    public void userLogIn(ActionEvent event) throws IOException {
        checkLogin();

    }

    private void checkLogin() throws IOException {
        ZQRTApplication m = new ZQRTApplication();
        try{
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ZQRT", username.getText(), password.getText());
            wrongLogIn.setText("Success!");
          //  System.exit(0);
        } catch (SQLException e) {
            if(username.getText().isEmpty() && password.getText().isEmpty()) {
                wrongLogIn.setText("Please enter your data.");
            }
            else {
                wrongLogIn.setText("Login Failed: Wrong username or password!");
            }
        }


          //  m.changeScene("loginPage.fxml");






    }
}
