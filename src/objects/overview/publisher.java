package objects.overview;

public record publisher(String ID, String name, String email,
                        String phone, String address, String publishedBooks,
                        String copiesSold, String totalRevenue) {}