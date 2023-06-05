package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class bookDefault {
    @FXML
    private Label ISBN;
    @FXML
    private Label Title;
    @FXML
    private Label Author;
    @FXML
    private Label Genre;
    @FXML
    private Label Price;
    @FXML
    private Label Stock;
    @FXML
    private Label Pages;
    @FXML
    private Label Publisher;
    @FXML
    private Label releaseDate;

    //set text for all labels
    public void setISBN(String isbn){
        ISBN.setText(isbn);
    }
    public void setTitle(String title) {
        Title.setText(title);
    }

    public void setAuthor(String author) {
        Author.setText(author);
    }

    public void setGenre(String genre) {
        Genre.setText(genre);
    }

    public void setPrice(String price) {
        Price.setText(price);
    }

    public void setStock(String stock) {
        Stock.setText(stock);
    }

    public void setPages(String pages) {
        Pages.setText(pages);
    }

    public void setPublisher(String publisher) {
        Publisher.setText(publisher);
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate.setText(releaseDate);
    }

}
