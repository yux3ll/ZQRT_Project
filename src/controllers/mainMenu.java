package controllers;

import Main.ZQRTApplication;
import dataCarrier.SQLConnection;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import objects.*;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
    public TableColumn<publisher, String> publisherTable1, publisherTable2, publisherTable3, publisherTable4, publisherTable5, publisherTable6, publisherTable7,publisherTable8;
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
        con.close();
        col1.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().ISBN()));
        col2.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().title()));
        col3.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().authorName()));
        col4.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().publisherName()));
        col5.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().price()));
        col6.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().numberOfPages()));
        col7.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().stockAmount()));
        col8.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().releaseDate()));
        col9.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().genre()));

        bookTable.setItems(bookList);
        FilteredList<book> filteredData = new FilteredList<>(bookList, b -> true);

        searchBar.textProperty().addListener((observable, oldValue, newValue) -> filteredData.setPredicate(book -> {
            if (newValue == null || newValue.isEmpty()) {
                return true;
            }
            String lowerCaseFilter = newValue.toLowerCase();
            if (book.ISBN().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (book.title().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (book.authorName().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (book.publisherName().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (book.price().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (book.numberOfPages().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (book.stockAmount().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (book.releaseDate().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else return book.genre().toLowerCase().contains(lowerCaseFilter);
        }));
        SortedList<book> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(bookTable.comparatorProperty());
        bookTable.setItems(sortedData);
    }
    public void overviewAuthor() throws SQLException {
        authorTable.toFront();

    }
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
            try {
                overviewAuthor();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(actionEvent.getSource() == publishersButtonOverview){
            try {
                overviewPublisher();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(actionEvent.getSource() == customersButtonOverview){
            try {
                overviewCustomer();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(actionEvent.getSource() == warehousesButtonOverview){
            try {
                overviewWarehouse();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(actionEvent.getSource() == ordersButtonOverview){
            try {
                overviewOrder();
            } catch (SQLException e) {
                e.printStackTrace();
            }
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
                "(SELECT COUNT(*) FROM orderLogistics);");
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
