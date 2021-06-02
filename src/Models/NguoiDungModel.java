package Models;

import static Core.ConnectMySQL.getJDBCConnection;
import Class.NguoiDung;
import com.mysql.jdbc.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NguoiDungModel {

    public boolean CheckNguoiDungChinhXac(NguoiDung nguoidung) throws ClassNotFoundException, SQLException {
        Connection connection = getJDBCConnection();
        String Sql = "SELECT count(*) AS DemTaiKhoan FROM NguoiDung WHERE TaiKhoan = ? AND MatKhau =?";
        PreparedStatement preparedStatement = connection.prepareStatement(Sql);
        preparedStatement.setString(1, nguoidung.getTaiKhoan());
        preparedStatement.setString(2, nguoidung.getMạtKhau());
        ResultSet rs = preparedStatement.executeQuery();
        rs.first();
        if (rs.getInt("DemTaiKhoan") > 0) {
            return true;
        }
        return false;
    }

    public boolean CheckNguoiDungTonTai(NguoiDung nguoidung) throws ClassNotFoundException, SQLException {
        Connection connection = getJDBCConnection();
        String Sql = "SELECT count(*) AS DemTaiKhoan FROM NguoiDung WHERE TaiKhoan = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(Sql);
        preparedStatement.setString(1, nguoidung.getTaiKhoan());
        ResultSet rs = preparedStatement.executeQuery();
        rs.first();
        if (rs.getInt("DemTaiKhoan") == 1) {
            return true;
        }
        return false;
    }

    public boolean addNguoiDung(NguoiDung nguoidung) throws ClassNotFoundException, SQLException {
        Connection connection = getJDBCConnection();
        String Sql = "INSERT INTO NguoiDung VALUES (?, ?);";
        PreparedStatement preparedStatement = connection.prepareStatement(Sql);
        preparedStatement.setString(1, nguoidung.getTaiKhoan());
        preparedStatement.setString(2, nguoidung.getMạtKhau());
        preparedStatement.executeUpdate();
        return true;
    }

    public boolean updateNguoiDung(NguoiDung nguoidung) throws ClassNotFoundException, SQLException {
        Connection connection = getJDBCConnection();
        String Sql = "UPDATE NguoiDung SET MatKhau = ? WHERE TaiKhoan = ? AND MatKhau = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(Sql);
        preparedStatement.setString(1, nguoidung.getMạtKhauMoi());
        preparedStatement.setString(2, nguoidung.getTaiKhoan());
        preparedStatement.setString(3, nguoidung.getMạtKhau());
        preparedStatement.executeUpdate();
        return true;
    }
}
