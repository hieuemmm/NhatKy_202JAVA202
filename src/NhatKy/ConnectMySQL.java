package NhatKy;

import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;

public class ConnectMySQL {

    public ConnectMySQL() {
    }

    public static Connection getJDBCConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/Diary?useSSL=false";
        String user = "root";
        String password = "";
        Connection connection = (Connection) DriverManager.getConnection(url, user, password);
        return connection;
    }
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        if (getJDBCConnection()!= null){
            System.err.println("Ket noi Thanh cong");
        } else {
            System.err.println("Ket noi That Bai");
        }
    }
}
