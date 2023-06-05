package controllers;

import credentials.SQLConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class mainMenu implements Initializable {


    @FXML
    private Label bookNum;
    @FXML
    private Label authorNum;
    @FXML
    private Label customerNum;
    @FXML
    private Label publisherNum;
    @FXML
    private Label warehouseNum;

    @FXML
    private VBox pnItems = null;
    @FXML
    private Button btnOverview;

    @FXML
    private Button btnOrders;

    @FXML
    private Button btnCustomers;

    @FXML
    private Button btnMenus;

    @FXML
    private Button btnPackages;

    @FXML
    private Button btnSettings;

    @FXML
    private Button btnSignOut;

    @FXML
    private Pane pnlCustomer;

    @FXML
    private Pane pnlOrders;

    @FXML
    private Pane pnlOverview;

    @FXML
    private Pane pnlMenus;
    SQLConnection credentials = SQLConnection.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ZQRT", credentials.getUsername(), credentials.getPassword());
            String[] tablesList = {"book", "author", "publisher", "customer", "warehouse"};
            for(int i = 0; i < tablesList.length; i++){
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM "+ tablesList[i]);
            rs.next();
            if(i == 0)
                bookNum.setText(rs.getString(1));
            else if(i == 1)
                authorNum.setText(rs.getString(1));
            else if(i == 2)
                publisherNum.setText(rs.getString(1));
            else if(i == 3)
                customerNum.setText(rs.getString(1));
            else warehouseNum.setText(rs.getString(1));
            }

            Statement stmt = con.createStatement();
            Statement stmt2 = con.createStatement();
            ResultSet rsTemp= stmt2.executeQuery("SELECT count(*) FROM book");
            rsTemp.next();
            Integer bookAmount = Integer.parseInt(rsTemp.getString(1));
            ResultSet rs = stmt.executeQuery("SELECT ISBN, title,author.name, publisher.name,price, numberOfPages, stockAmount,releaseDate, genre From book\n" +
                    "inner join author on book.authorID = author.authorID\n" +
                    "inner join publisher on book.publisherID = publisher.publisherID\n" +
                    "order by author.authorID asc;");

            Node[] nodes = new Node[bookAmount];
            bookDefault[] bookDefaults = new bookDefault[bookAmount];
            for (int i = 0; rs.next(); i++) {
                final int j = i;
                URL url = getClass().getResource("/resources/FXML/bookDefault.fxml");
                assert url != null;
                nodes[i] = FXMLLoader.load(url);
                //set label texts from databse query
                bookDefaults[i] = (bookDefault) nodes[i].getUserData();
                bookDefaults[i].setISBN(rs.getString(1));
                bookDefaults[i].setTitle(rs.getString(2));
                bookDefaults[i].setAuthor(rs.getString(3));
                bookDefaults[i].setPublisher(rs.getString(4));
                bookDefaults[i].setPrice(rs.getString(5));
                bookDefaults[i].setPages(rs.getString(6));
                bookDefaults[i].setStock(rs.getString(7));
                bookDefaults[i].setReleaseDate(rs.getString(8));
                bookDefaults[i].setGenre(rs.getString(9));

                nodes[i].setOnMouseEntered(event -> nodes[j].setStyle("-fx-background-color : #121212"));
                nodes[i].setOnMouseExited(event -> nodes[j].setStyle("-fx-background-color : #2c2c2c"));
                pnItems.getChildren().add(nodes[i]);
            }
        } catch (SQLException | IOException e) {
            System.out.println("Error connecting to database: " + e);
            System.out.println("Please log out and log back in.");
        }
        Integer bookAmount = Integer.parseInt(bookNum.getText());
        Node[] nodes = new Node[bookAmount];
        for (int i = 0; i < nodes.length; i++) {
            try {
                final int j = i;
                URL url = getClass().getResource("/resources/FXML/bookDefault.fxml");
                assert url != null;
                nodes[i] = FXMLLoader.load(url);
                nodes[i].setOnMouseEntered(event -> nodes[j].setStyle("-fx-background-color : #121212"));
                nodes[i].setOnMouseExited(event -> nodes[j].setStyle("-fx-background-color : #2c2c2c"));
                pnItems.getChildren().add(nodes[i]);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    public void handleClicks(ActionEvent actionEvent) {
        if (actionEvent.getSource() == btnCustomers) {
            pnlCustomer.toFront();
        }
        if (actionEvent.getSource() == btnMenus) {
            pnlMenus.toFront();
        }
        if (actionEvent.getSource() == btnOverview) {
            pnlOverview.toFront();
        }
        if(actionEvent.getSource()==btnOrders)
        {
            pnlOrders.toFront();
        }
    }
}
