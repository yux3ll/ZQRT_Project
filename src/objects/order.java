package objects;

public record order(String ID,String customerName, String date,
                    String status, String numberOfBooks,
                    String totalPrice){}