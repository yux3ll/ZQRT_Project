package controllers;

import Main.ZQRTApplication;
import credentials.SQLConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import objects.*;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class mainMenu implements Initializable {
    @FXML
    public Label user, bookNum, authorNum, customerNum, publisherNum, warehouseNum, orderNum;
   @FXML
   public TableView<book> bookTable;
    @FXML
    public TableView<publisher> publisherTable;
    @FXML
    public TableView<customer> customerTable;
    @FXML
    public TableView<warehouse> warehouseTable;
    @FXML
    public TableView<order> orderTable;
    @FXML
    public TableView<author> authorTable;
    @FXML
    public TableColumn<publisher, String> publisherTable1, publisherTable2, publisherTable3, publisherTable4, publisherTable5, publisherTable6, publisherTable7;
    @FXML
    public TableColumn<customer, String> customerTable1, customerTable2, customerTable3, customerTable4, customerTable5, customerTable6, customerTable7, customerTable8;
    @FXML
    public TableColumn<author,String> authorTable1, authorTable2, authorTable3, authorTable4, authorTable5;
    @FXML
    public TableColumn<warehouse, String> warehouseTable1, warehouseTable2, warehouseTable3, warehouseTable4;
    @FXML
    public TableColumn<order, String> orderTable1, orderTable2, orderTable3, orderTable4, orderTable5, orderTable6, orderTable7, orderTable8, orderTable9;
    @FXML
    public TableColumn<book,String>col1, col2, col3, col4, col5, col6, col7, col8, col9;
    @FXML
    public TextField searchBar;
    ObservableList<book> bookList= FXCollections.observableArrayList();

    @FXML
    private Button btnOverview, btnInserts, btnDeletions, btnUpdates, btnStatistics, btnSettings,btnSignOut, booksButtonOverview, authorsButtonOverview, publishersButtonOverview, customersButtonOverview, warehousesButtonOverview, ordersButtonOverview;
    @FXML
    private Pane pnlInserts, pnlDeletions, pnlOverview, pnlUpdates, pnlStatistics, pnlSettings, hider;
    SQLConnection credentials = SQLConnection.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       try {
              overviewBase();
         } catch (SQLException e) {
              e.printStackTrace();
       }
    }

    public void overviewBase() throws SQLException {
        pnlOverview.toFront();
        setUsername();
        setFancyNumbers();
        hider.toFront();
    }
    public void overviewBook() throws SQLException {
        bookTable.toFront();
        bookList.clear();
        Connection con = credentials.getConnection();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("""
                SELECT ISBN, title,author.name as authorName, publisher.name as publisherName,price, numberOfPages, stockAmount,releaseDate, genre From book
                inner join author on book.authorID = author.authorID
                inner join publisher on book.publisherID = publisher.publisherID
                order by author.authorID;""");
        while(rs.next()){
            String ISBN = rs.getString("ISBN");
            String title = rs.getString("title");
            String author = rs.getString("authorName");
            String publisher = rs.getString("publisherName");
            String price = rs.getString("price");
            String numberOfPages = rs.getString("numberOfPages");
            String stockAmount = rs.getString("stockAmount");
            String releaseDate = rs.getString("releaseDate");
            String genre = rs.getString("genre");
            bookList.add(new book(ISBN, title, author, publisher, price, numberOfPages, stockAmount, releaseDate, genre));
        }
        col1.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
        col2.setCellValueFactory(new PropertyValueFactory<>("title"));
        col3.setCellValueFactory(new PropertyValueFactory<>("authorName"));
        col4.setCellValueFactory(new PropertyValueFactory<>("publisherName"));
        col5.setCellValueFactory(new PropertyValueFactory<>("price"));
        col6.setCellValueFactory(new PropertyValueFactory<>("numberOfPages"));
        col7.setCellValueFactory(new PropertyValueFactory<>("stockAmount"));
        col8.setCellValueFactory(new PropertyValueFactory<>("releaseDate"));
        col9.setCellValueFactory(new PropertyValueFactory<>("genre"));

        bookTable.setItems(bookList);
        FilteredList<book> filteredData = new FilteredList<>(bookList, b -> true);

        searchBar.textProperty().addListener((observable, oldValue, newValue) -> filteredData.setPredicate(book -> {
            if (newValue == null || newValue.isEmpty()) {
                return true;
            }
            String lowerCaseFilter = newValue.toLowerCase();
            if (book.getISBN().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (book.getTitle().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (book.getAuthorName().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (book.getPublisherName().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (book.getPrice().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (book.getNumberOfPages().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (book.getStockAmount().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (book.getReleaseDate().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else return book.getGenre().toLowerCase().contains(lowerCaseFilter);
        }));
        SortedList<book> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(bookTable.comparatorProperty());
        bookTable.setItems(sortedData);
    }
    public void overviewAuthor() throws SQLException {}
    public void overviewPublisher() throws SQLException {}
    public void overviewCustomer() throws SQLException {}
    public void overviewWarehouse() throws SQLException {}
    public void overviewOrder() throws SQLException {}




    public void handleOverviewClick(ActionEvent actionEvent){
        if(actionEvent.getSource() == booksButtonOverview){
            try {
                overviewBook();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(actionEvent.getSource() == authorsButtonOverview){

        }
        if(actionEvent.getSource() == publishersButtonOverview){

        }
        if(actionEvent.getSource() == customersButtonOverview){

        }
        if(actionEvent.getSource() == warehousesButtonOverview){

        }
        if(actionEvent.getSource() == ordersButtonOverview){

        }
    }
    public void handleSignOut() throws Exception {
        credentials.resetCredentials();
        URL url = getClass().getResource("/resources/FXML/logInPage.fxml");
        Stage stage = (Stage) user.getScene().getWindow();
        ZQRTApplication z = new ZQRTApplication();
        z.changeSceneInStage(stage, url);
    }
    public void setUsername(){
        user.setText(credentials.getUsername());
    }
    public void setFancyNumbers() throws SQLException {
        Connection con = credentials.getConnection();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT (SELECT COUNT(*) FROM book), " +
                "(SELECT COUNT(*) FROM author), (SELECT COUNT(*) FROM publisher), " +
                "(SELECT COUNT(*) FROM customer), " +
                "(SELECT COUNT(*) FROM warehouse), " +
                "(SELECT COUNT(*) FROM orders);");
        rs.next();
        bookNum.setText(rs.getString(1));
        authorNum.setText(rs.getString(2));
        publisherNum.setText(rs.getString(3));
        customerNum.setText(rs.getString(4));
        warehouseNum.setText(rs.getString(5));
        orderNum.setText(rs.getString(6));
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
        if (actionEvent.getSource() == btnSignOut) {
            try {
                handleSignOut();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
