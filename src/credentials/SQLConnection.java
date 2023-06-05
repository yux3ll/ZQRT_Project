package credentials;

public class SQLConnection {
    private static final SQLConnection instance = new SQLConnection();
    private String username;
    private String password;

    private SQLConnection(){}

    public static SQLConnection getInstance() {
        return instance;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
