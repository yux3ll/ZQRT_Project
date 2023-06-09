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
import java.util.ResourceBundle;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class mainMenu implements Initializable {
    @FXML
    private Pane
            pnlInserts,
            pnlDeletions,
            pnlOverview,
            pnlUpdates,
            pnlStatistics,
            pnlSettings,
            overviewHider,
            bookInsert,
            insertsMain,
            authorInsert,
            publisherInsert,
            warehouseInsert,
            customerInsert,
            orderInsert;
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
            orderInsertResult;
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
            orderTable6;
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
            orderInsertBooks;
    ObservableList<book> bookList= FXCollections.observableArrayList();
    ObservableList<publisher> publisherList= FXCollections.observableArrayList();
    ObservableList<customer> customerList= FXCollections.observableArrayList();
    ObservableList<warehouse> warehouseList= FXCollections.observableArrayList();
    ObservableList<order> orderList= FXCollections.observableArrayList();
    ObservableList<author> authorList= FXCollections.observableArrayList();

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
                SUM(book.price * orders.quantity) AS totalPriceOfOrder
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
                orderlogistics.orderID;""");

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
            orderList.add(new order(ID, customerName, date, status, amountOfBooks, totalPriceOfOrder));
        }
        con.close();
        orderTable1.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().ID()));
        orderTable2.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().customerName()));
        orderTable3.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().date()));
        orderTable4.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().status()));
        orderTable5.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().numberOfBooks()));
        orderTable6.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().totalPrice()));

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
            } else if (order.numberOfBooks().toLowerCase().contains(lowerCaseFilter)) {
                return true;
            } else return order.totalPrice().toLowerCase().contains(lowerCaseFilter);
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

    public void updatesBase() {
        pnlUpdates.toFront();
    }
    public void deletionsBase() {
        pnlDeletions.toFront();
    }
    public void statisticsBase() {
        pnlStatistics.toFront();
    }
    public void settingsBase() {
        pnlSettings.toFront();
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

}
