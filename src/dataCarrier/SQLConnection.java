package dataCarrier;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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

    public void resetCredentials(){
        this.username = null;
        this.password = null;
    }
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/ZQRT", username, password);
    }
}
