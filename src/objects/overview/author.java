package objects.overview;

public record author(String ID, String name, String email,
                     String publishedBooks, String copiesSold, String totalRevenue, String avgPage) {}