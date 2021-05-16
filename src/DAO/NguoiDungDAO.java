package DAO;

import static NhatKy.ConnectMySQL.getJDBCConnection;
import NhatKy.NguoiDung;
import com.mysql.jdbc.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NguoiDungDAO {

//    public List<NguoiDung> getAllNguoiDung() throws ClassNotFoundException, SQLException {
//        List<NguoiDung> NguoiDungs = new ArrayList<>();
//        Connection connection = getJDBCConnection();
//        String Sql = "SELECT * FROM NguoiDung;";
//        try {
//            PreparedStatement preparedStatement = connection.prepareStatement(Sql);
//            ResultSet rs = preparedStatement.executeQuery();
//            while (rs.next()) {
//                NguoiDung nguoidung = new NguoiDung();
//                nguoidung.setTaiKhoan(rs.getString("TaiKhoan"));
//                nguoidung.setMạtKhau(rs.getString("MatKhau"));
//                NguoiDungs.add(nguoidung);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return NguoiDungs;
//    }
//
//    public NguoiDung getNguoiDungByTaiKhoan(String TaiKhoan) throws ClassNotFoundException, SQLException {
//        Connection connection = getJDBCConnection();
//        String Sql = "SELECT * FROM NguoiDung WHERE TaiKhoan = ?";
//        try {
//            PreparedStatement preparedStatement = connection.prepareStatement(Sql);
//            preparedStatement.setString(1, TaiKhoan);
//            ResultSet rs = preparedStatement.executeQuery();
//            rs.first();
//            NguoiDung nguoidung = new NguoiDung();
//            nguoidung.setTaiKhoan(rs.getString("TaiKhoan"));
//            nguoidung.setMạtKhau(rs.getString("MatKhau"));
//            return nguoidung;
//        } catch (SQLException e) {
//        }
//        return null;
//    }
    public boolean CheckNguoiDungChinhXac(NguoiDung nguoidung) throws ClassNotFoundException, SQLException {
        Connection connection = getJDBCConnection();
        String Sql = "SELECT count(*) AS DemTaiKhoan FROM NguoiDung WHERE TaiKhoan = ? AND MatKhau =?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Sql);
            preparedStatement.setString(1, nguoidung.getTaiKhoan());
            preparedStatement.setString(2, nguoidung.getMạtKhau());
            ResultSet rs = preparedStatement.executeQuery();
            rs.first();
            if (rs.getInt("DemTaiKhoan") > 0) {
                return true;
            }
        } catch (SQLException e) {
        }
        return false;
    }
    public boolean CheckNguoiDungTonTai(NguoiDung nguoidung) throws ClassNotFoundException, SQLException {
        Connection connection = getJDBCConnection();
        String Sql = "SELECT count(*) AS DemTaiKhoan FROM NguoiDung WHERE TaiKhoan = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Sql);
            preparedStatement.setString(1, nguoidung.getTaiKhoan());
            ResultSet rs = preparedStatement.executeQuery();
            rs.first();
            if (rs.getInt("DemTaiKhoan") == 1) {
                return true;
            }
        } catch (SQLException e) {
        }
        return false;
    }
    public boolean addNguoiDung(NguoiDung nguoidung) throws ClassNotFoundException, SQLException {
        Connection connection = getJDBCConnection();
        String Sql = "INSERT INTO NguoiDung VALUES (?, ?);";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Sql);
            preparedStatement.setString(1, nguoidung.getTaiKhoan());
            preparedStatement.setString(2, nguoidung.getMạtKhau());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
        }
        return false;
    }
    public boolean updateNguoiDung(NguoiDung nguoidung) throws ClassNotFoundException, SQLException {
        Connection connection = getJDBCConnection();
        String Sql = "UPDATE NguoiDung SET MatKhau = ? WHERE TaiKhoan = ? AND MatKhau = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Sql);
            preparedStatement.setString(1, nguoidung.getMạtKhauMoi());
            preparedStatement.setString(2, nguoidung.getTaiKhoan());
            preparedStatement.setString(3, nguoidung.getMạtKhau());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
        }
        return false;
    }
}
