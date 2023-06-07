package controllers;

import Main.ZQRTApplication;
import dataCarrier.SQLConnection;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
    public Label user, bookNum, authorNum, customerNum, publisherNum, warehouseNum, orderNum,revenueNum;
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
    public TableColumn<author,String> authorTable1, authorTable2, authorTable3, authorTable4, authorTable5, authorTable6, authorTable7;
    @FXML
    public TableColumn<warehouse, String> warehouseTable1, warehouseTable2, warehouseTable3, warehouseTable4;
    @FXML
    public TableColumn<order, String> orderTable1, orderTable2, orderTable3, orderTable4, orderTable5, orderTable6;
    @FXML
    public TableColumn<book,String>col1, col2, col3, col4, col5, col6, col7, col8, col9;
    @FXML
    public TextField searchBar;
    ObservableList<book> bookList= FXCollections.observableArrayList();
    ObservableList<publisher> publisherList= FXCollections.observableArrayList();
    ObservableList<customer> customerList= FXCollections.observableArrayList();
    ObservableList<warehouse> warehouseList= FXCollections.observableArrayList();
    ObservableList<order> orderList= FXCollections.observableArrayList();
    ObservableList<author> authorList= FXCollections.observableArrayList();

    @FXML
    private Pane pnlInserts, pnlDeletions, pnlOverview, pnlUpdates, pnlStatistics, pnlSettings, overviewHider;
    SQLConnection credentials = SQLConnection.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       try {
           setUsername();
           overviewBase();
         } catch (SQLException e) {
              e.printStackTrace();
       }
    }

    public void overviewBase() throws SQLException {
        pnlOverview.toFront();
        setFancyNumbers();
        overviewHider.toFront();
    }
    public void insertsBase() throws SQLException {
        pnlInserts.toFront();

    }
    public void updatesBase() throws SQLException {
        pnlUpdates.toFront();

    }
    public void deletionsBase() throws SQLException {
        pnlDeletions.toFront();

    }
    public void statisticsBase() throws SQLException {
        pnlStatistics.toFront();

    }
    public void settingsBase() throws SQLException {
        pnlSettings.toFront();

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
        authorList.clear();
        Connection con = credentials.getConnection();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("""
                SELECT author.authorID, author.name, author.email, COUNT(DISTINCT book.ISBN) AS amountOfPublishedBooks, SUM(orders.quantity) AS amountOfCopiesSold, AVG(book.numberOfPages) AS averagePage, SUM(book.price * orders.quantity) AS revenue
                FROM author
                LEFT JOIN book ON author.authorID = book.authorID
                LEFT JOIN orders ON book.ISBN = orders.ISBN
                GROUP BY author.authorID, author.name, author.email
                ORDER BY author.authorID;""");
        while(rs.next()){
            String authorID = rs.getString("authorID");
            String name = rs.getString("name");
            String email = rs.getString("email");
            String amountOfPublishedBooks = rs.getString("amountOfPublishedBooks");
            String amountOfCopiesSold = rs.getString("amountOfCopiesSold");
            String averagePage = rs.getString("averagePage");
            String revenue = rs.getString("revenue");
            authorList.add(new author(authorID, name, email, amountOfPublishedBooks, amountOfCopiesSold, averagePage, revenue));
        }
        con.close();
        authorTable1.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().ID()));
        authorTable2.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().name()));
        authorTable3.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().email()));
        authorTable4.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().publishedBooks()));
        authorTable5.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().copiesSold()));
        authorTable6.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().avgPage()));
        authorTable7.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().totalRevenue()));

        authorTable.setItems(authorList);
        FilteredList<author> filteredData = new FilteredList<>(authorList, b -> true);

        searchBar.textProperty().addListener((observable, oldValue, newValue) -> filteredData.setPredicate(author -> {
            if (newValue == null || newValue.isEmpty()) {
                return true;
            }
            String lowerCaseFilter = newValue.toLowerCase();
            if (author.ID().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (author.name().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (author.email().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (author.publishedBooks().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (author.copiesSold().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (author.avgPage().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else return author.totalRevenue().toLowerCase().contains(lowerCaseFilter);
        }));
        SortedList<author> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(authorTable.comparatorProperty());
        authorTable.setItems(sortedData);
    }

    public void overviewPublisher() throws SQLException {
        publisherTable.toFront();

    }
    public void overviewCustomer() throws SQLException {
        customerTable.toFront();
    }
    public void overviewWarehouse() throws SQLException {
        warehouseTable.toFront();
    }
    public void overviewOrder() throws SQLException {
        orderTable.toFront();

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
        Statement stmt2 = con.createStatement();
        ResultSet rs2 = stmt2.executeQuery("SELECT SUM(orders.quantity * book.price) AS biggBucks " +
                "FROM orders " +
                "INNER JOIN book ON book.ISBN=orders.ISBN;");
        rs2.next();
        revenueNum.setText(rs2.getString(1)+" ₺");
        con.close();
    }

}
