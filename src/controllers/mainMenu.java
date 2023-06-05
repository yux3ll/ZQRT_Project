package controllers;

import Main.ZQRTApplication;
import credentials.SQLConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class mainMenu implements Initializable {

    @FXML
    public Label user;
    //public ListView listView;
    public HBox warehouseLister;
    public HBox customerLister;
    public HBox publisherLister;
    public HBox bookLister;
    public HBox authorLister;
    public HBox orderLister;
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
    private Label orderNum;
    @FXML
    private VBox pnItems = null;
    @FXML
    private Button btnOverview;
    @FXML
    public Button btnInserts;
    @FXML
    public Button btnDeletions;
    @FXML
    public Button btnUpdates;
    @FXML
    public Button btnStatistics;

    @FXML
    private Button btnSettings;

    @FXML
    private Button booksButtonOverview;
    @FXML
    private Button authorsButtonOverview;
    @FXML
    private Button publishersButtonOverview;
    @FXML
    private Button customersButtonOverview;
    @FXML
    private Button warehousesButtonOverview;
    @FXML
    private Button ordersButtonOverview;

    @FXML
    private Pane pnlInserts;

    @FXML
    private Pane pnlDeletions;

    @FXML
    private Pane pnlOverview;

    @FXML
    private Pane pnlUpdates;
    @FXML
    public Pane pnlStatistics;

    @FXML
    private Pane pnlSettings;

    @FXML
    private VBox bookOverview;



    SQLConnection credentials = SQLConnection.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       setUsername();
       try {
              setFancyNumbers();
         } catch (SQLException e) {
              e.printStackTrace();
       }
        /*try {

            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ZQRT", credentials.getUsername(), credentials.getPassword());
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
*/
    }


    public void setUsername(){
        user.setText(credentials.getUsername());
    }
    public void setFancyNumbers() throws SQLException {
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ZQRT", credentials.getUsername(), credentials.getPassword());
        String[] tablesList = {"book", "author", "publisher", "customer", "warehouse","orders"};
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
            else if (i==4)
                warehouseNum.setText(rs.getString(1));
            else
                orderNum.setText(rs.getString(1));
        }
    con.close();
    }
    public void handleClicks(ActionEvent actionEvent) {
        if (actionEvent.getSource() == btnOverview) {
            pnlOverview.toFront();
        }
        if (actionEvent.getSource() == btnInserts) {
            pnlInserts.toFront();
        }
        if (actionEvent.getSource() == btnDeletions) {
            pnlDeletions.toFront();
        }
        if (actionEvent.getSource() == btnUpdates) {
            pnlUpdates.toFront();
        }
        if (actionEvent.getSource() == btnStatistics) {
            pnlStatistics.toFront();
        }
        if (actionEvent.getSource() == btnSettings) {
            pnlSettings.toFront();
        }
    }

    public void handleOverview(ActionEvent actionEvent){
        if(actionEvent.getSource() == booksButtonOverview){
            bookLister.toFront();
        }
        if(actionEvent.getSource() == authorsButtonOverview){
            authorLister.toFront();
        }
        if(actionEvent.getSource() == publishersButtonOverview){
            publisherLister.toFront();
        }
        if(actionEvent.getSource() == customersButtonOverview){
            customerLister.toFront();
        }
        if(actionEvent.getSource() == warehousesButtonOverview){
            warehouseLister.toFront();
        }
        if(actionEvent.getSource() == ordersButtonOverview){
            orderLister.toFront();
        }

    }

    public void handleSignOut(ActionEvent actionEvent) throws Exception {
        credentials.resetCredentials();
        URL url = getClass().getResource("/resources/FXML/logInPage.fxml");
        Stage stage = (Stage) user.getScene().getWindow();
        ZQRTApplication z = new ZQRTApplication();
        z.changeSceneInStage(stage, url);
    }
}
