package objects;

import javafx.beans.value.ObservableValue;

public class book {
    private String ISBN, title,authorName, publisherName,price, numberOfPages, stockAmount,releaseDate, genre;

    public book(String ISBN, String title, String authorName, String publisherName, String price, String numberOfPages, String stockAmount, String releaseDate, String genre) {
        this.ISBN = ISBN;
        this.title = title;
        this.authorName = authorName;
        this.publisherName = publisherName;
        this.price = price;
        this.numberOfPages = numberOfPages;
        this.stockAmount = stockAmount;
        this.releaseDate = releaseDate;
        this.genre = genre;
    }

    public String getISBN() {
        return ISBN;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public String getPrice() {
        return price;
    }

    public String getNumberOfPages() {
        return numberOfPages;
    }

    public String getStockAmount() {
        return stockAmount;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getGenre() {
        return genre;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setNumberOfPages(String numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public void setStockAmount(String stockAmount) {
        this.stockAmount = stockAmount;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }


}
