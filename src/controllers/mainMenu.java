package controllers;

import Main.ZQRTApplication;
import data.SQLConnection;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class mainMenu implements Initializable {


    @FXML
    private Pane
            pnlInserts,
            pnlOverview,
            pnlUpdates,
            pnlStatistics,
            overviewHider,
            bookInsert,
            insertsMain,
            authorInsert,
            publisherInsert,
            warehouseInsert,
            customerInsert,
            orderInsert,
            updatesMain,
            bookUpdate,
            authorUpdate,
            publisherUpdate,
            warehouseUpdate,
            customerUpdate,
            orderUpdate;

    @FXML
    public Label
            user,
            bookNum,
            authorNum,
            customerNum,
            publisherNum,
            warehouseNum,
            orderNum,
            revenueNum,
            bookInsertResult,
            authorInsertResult,
            publisherInsertResult,
            warehouseInsertResult,
            customerInsertResult,
            orderInsertResult,
            bookUpdateResult,
            authorUpdateResult,
            publisherUpdateResult,
            warehouseUpdateResult,
            customerUpdateResult,
            orderUpdateResult,
            bestCustomer,
            bestAuthor,
            trBook,
            bestBook,
            bestPublisher,
            bigBasket;
   @FXML
    public TableView<book>
           bookTable;
    @FXML
    public TableView<publisher>
            publisherTable;
    @FXML
    public TableView<customer>
            customerTable;
    @FXML
    public TableView<warehouse>
            warehouseTable;
    @FXML
    public TableView<order>
            orderTable;
    @FXML
    public TableView<author>
            authorTable;
    @FXML
    public TableColumn<publisher, String>
            publisherTable1,
            publisherTable2,
            publisherTable3,
            publisherTable4,
            publisherTable5,
            publisherTable6,
            publisherTable7,
            publisherTable8;
    @FXML
    public TableColumn<customer, String>
            customerTable1,
            customerTable2,
            customerTable3,
            customerTable4,
            customerTable5,
            customerTable6,
            customerTable7,
            customerTable8;
    @FXML
    public TableColumn<author,String>
            authorTable1,
            authorTable2,
            authorTable3,
            authorTable4,
            authorTable5,
            authorTable6,
            authorTable7;
    @FXML
    public TableColumn<warehouse, String>
            warehouseTable1,
            warehouseTable2,
            warehouseTable3,
            warehouseTable4;
    @FXML
    public TableColumn<order, String>
            orderTable1,
            orderTable2,
            orderTable3,
            orderTable4,
            orderTable5,
            orderTable6,
            orderTable7;
    @FXML
    public TableColumn<book,String>
            col1,
            col2,
            col3,
            col4,
            col5,
            col6,
            col7,
            col8,
            col9;
    @FXML
    public TextField
            searchBar,
            bookInsertISBN,
            bookInsertPublisherID,
            bookInsertAuthorID,
            bookInsertTitle,
            bookInsertPrice,
            bookInsertNumberOfPages,
            bookInsertStockAmount,
            bookInsertReleaseDate,
            bookInsertGenre,
            authorInsertName,
            authorInsertID,
            authorInsertEmail,
            publisherInsertID,
            publisherInsertPhone,
            publisherInsertName,
            publisherInsertAddress,
            publisherInsertEmail,
            warehouseInsertID,
            warehouseInsertName,
            warehouseInsertAddress,
            customerInsertID,
            customerInsertName,
            customerInsertAddress,
            customerInsertPhone,
            customerInsertEmail,
            orderInsertID,
            orderInsertCustomerID,
            orderInsertBooks,
            bookUpdateISBN,
            bookUpdatePublisherID,
            bookUpdateAuthorID,
            bookUpdateTitle,
            bookUpdatePrice,
            bookUpdateNumberOfPages,
            bookUpdateStockAmount,
            bookUpdateReleaseDate,
            bookUpdateGenre,
            authorUpdateName,
            authorUpdateID,
            authorUpdateEmail,
            publisherUpdateID,
            publisherUpdatePhone,
            publisherUpdateName,
            publisherUpdateAddress,
            publisherUpdateEmail,
            warehouseUpdateID,
            warehouseUpdateName,
            warehouseUpdateAddress,
            customerUpdateID,
            customerUpdateName,
            customerUpdateAddress,
            customerUpdatePhone,
            customerUpdateEmail,
            orderUpdateID,
            orderUpdateStatus;


    final ObservableList<book> bookList= FXCollections.observableArrayList();
    final ObservableList<publisher> publisherList= FXCollections.observableArrayList();
    ObservableList<customer> customerList= FXCollections.observableArrayList();
    final ObservableList<warehouse> warehouseList= FXCollections.observableArrayList();
    final ObservableList<order> orderList= FXCollections.observableArrayList();
    final ObservableList<author> authorList= FXCollections.observableArrayList();

    final SQLConnection credentials = SQLConnection.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       try {
           setUsername();
           overviewBase();
         } catch (SQLException e) {
              e.printStackTrace();
       }
    }
    public void setUsername(){
        user.setText(credentials.getUsername());
    }
    public void setFancyNumbers() throws SQLException {
        Connection con = credentials.getConnection();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("""
                SELECT
                (SELECT COUNT(*) FROM book),
                (SELECT COUNT(*) FROM author),
                (SELECT COUNT(*) FROM publisher),
                (SELECT COUNT(*) FROM customer),
                (SELECT COUNT(*) FROM warehouse),
                (SELECT COUNT(*) FROM orderLogistics);""");
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

    public void overviewBase() throws SQLException {
        pnlOverview.toFront();
        setFancyNumbers();
        overviewHider.toFront();
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
                SELECT
                    author.authorID,
                    author.name,
                    author.email,
                    COUNT(DISTINCT book.ISBN) AS amountOfPublishedBooks,
                    SUM(orders.quantity) AS amountOfCopiesSold,
                    AVG(book.numberOfPages) AS averagePage,
                    SUM(book.price * orders.quantity) AS revenue
                FROM
                    author
                        LEFT JOIN book ON author.authorID = book.authorID
                        LEFT JOIN orders ON book.ISBN = orders.ISBN
                GROUP BY
                    author.authorID,
                    author.name,
                    author.email
                ORDER BY
                author.authorID;""");
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
        publisherList.clear();
        Connection con = credentials.getConnection();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("""
                SELECT
                    publisher.publisherID,
                    publisher.name AS publisherName,
                    publisher.email,
                    publisher.phoneNumber,
                    publisher.address,
                    SUM(orders.quantity) as copiesSold,
                    COUNT(book.publisherID) AS bookCount,
                    SUM(book.price * orders.quantity) AS revenue
                FROM
                    publisher
                        INNER JOIN book ON publisher.publisherID = book.publisherID
                        INNER JOIN orders ON book.ISBN = orders.ISBN
                GROUP BY
                    publisher.publisherID,
                    publisher.name,
                    publisher.email,
                    publisher.phoneNumber,
                    publisher.address
                ORDER BY
                    publisher.publisherID;
                """);
        while(rs.next()){
            String ID = rs.getString("publisherID");
            String name = rs.getString("publisherName");
            String email = rs.getString("email");
            String phone = rs.getString("phoneNumber");
            String address = rs.getString("address");
            String publishedBooks = rs.getString("bookCount");
            String copiesSold = rs.getString("copiesSold");
            String revenue = rs.getString("revenue");
            publisherList.add(new publisher(ID, name, email, phone, address, publishedBooks, copiesSold, revenue));
        }
        con.close();

        publisherTable1.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().ID()));
        publisherTable2.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().name()));
        publisherTable3.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().email()));
        publisherTable4.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().phone()));
        publisherTable5.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().address()));
        publisherTable6.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().publishedBooks()));
        publisherTable7.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().copiesSold()));
        publisherTable8.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().totalRevenue()));

        publisherTable.setItems(publisherList);
        FilteredList<publisher> filteredData = new FilteredList<>(publisherList, b -> true);

        searchBar.textProperty().addListener((observable, oldValue, newValue) -> filteredData.setPredicate(publisher -> {
            if (newValue == null || newValue.isEmpty()) {
                return true;
            }
            String lowerCaseFilter = newValue.toLowerCase();
            if (publisher.ID().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (publisher.name().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (publisher.email().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (publisher.phone().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (publisher.address().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (publisher.publishedBooks().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (publisher.copiesSold().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else return publisher.totalRevenue().toLowerCase().contains(lowerCaseFilter);
        }));
        SortedList<publisher> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(publisherTable.comparatorProperty());
        publisherTable.setItems(sortedData);
    }
    public void overviewCustomer() throws SQLException {
        customerTable.toFront();
        customerList.clear();
        Connection con = credentials.getConnection();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("""
                SELECT
                    customer.customerID,
                    customer.name,
                    customer.email,
                    customer.phoneNumber,
                    customer.address,
                    COUNT(shoppingbasket.customerID) AS booksInBasket,
                    COALESCE(SUM(orders.quantity), 0) AS booksBought,
                    COALESCE(SUM(book.price), 0) AS moneySpent
                FROM
                    customer
                        LEFT JOIN shoppingBasket ON shoppingBasket.customerID = customer.customerID
                        LEFT JOIN orderlogistics ON orderlogistics.customerID = customer.customerID
                        LEFT JOIN orders ON orderlogistics.orderID = orders.orderID
                        LEFT JOIN book ON book.ISBN = shoppingBasket.ISBN
                GROUP BY
                    customer.customerID,
                    customer.name,
                    customer.email,
                    customer.phoneNumber,
                    customer.address
                ORDER BY
                    customer.customerID;
                """);
        while(rs.next()){
            String ID = rs.getString("customerID");
            String name = rs.getString("name");
            String email = rs.getString("email");
            String phone = rs.getString("phoneNumber");
            String address = rs.getString("address");
            String booksInBasket = rs.getString("booksInBasket");
            String booksBought = rs.getString("booksBought");
            String moneySpent = rs.getString("moneySpent");
            customerList.add(new customer(ID, name, email, phone, address, booksInBasket, booksBought, moneySpent));
        }
        con.close();

        customerTable1.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().ID()));
        customerTable2.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().name()));
        customerTable3.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().email()));
        customerTable4.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().phone()));
        customerTable5.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().address()));
        customerTable6.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().booksInBasket()));
        customerTable7.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().booksBought()));
        customerTable8.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().moneySpent()));

        customerTable.setItems(customerList);
        FilteredList<customer> filteredData = new FilteredList<>(customerList, b -> true);

        searchBar.textProperty().addListener((observable, oldValue, newValue) -> filteredData.setPredicate(customer -> {
            if (newValue == null || newValue.isEmpty()) {
                return true;
            }
            String lowerCaseFilter = newValue.toLowerCase();
            if (customer.ID().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (customer.name().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (customer.email().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (customer.phone().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (customer.address().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (customer.booksInBasket().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (customer.booksBought().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else return customer.moneySpent().toLowerCase().contains(lowerCaseFilter);
        }));
        SortedList<customer> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(customerTable.comparatorProperty());
        customerTable.setItems(sortedData);
    }
    public void overviewWarehouse() throws SQLException {
        warehouseTable.toFront();
        warehouseList.clear();
        Connection con = credentials.getConnection();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("""
            SELECT
                warehouse.warehouseID,
                warehouse.name,
                warehouse.address,
                SUM(bookwarehouse.stockAmount) AS totalStock
            FROM
                warehouse
                    LEFT JOIN bookwarehouse ON warehouse.warehouseID = bookwarehouse.warehouseID
            GROUP BY
                warehouse.warehouseID,
                warehouse.name,
                warehouse.address
            ORDER BY
                warehouse.warehouseID;""");
        while(rs.next()){
            String ID = rs.getString("warehouseID");
            String name = rs.getString("name");
            String address = rs.getString("address");
            String totalStock = rs.getString("totalStock");
            warehouseList.add(new warehouse(ID, name, address, totalStock));
        }
        con.close();

        warehouseTable1.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().ID()));
        warehouseTable2.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().name()));
        warehouseTable3.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().address()));
        warehouseTable4.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().totalStock()));

        warehouseTable.setItems(warehouseList);
        FilteredList<warehouse> filteredData = new FilteredList<>(warehouseList, b -> true);

        searchBar.textProperty().addListener((observable, oldValue, newValue) -> filteredData.setPredicate(warehouse -> {
            if (newValue == null || newValue.isEmpty()) {
                return true;
            }
            String lowerCaseFilter = newValue.toLowerCase();
            if (warehouse.ID().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (warehouse.name().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (warehouse.address().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else return warehouse.totalStock().toLowerCase().contains(lowerCaseFilter);
        }));
        SortedList<warehouse> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(warehouseTable.comparatorProperty());
        warehouseTable.setItems(sortedData);
    }
    public void overviewOrder() throws SQLException {
        orderTable.toFront();
        orderList.clear();
        Connection con = credentials.getConnection();
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("""
            SELECT
                orderlogistics.orderID,
                customer.name AS customerName,
                orderlogistics.date,
                orderlogistics.status,
                SUM(orders.quantity) AS amountOfBooks,
                SUM(book.price * orders.quantity) AS totalPriceOfOrder,
                GROUP_CONCAT(DISTINCT book.title) AS booksInOrder
            FROM
                orderlogistics
                    INNER JOIN customer ON orderlogistics.customerID = customer.customerID
                    INNER JOIN orders ON orderlogistics.orderID = orders.orderID
                    INNER JOIN book ON orders.ISBN = book.ISBN
            GROUP BY
                orderlogistics.orderID,
                customer.name,
                orderlogistics.date,
                orderlogistics.status
            ORDER BY
                orderlogistics.orderID;
                """);

        while(rs.next()){
            String ID = rs.getString("orderID");
            String customerName = rs.getString("customerName");
            String date = rs.getString("date");
            String statusTemp = rs.getString("status");
            String status;
            if(statusTemp.equals("0")){
                status = "Pending";
            } else {
                status = "Completed";
            }
            String amountOfBooks = rs.getString("amountOfBooks");
            String totalPriceOfOrder = rs.getString("totalPriceOfOrder");
            String booksInOrder = rs.getString("booksInOrder");
            orderList.add(new order(ID, customerName, date, status, amountOfBooks, totalPriceOfOrder, booksInOrder));
    }
        con.close();

        orderTable1.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().ID()));
        orderTable2.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().customerName()));
        orderTable3.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().date()));
        orderTable4.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().status()));
        orderTable5.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().amountOfBooks()));
        orderTable6.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().totalPriceOfOrder()));
        orderTable7.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().booksInOrder()));

        orderTable.setItems(orderList);
        FilteredList<order> filteredData = new FilteredList<>(orderList, b -> true);

        searchBar.textProperty().addListener((observable, oldValue, newValue) -> filteredData.setPredicate(order -> {
            if (newValue == null || newValue.isEmpty()) {
                return true;
            }
            String lowerCaseFilter = newValue.toLowerCase();
            if (order.ID().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (order.customerName().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (order.date().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (order.status().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (order.amountOfBooks().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else if (order.totalPriceOfOrder().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else return order.booksInOrder().toLowerCase().contains(lowerCaseFilter);
        }));
        SortedList<order> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(orderTable.comparatorProperty());
        orderTable.setItems(sortedData);
    }
    public void insertsBase() {
        pnlInserts.toFront();
        insertsMain.toFront();
    }
    public void insertsGoBack(){
        insertsMain.toFront();
    }

    public void insertBook(){
        bookInsert.toFront();
    }
    public void insertBookClear(){
        bookInsertISBN.clear();
        bookInsertTitle.clear();
        bookInsertPrice.clear();
        bookInsertStockAmount.clear();
        bookInsertAuthorID.clear();
        bookInsertPublisherID.clear();
        bookInsertGenre.clear();
        bookInsertReleaseDate.clear();
        bookInsertNumberOfPages.clear();
        bookInsertResult.setText("");
    }
    public void attemptBookInsert(){
        String ISBN = bookInsertISBN.getText();
        String title = bookInsertTitle.getText();
        String price = bookInsertPrice.getText();
        String stockAmount = bookInsertStockAmount.getText();
        String authorID = bookInsertAuthorID.getText();
        String publisherID = bookInsertPublisherID.getText();
        String genre = bookInsertGenre.getText();
        String releaseDate = bookInsertReleaseDate.getText();
        String numberOfPages = bookInsertNumberOfPages.getText();

        // replace "'" with "/'" to avoid SQL syntax errors
        title = title.replace("'", "/'");
        genre = genre.replace("'", "/'");
        //if isbn is not 13 digits, return error
        if(ISBN.length() != 12){
            bookInsertResult.setText("ISBN must be 12 digits");
            return;
        }
        // if any of the fields are empty, return error
        if(title.isEmpty() || price.isEmpty() || stockAmount.isEmpty() || authorID.isEmpty() || publisherID.isEmpty() || genre.isEmpty() || releaseDate.isEmpty() || numberOfPages.isEmpty()){
            bookInsertResult.setText("All fields must be filled");
            return;
        }

        String query = "INSERT INTO book (ISBN, title, authorID, publisherID, price, numberOfPages, stockAmount, releaseDate, genre) VALUES ('" + ISBN + "', '" + title + "', '" + authorID + "', '" + publisherID + "', '" + price + "', '" + numberOfPages + "', '" + stockAmount + "', '" + releaseDate + "', '" + genre + "');";

        try {
            Connection con = credentials.getConnection();
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
            //randomly select warehouseID from the table warehouse
            String query1 = "SELECT warehouseID FROM warehouse ORDER BY RAND() LIMIT 1;";
            ResultSet rs = stmt.executeQuery(query1);
            rs.next();
            String warehouseID = rs.getString("warehouseID");
            String query2 = "INSERT INTO bookWarehouse (ISBN, warehouseID,stockAmount) VALUES ('" + ISBN + "', '" + warehouseID + "', '" + stockAmount + "');";
            stmt.executeUpdate(query2);
            con.close();
            bookInsertResult.setText("Book successfully added. ISBN: " + ISBN);
        } catch (SQLException e) {
            String error = e.getMessage();
            if (error.contains("Duplicate entry")) {
                error = "ISBN already exists";
            }
            if(error.contains("Incorrect date value")){
                error = "Incorrect date format, please use YYYY-MM-DD";
            }
            if(error.contains("authorFK")){
                error = "Author ID does not exist";
            }
            if(error.contains("publisherFK")){
                error = "Publisher ID does not exist";
            }
            if(error.contains("Incorrect integer value")){
                error = "Incorrect integer value";
            }
            bookInsertResult.setText(error);
        }
    }
    public void insertAuthor(){
        authorInsert.toFront();
    }
    public void insertAuthorClear(){
        authorInsertID.clear();
        authorInsertName.clear();
        authorInsertEmail.clear();
        authorInsertResult.setText("");
    }
    public void attemptAuthorInsert(){
        String ID = authorInsertID.getText();
        String name = authorInsertName.getText();
        String email = authorInsertEmail.getText();
        // replace "'" with "/'" to avoid SQL syntax errors
        name = name.replace("'", "/'");
        if(ID.isEmpty()){
            try {
                Connection con = credentials.getConnection();
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT MAX(authorID) FROM author;");
                while (rs.next()) {
                    ID = rs.getString("MAX(authorID)");
                }
                ID = Integer.toString(Integer.parseInt(ID) + 1);
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        // if any of the fields are empty, return error
        if(name.isEmpty()){
            authorInsertResult.setText("All fields must be filled");
            return;
        }

        String query = "INSERT INTO author (authorID, name, email) VALUES ('" + ID + "', '" + name + "', '" + email + "');";

        try {
            Connection con = credentials.getConnection();
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
            con.close();
            authorInsertResult.setText("Author successfully added. ID: " + ID);
        } catch (SQLException e) {
            String error = e.getMessage();
            if (error.contains("Duplicate entry")) {
                error = "Author ID already exists";
            }
            authorInsertResult.setText(error);
        }
    }

    public void insertPublisher(){
        publisherInsert.toFront();
    }
    public void insertPublisherClear(){
        publisherInsertID.clear();
        publisherInsertName.clear();
        publisherInsertResult.setText("");
        publisherInsertAddress.clear();
        publisherInsertPhone.clear();
        publisherInsertEmail.clear();
    }
    public void attemptPublisherInsert(){
        String ID = publisherInsertID.getText();
        String name = publisherInsertName.getText();
        String address = publisherInsertAddress.getText();
        String phone = publisherInsertPhone.getText();
        String email = publisherInsertEmail.getText();
        // replace "'" with "/'" to avoid SQL syntax errors
        name = name.replace("'", "/'");
        address = address.replace("'", "/'");
        if(ID.isEmpty()){
            try {
                Connection con = credentials.getConnection();
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT MAX(publisherID) FROM publisher;");
                while (rs.next()) {
                    ID = rs.getString("MAX(publisherID)");
                }
                ID = Integer.toString(Integer.parseInt(ID) + 1);
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        // if any of the fields are empty, return error
        if(name.isEmpty()||address.isEmpty()||phone.isEmpty()||email.isEmpty()){
            publisherInsertResult.setText("All fields except for ID must be filled");
            return;
        }
        String query = "INSERT INTO publisher (publisherID, name, address, phoneNumber, email) VALUES ('" + ID + "', '" + name + "', '" + address + "', '" + phone + "', '" + email + "');";
        try {
            Connection con = credentials.getConnection();
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
            con.close();
            publisherInsertResult.setText("Publisher successfully added. ID: " + ID);
        } catch (SQLException e) {
            String error = e.getMessage();
            if (error.contains("Duplicate entry")) {
                error = "Publisher with identical ID already exists";
            }
            publisherInsertResult.setText(error);
        }
    }

    public void insertCustomer(){
        customerInsert.toFront();
    }
    public void insertCustomerClear(){
        customerInsertID.clear();
        customerInsertName.clear();
        customerInsertAddress.clear();
        customerInsertPhone.clear();
        customerInsertEmail.clear();
        customerInsertResult.setText("");
    }
    public void attemptCustomerInsert(){
        String ID = customerInsertID.getText();
        String name = customerInsertName.getText();
        String address = customerInsertAddress.getText();
        String phone = customerInsertPhone.getText();
        String email = customerInsertEmail.getText();
        // replace "'" with "/'" to avoid SQL syntax errors
        name = name.replace("'", "/'");
        address = address.replace("'", "/'");
        if(ID.isEmpty()){
            try {
                Connection con = credentials.getConnection();
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT MAX(customerID) FROM customer;");
                while (rs.next()) {
                    ID = rs.getString("MAX(customerID)");
                }
                ID = Integer.toString(Integer.parseInt(ID) + 1);
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        // if any of the fields are empty, return error
        if(name.isEmpty()||address.isEmpty()||phone.isEmpty()||email.isEmpty()){
            customerInsertResult.setText("All fields except for ID must be filled");
            return;
        }
        String query = "INSERT INTO customer (customerID, name, address, phoneNumber, email) VALUES ('" + ID + "', '" + name + "', '" + address + "', '" + phone + "', '" + email + "');";
        try {
            Connection con = credentials.getConnection();
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
            con.close();
            customerInsertResult.setText("Customer successfully added. ID: " + ID);
        } catch (SQLException e) {
            String error = e.getMessage();
            if (error.contains("Duplicate entry")) {
                error = "Customer with identical ID already exists";
            }
            customerInsertResult.setText(error);
        }
    }

    public void insertWarehouse(){
        warehouseInsert.toFront();
    }
    public void insertWarehouseClear(){
        warehouseInsertID.clear();
        warehouseInsertName.clear();
        warehouseInsertAddress.clear();
        warehouseInsertResult.setText("");
    }
    public void attemptWarehouseInsert(){
        String ID = warehouseInsertID.getText();
        String name = warehouseInsertName.getText();
        String address = warehouseInsertAddress.getText();
        // replace "'" with "/'" to avoid SQL syntax errors
        name = name.replace("'", "/'");
        address = address.replace("'", "/'");
        if(ID.isEmpty()){
            try {
                Connection con = credentials.getConnection();
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT MAX(warehouseID) FROM warehouse;");
                while (rs.next()) {
                    ID = rs.getString("MAX(warehouseID)");
                }
                ID = Integer.toString(Integer.parseInt(ID) + 1);
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        // if any of the fields are empty, return error
        if(name.isEmpty()||address.isEmpty()){
            warehouseInsertResult.setText("All fields except for ID must be filled");
            return;
        }
        String query = "INSERT INTO warehouse (warehouseID, name, address) VALUES ('" + ID + "', '" + name + "', '" + address + "');";
        try {
            Connection con = credentials.getConnection();
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
            con.close();
            warehouseInsertResult.setText("Warehouse successfully added. ID: " + ID);
        } catch (SQLException e) {
            String error = e.getMessage();
            if (error.contains("Duplicate entry")) {
                error = "Warehouse with identical ID already exists";
            }
            warehouseInsertResult.setText(error);
        }
    }

    public void insertOrder(){
        orderInsert.toFront();
    }
    public void insertOrderClear(){
        orderInsertID.clear();
        orderInsertCustomerID.clear();
        orderInsertBooks.clear();
        orderInsertResult.setText("");
    }
    public void attemptOrderInsert(){
        String ID = orderInsertID.getText();
        String customerID = orderInsertCustomerID.getText();
        String books = orderInsertBooks.getText();
        LocalDate dateObj = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = dateObj.format(formatter);
        // replace "'" with "/'" to avoid SQL syntax errors
        books = books.replace("'", "/'");
        //for order M:N table
        String[] bookList = books.split("[,\\-]");
        for (String s : bookList) {
            if (s.length() != 12) {
                orderInsertResult.setText("ISBNs must be 12 characters long");
                return;
            }
        }
        // if any of the fields are empty, return error
        if(customerID.isEmpty()||books.isEmpty()){
            orderInsertResult.setText("All fields except for ID must be filled");
            return;
        }
        //check if the books exist in the book table before inserting
        for (String s : bookList) {
            String query = "SELECT * FROM book WHERE ISBN = '" + s + "';";
            try {
                Connection con = credentials.getConnection();
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                if (!rs.next()) {
                    orderInsertResult.setText("Book with ISBN " + s + " does not exist");
                    return;
                }
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if(ID.isEmpty()){
            try {
                Connection con = credentials.getConnection();
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery("SELECT MAX(orderID) FROM orderlogistics;");
                while (rs.next()) {
                    ID = rs.getString("MAX(orderID)");
                }
                ID = Integer.toString(Integer.parseInt(ID) + 1);
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        // if any of the fields are empty, return error

        String query = "INSERT INTO orderlogistics (orderID, customerID, date,status) VALUES ('" + ID + "', '" + customerID + "', '" + date + "', '" + 0 + "');";
        try {
            Connection con = credentials.getConnection();
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
            for (String s : bookList) {
                //check if a book has stock
                ResultSet rs = stmt.executeQuery("SELECT * FROM book WHERE ISBN = '" + s + "' AND stockAmount > 0;");
                if (!rs.next()) {
                    orderInsertResult.setText("Book with ISBN " + s + " is out of stock");
                    return;
                }
                rs.close();
                //decrement 1 from the stockAmount of books in the table book and decrement 1 form the stockAmount of books in table bookWarehouse
                // where the stockAmount is the largest and warehouse id is the smallest
                stmt.executeUpdate("UPDATE book SET stockAmount = stockAmount - 1 WHERE ISBN = '" + s + "';");
                ResultSet rs2 = stmt.executeQuery("SELECT * FROM bookWarehouse WHERE ISBN = '" + s + "' ORDER BY stockAmount DESC;");
                rs2.next();
                String warehouseID = rs2.getString("warehouseID");
                stmt.executeUpdate("UPDATE bookWarehouse SET stockAmount = stockAmount - 1 WHERE ISBN = '" + s + "' AND warehouseID = '" + warehouseID + "';");
                rs2.close();
                //if an orderID ISBN pair already exists,
                // increase the quantity by 1,else create a new pair with quantity 1
                ResultSet rs1 = stmt.executeQuery("SELECT * FROM orders WHERE orderID = '" + ID + "' AND ISBN = '" + s + "';");
                if (rs1.next()) {
                    stmt.executeUpdate("UPDATE orders SET quantity = quantity + 1 WHERE orderID = '" + ID + "' AND ISBN = '" + s + "';");
                } else {
                    stmt.executeUpdate("INSERT INTO orders (orderID, ISBN, quantity) VALUES ('" + ID + "', '" + s + "', '" + 1 + "');");
                }
            }
                con.close();
                orderInsertResult.setText("Order successfully added. ID: " + ID);
        } catch (SQLException e) {
            String error = e.getMessage();
            if (error.contains("Duplicate entry")) {
                error = "Order with identical ID already exists";
            }
            if(error.contains("customerFK2")){
                error = "Customer with ID " + customerID + " does not exist";
            }
            orderInsertResult.setText(error);
        }
    }


    public void statisticsBase() throws Exception{
        pnlStatistics.toFront();
        Connection con = credentials.getConnection();
        Statement stmt = con.createStatement();

        ResultSet rs = stmt.executeQuery("SELECT ISBN, SUM(quantity) AS total FROM orders GROUP BY ISBN ORDER BY total DESC LIMIT 1;");
        rs.next();
        String ISBN = rs.getString("ISBN");
        String total = rs.getString("total");
        ResultSet rs1 = stmt.executeQuery("SELECT * FROM book WHERE ISBN = '" + ISBN + "';");
        rs1.next();
        String title = rs1.getString("title");
        bestBook.setText(title + " with " + total + " copies sold");
        rs1.close();

        ResultSet rs2 = stmt.executeQuery("SELECT authorID, SUM(quantity) AS total FROM orders NATURAL JOIN book GROUP BY authorID ORDER BY total DESC LIMIT 1;");
        rs2.next();
        String authorID = rs2.getString("authorID");
        String total1 = rs2.getString("total");
        rs2.close();
        ResultSet rs3 = stmt.executeQuery("SELECT * FROM author WHERE authorID = '" + authorID + "';");
        rs3.next();
        String name = rs3.getString("name");
        rs3.close();
        bestAuthor.setText(name + " with " + total1 + " copies sold");

        ResultSet rs4 = stmt.executeQuery("SELECT publisherID, SUM(quantity) AS total FROM orders NATURAL JOIN book GROUP BY publisherID ORDER BY total DESC LIMIT 1;");
        rs4.next();
        String publisherID = rs4.getString("publisherID");
        String total2 = rs4.getString("total");
        rs4.close();
        ResultSet rs5 = stmt.executeQuery("SELECT * FROM publisher WHERE publisherID = '" + publisherID + "';");
        rs5.next();
        String name1 = rs5.getString("name");
        rs5.close();
        bestPublisher.setText(name1 + " with " + total2 + " copies sold");

        ResultSet rs6 = stmt.executeQuery("SELECT customerID, COUNT(ISBN) AS total FROM shoppingBasket GROUP BY customerID ORDER BY total DESC LIMIT 1;");
        rs6.next();
        String customerID = rs6.getString("customerID");
        String total3 = rs6.getString("total");
        rs6.close();
        ResultSet rs7 = stmt.executeQuery("SELECT * FROM customer WHERE customerID = '" + customerID + "';");
        rs7.next();
        String name2 = rs7.getString("name");
        rs7.close();
        bigBasket.setText(name2 + " with " + total3 + " books");

        ResultSet rs8 = stmt.executeQuery("SELECT customerID, SUM(price) AS total FROM orders NATURAL JOIN orderLogistics NATURAL JOIN book GROUP BY customerID ORDER BY total DESC LIMIT 1;");
        rs8.next();
        String customerID1 = rs8.getString("customerID");
        String total4 = rs8.getString("total");
        rs8.close();
        ResultSet rs9 = stmt.executeQuery("SELECT * FROM customer WHERE customerID = '" + customerID1 + "';");
        rs9.next();
        String name3 = rs9.getString("name");
        rs9.close();
        bestCustomer.setText(name3 + " with " + total4 + " lira spent");

        ResultSet rs10 = stmt.executeQuery("SELECT ISBN, price FROM book ORDER BY price DESC LIMIT 1;");
        rs10.next();
        String ISBN1 = rs10.getString("ISBN");
        String price = rs10.getString("price");
        rs10.close();
        ResultSet rs11 = stmt.executeQuery("SELECT * FROM book WHERE ISBN = '" + ISBN1 + "';");
        rs11.next();
        String title1 = rs11.getString("title");
        rs11.close();
        trBook.setText(title1 + " with price " + price + " euros");
    }


    public void handleSignOut() throws Exception {
        credentials.resetCredentials();
        URL url = getClass().getResource("/resources/FXML/logInPage.fxml");
        Stage stage = (Stage) user.getScene().getWindow();
        ZQRTApplication z = new ZQRTApplication();
        z.changeSceneInStage(stage, url);
    }

    public void updatesBase() {
        pnlUpdates.toFront();
        updatesMain.toFront();
    }
    public void updatesGoBack() {
        updatesMain.toFront();
    }
    public void updateBook() {
        bookUpdate.toFront();
    }
    public void listBookElements(){
        String ISBN = bookUpdateISBN.getText();
        String query = "SELECT * FROM book WHERE ISBN = '" + ISBN + "';";
        try {
            Connection con = credentials.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                bookUpdateTitle.setText(rs.getString("title"));
                bookUpdateAuthorID.setText(rs.getString("authorID"));
                bookUpdatePublisherID.setText(rs.getString("publisherID"));
                bookUpdateGenre.setText(rs.getString("genre"));
                bookUpdatePrice.setText(rs.getString("price"));
                bookUpdateStockAmount.setText(rs.getString("stockAmount"));
                bookUpdateReleaseDate.setText(rs.getString("releaseDate"));
                bookUpdateNumberOfPages.setText(rs.getString("numberOfPages"));
            }
            if(bookUpdateTitle.getText().isEmpty()){
                bookUpdateResult.setText("Book with ISBN " + ISBN + " does not exist");
            }
            con.close();
            bookUpdateResult.setText("Book with ISBN " + ISBN + " successfully listed");
        } catch (SQLException e) {
            bookUpdateResult.setText(e.getMessage());
            }
        }

    public void updateBookClear() {
        bookUpdateISBN.clear();
        bookUpdateTitle.clear();
        bookUpdateAuthorID.clear();
        bookUpdatePublisherID.clear();
        bookUpdateGenre.clear();
        bookUpdatePrice.clear();
        bookUpdateStockAmount.clear();
        bookUpdateReleaseDate.clear();
        bookUpdateNumberOfPages.clear();
        bookUpdateResult.setText("");
    }
    public void attemptBookUpdate() {
        String ISBN = bookUpdateISBN.getText();
        String title = bookUpdateTitle.getText();
        String authorID = bookUpdateAuthorID.getText();
        String publisherID = bookUpdatePublisherID.getText();
        String genre = bookUpdateGenre.getText();
        String price = bookUpdatePrice.getText();
        String stockAmount = bookUpdateStockAmount.getText();
        String releaseDate = bookUpdateReleaseDate.getText();
        String numberOfPages = bookUpdateNumberOfPages.getText();
        String error;
        //date format error

        String query = "UPDATE book SET title = '" + title + "', authorID = '" + authorID + "', publisherID = '" + publisherID + "', genre = '" + genre + "', price = '" + price + "', stockAmount = '" + stockAmount + "', releaseDate = '" + releaseDate + "', numberOfPages = '" + numberOfPages + "' WHERE ISBN = '" + ISBN + "';";
        try {
            Connection con = credentials.getConnection();
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
            con.close();
            bookUpdateResult.setText("Book with ISBN " + ISBN + " successfully updated");
        } catch (SQLException e) {
            error = e.getMessage();
            if(error.contains("authorFK")){
                error = "Author with ID " + authorID + " does not exist";
            }
            if(error.contains("publisherFK")){
                error = "Publisher with ID " + publisherID + " does not exist";
            }
            if(error.contains("Incorrect date")){
                error = "Incorrect date format, please use YYYY-MM-DD";
            }
            bookUpdateResult.setText(error);
        }
    }
    public void attemptBookDelete() {
        String ISBN = bookUpdateISBN.getText();
        String query = "DELETE FROM book WHERE ISBN = '" + ISBN + "';";
        try {
            Connection con = credentials.getConnection();
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
            con.close();
            bookUpdateResult.setText("Book with ISBN " + ISBN + " successfully deleted");
        } catch (SQLException e) {
            bookUpdateResult.setText(e.getMessage());
        }
    }

    public void updateCustomer() {
        customerUpdate.toFront();
    }
    public void listCustomerElements(){
        String ID = customerUpdateID.getText();
        String query = "SELECT * FROM customer WHERE customerID = '" + ID + "';";
        try {
            Connection con = credentials.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                customerUpdateName.setText(rs.getString("name"));
                customerUpdateAddress.setText(rs.getString("address"));
                customerUpdateEmail.setText(rs.getString("email"));
                customerUpdatePhone.setText(rs.getString("phoneNumber"));
            }
            if(customerUpdateName.getText().isEmpty()){
                customerUpdateResult.setText("Customer with ID " + ID + " does not exist");
            }
            con.close();
            customerUpdateResult.setText("Customer with ID " + ID + " successfully listed");
        } catch (SQLException e) {
            customerUpdateResult.setText(e.getMessage());
        }

    }
    public void updateCustomerClear() {
        customerUpdateID.clear();
        customerUpdateName.clear();
        customerUpdateAddress.clear();
        customerUpdateEmail.clear();
        customerUpdatePhone.clear();
        customerUpdateResult.setText("");
    }
    public void attemptCustomerUpdate() {
        String ID = customerUpdateID.getText();
        String name = customerUpdateName.getText();
        String address = customerUpdateAddress.getText();
        String email = customerUpdateEmail.getText();
        String phone = customerUpdatePhone.getText();
        String error;

        String query = "UPDATE customer SET name = '" + name + "', address = '" + address + "', email = '" + email + "', phoneNumber = '" + phone + "' WHERE customerID = '" + ID + "';";
        try {
            Connection con = credentials.getConnection();
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
            con.close();
            customerUpdateResult.setText("Customer with ID " + ID + " successfully updated");
        } catch (SQLException e) {
            error = e.getMessage();
            customerUpdateResult.setText(error);
        }
    }
    public void attemptCustomerDelete() {
        String ID = customerUpdateID.getText();
        String query = "DELETE FROM customer WHERE customerID = '" + ID + "';";
        try {
            Connection con = credentials.getConnection();
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
            con.close();
            customerUpdateResult.setText("Customer with ID " + ID + " successfully deleted");
        } catch (SQLException e) {
            customerUpdateResult.setText(e.getMessage());
        }
    }
    public void updateWarehouse() {
        warehouseUpdate.toFront();
    }
    public void listWarehouseElements(){
        String ID = warehouseUpdateID.getText();
        String query = "SELECT * FROM warehouse WHERE warehouseID = '" + ID + "';";
        try {
            Connection con = credentials.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                warehouseUpdateName.setText(rs.getString("name"));
                warehouseUpdateAddress.setText(rs.getString("address"));
            }
            if(warehouseUpdateAddress.getText().isEmpty()){
                warehouseUpdateResult.setText("Warehouse with ID " + ID + " does not exist");
            }
            con.close();
            warehouseUpdateResult.setText("Warehouse with ID " + ID + " successfully listed");
        } catch (SQLException e) {
            warehouseUpdateResult.setText(e.getMessage());
        }
    }
    public void updateWarehouseClear() {
        warehouseUpdateID.clear();
        warehouseUpdateName.clear();
        warehouseUpdateAddress.clear();
        warehouseUpdateResult.setText("");
    }
    public void attemptWarehouseUpdate() {
        String ID = warehouseUpdateID.getText();
        String name = warehouseUpdateName.getText();
        String address = warehouseUpdateAddress.getText();
        String error;

        String query = "UPDATE warehouse SET name = '" + name + "', address = '" + address + "' WHERE warehouseID = '" + ID + "';";
        try {
            Connection con = credentials.getConnection();
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
            con.close();
            warehouseUpdateResult.setText("Warehouse with ID " + ID + " successfully updated");
        } catch (SQLException e) {
            error = e.getMessage();
            warehouseUpdateResult.setText(error);
        }
    }
    public void attemptWarehouseDelete() {
        String ID = warehouseUpdateID.getText();
        String query2 = "DELETE FROM warehouse WHERE warehouseID = '" + ID + "';";
        try {
            Connection con = credentials.getConnection();
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query2);
            con.close();
            warehouseUpdateResult.setText("Warehouse with ID " + ID + " successfully deleted");
        } catch (SQLException e) {
            warehouseUpdateResult.setText(e.getMessage());
        }
    }
    public void updateAuthor() {
        authorUpdate.toFront();
    }
    public void listAuthorElements(){
        String ID = authorUpdateID.getText();
        String query = "SELECT * FROM author WHERE authorID = '" + ID + "';";
        try {
            Connection con = credentials.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                authorUpdateName.setText(rs.getString("name"));
                authorUpdateEmail.setText(rs.getString("email"));
            }
            if(authorUpdateName.getText().isEmpty()){
                authorUpdateResult.setText("Author with ID " + ID + " does not exist");
            }
            con.close();
            authorUpdateResult.setText("Author with ID " + ID + " successfully listed");
        } catch (SQLException e) {
            authorUpdateResult.setText(e.getMessage());
        }
    }
    public void updateAuthorClear() {
        authorUpdateID.clear();
        authorUpdateName.clear();
        authorUpdateEmail.clear();
        authorUpdateResult.setText("");
    }
    public void attemptAuthorUpdate() {
        String ID = authorUpdateID.getText();
        String name = authorUpdateName.getText();
        String email = authorUpdateEmail.getText();
        String error;

        String query = "UPDATE author SET name = '" + name + "', email = '" + email + "' WHERE authorID = '" + ID + "';";
        try {
            Connection con = credentials.getConnection();
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
            con.close();
            authorUpdateResult.setText("Author with ID " + ID + " successfully updated");
        } catch (SQLException e) {
            error = e.getMessage();
            authorUpdateResult.setText(error);
        }
    }
    public void attemptAuthorDelete() {
        String ID = authorUpdateID.getText();
        //null from book table
        String query = "UPDATE book SET authorID = NULL WHERE authorID = '" + ID + "';";
        String query2 = "DELETE FROM author WHERE authorID = '" + ID + "';";
        try {
            Connection con = credentials.getConnection();
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
            stmt.executeUpdate(query2);
            con.close();
            authorUpdateResult.setText("Author with ID " + ID + " successfully deleted");
        } catch (SQLException e) {
            authorUpdateResult.setText(e.getMessage());
        }
    }
    public void updatePublisher() {
        publisherUpdate.toFront();
    }
    public void listPublisherElements(){
        String ID = publisherUpdateID.getText();
        String query = "SELECT * FROM publisher WHERE publisherID = '" + ID + "';";
        try {
            Connection con = credentials.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                publisherUpdateName.setText(rs.getString("name"));
                publisherUpdateAddress.setText(rs.getString("address"));
                publisherUpdateEmail.setText(rs.getString("email"));
                publisherUpdatePhone.setText(rs.getString("phoneNumber"));
            }
            if(publisherUpdateName.getText().isEmpty()){
                publisherUpdateResult.setText("Publisher with ID " + ID + " does not exist");
            }
            con.close();
            publisherUpdateResult.setText("Publisher with ID " + ID + " successfully listed");
        } catch (SQLException e) {
            publisherUpdateResult.setText(e.getMessage());
        }
    }
    public void updatePublisherClear() {
        publisherUpdateID.clear();
        publisherUpdateName.clear();
        publisherUpdateAddress.clear();
        publisherUpdateEmail.clear();
        publisherUpdatePhone.clear();
        publisherUpdateResult.setText("");
    }
    public void attemptPublisherUpdate() {
        String ID = publisherUpdateID.getText();
        String name = publisherUpdateName.getText();
        String address = publisherUpdateAddress.getText();
        String email = publisherUpdateEmail.getText();
        String phone = publisherUpdatePhone.getText();
        String error;

        String query = "UPDATE publisher SET name = '" + name + "', address = '" + address + "', email = '" + email + "', phoneNumber = '" + phone + "' WHERE publisherID = '" + ID + "';";
        try {
            Connection con = credentials.getConnection();
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
            con.close();
            publisherUpdateResult.setText("Publisher with ID " + ID + " successfully updated");
        } catch (SQLException e) {
            error = e.getMessage();
            publisherUpdateResult.setText(error);
        }
    }
    public void attemptPublisherDelete() {
        String ID = publisherUpdateID.getText();
        String query1=  "UPDATE book SET publisherID = NULL WHERE publisherID = '" + ID + "';";
        String query2 = "DELETE FROM publisher WHERE publisherID = '" + ID + "';";
        try {
            Connection con = credentials.getConnection();
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query1);
            stmt.executeUpdate(query2);
            con.close();
            publisherUpdateResult.setText("Publisher with ID " + ID + ", and their books have been successfully deleted");
        } catch (SQLException e) {
            publisherUpdateResult.setText(e.getMessage());
        }
    }
    public void updateOrder() {
        orderUpdate.toFront();
    }
    public void listOrderElements() {
        String ID = orderUpdateID.getText();
        String query = "SELECT * FROM orders WHERE orderID = '" + ID + "';";
        try {
            Connection con = credentials.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String orderStatus = rs.getString("status");
                int orderStatusInt = Integer.parseInt(orderStatus);
                if(orderStatusInt==0){
                    orderUpdateStatus.setText("Pending");
                }
                else if(orderStatusInt==1){
                    orderUpdateStatus.setText("Completed");
                }
            }
            if(orderUpdateID.getText().isEmpty()){
                orderUpdateResult.setText("Order with ID " + ID + " does not exist");
            }
            con.close();
            orderUpdateResult.setText("Order with ID " + ID + " successfully listed");
        } catch (SQLException e) {
            orderUpdateResult.setText(e.getMessage());
        }
    }
    public void updateOrderClear() {
        orderUpdateID.clear();
        orderUpdateStatus.clear();
        orderUpdateResult.setText("");
    }
    public void attemptOrderUpdate() {
        String ID = orderUpdateID.getText();
        String statusTemp = orderUpdateStatus.getText();
        String status;
        String error;
        if(statusTemp.equals("Pending")){
            status = "0";
        }
        else if(statusTemp.equals("Completed")){
            status = "1";
        }
        else{
            orderUpdateResult.setText("Invalid status");
            return;
        }

        String query = "UPDATE orderlogistics SET status = '" + status + "' WHERE orderID = '" + ID + "';";
        try {
            Connection con = credentials.getConnection();
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query);
            con.close();
            orderUpdateResult.setText("Order with ID " + ID + " successfully updated");
        } catch (SQLException e) {
            error = e.getMessage();
            orderUpdateResult.setText(error);
        }
    }
    public void attemptOrderDelete() {
        String ID = orderUpdateID.getText();
        String query1 = "DELETE FROM orders WHERE orderID = '" + ID + "';";
        String query = "DELETE FROM orderlogistics WHERE orderID = '" + ID + "';";
        try {
            Connection con = credentials.getConnection();
            Statement stmt = con.createStatement();
            stmt.executeUpdate(query1);
            stmt.executeUpdate(query);
            con.close();
            orderUpdateResult.setText("Order with ID " + ID + " successfully deleted");
        } catch (SQLException e) {
            orderUpdateResult.setText(e.getMessage());
        }
    }
}
