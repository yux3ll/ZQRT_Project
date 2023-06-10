package objects;

public record order(String ID,String customerName, String date,
                    String status, String amountOfBooks,
                    String totalPriceOfOrder, String booksInOrder){}