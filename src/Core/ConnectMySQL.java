package Core;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;

public class ConnectMySQL {
    public static String url;
    public static String user;
    public static String password;
    public ConnectMySQL() {
    }

    public static Connection getJDBCConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        
        Connection connection = (Connection) DriverManager.getConnection(url, user, password);
        return connection;
    }
    public static boolean CheckConnect() throws ClassNotFoundException, SQLException {
        if (getJDBCConnection()!= null){
            return true;
        } 
        return false;
    }
}
