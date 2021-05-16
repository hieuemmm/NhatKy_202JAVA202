package DAO;

import static NhatKy.ConnectMySQL.getJDBCConnection;
import NhatKy.NguoiDung;
import NhatKy.ThuMuc;
import com.mysql.jdbc.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ThuMucDAO {
    //load ra giao diện
    public List<ThuMuc> getAllThuMucByTaiKhoan(NguoiDung nguoidung) throws ClassNotFoundException, SQLException {
        List<ThuMuc> ThuMucs = new ArrayList<>();
        Connection connection = getJDBCConnection();
        String Sql = "SELECT * FROM ThuMuc WHERE TaiKhoan = ?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Sql);
            preparedStatement.setString(1, nguoidung.getTaiKhoan());
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                ThuMuc thumuc = new ThuMuc();
                thumuc.setMaThuMuc(rs.getInt("MaThuMuc"));
                thumuc.setTaiKhoan(rs.getString("TaiKhoan"));
                thumuc.setTenThuMuc(rs.getString("TenThuMuc"));
                ThuMucs.add(thumuc);
            }
        } catch (SQLException e) {
        }
        return ThuMucs;
    }
    //Lấy thông tin của thư mục
    public ThuMuc getThuMucByMaThuMuc(int MaThuMuc) throws ClassNotFoundException, SQLException {
        Connection connection = getJDBCConnection();
        String Sql = "SELECT * FROM ThuMuc WHERE MaThuMuc = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Sql);
            preparedStatement.setInt(1, MaThuMuc);
            ResultSet rs = preparedStatement.executeQuery();
            rs.first();
            ThuMuc thumuc = new ThuMuc();
            thumuc.setMaThuMuc(rs.getInt("MaThuMuc"));
            thumuc.setTaiKhoan(rs.getString("TaiKhoan"));
            thumuc.setTenThuMuc(rs.getString("TenThuMuc"));
            return thumuc;
        } catch (SQLException e) {
        }
        return null;
    }
//    //TOI DAY ROI
    public boolean CheckThuMucTonTai(ThuMuc thumuc) throws ClassNotFoundException, SQLException {
        Connection connection = getJDBCConnection();
        String Sql = "SELECT count(*) AS DemThuMuc FROM ThuMuc WHERE TenThuMuc = ? AND TaiKhoan= ?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Sql);
            preparedStatement.setString(1, thumuc.getTenThuMuc());
            preparedStatement.setString(2, thumuc.getTaiKhoan());
            ResultSet rs = preparedStatement.executeQuery();
            rs.first();
            if (rs.getInt("DemThuMuc") > 0) {
                return true;
            }
        } catch (SQLException e) {
        }
        return false;
    }
    public boolean AddThuMuc(ThuMuc thumuc) throws ClassNotFoundException, SQLException {
        Connection connection = getJDBCConnection();
        String Sql = "INSERT INTO ThuMuc(TaiKhoan,TenThuMuc) VALUES (?, ?);";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Sql);
            preparedStatement.setString(1, thumuc.getTaiKhoan());
            preparedStatement.setString(2, thumuc.getTenThuMuc());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
        }
        return false;
    }

    public boolean UpdateThuMuc(ThuMuc thumuc) throws ClassNotFoundException, SQLException {
        Connection connection = getJDBCConnection();
        String Sql = "UPDATE ThuMuc SET TenThuMuc = ? WHERE MaThuMuc = ? AND TaiKhoan = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Sql);
            preparedStatement.setString(1, thumuc.getTenThuMuc());
            preparedStatement.setInt(2, thumuc.getMaThuMuc());
            preparedStatement.setString(3, thumuc.getTaiKhoan());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
        }
        return false;
    }
//
//    public boolean DeleteThuMuc(int MaThuMuc,NguoiDung nguoidung) throws ClassNotFoundException, SQLException {
//        Connection connection = getJDBCConnection();
//        String Sql = "DELETE FROM ThuMuc WHERE MaThuMuc = ? AND TaiKhoan = ?";
//        try {
//            PreparedStatement preparedStatement = connection.prepareStatement(Sql);
//            preparedStatement.setInt(1, MaThuMuc);
//            preparedStatement.setString(2, nguoidung.getTaiKhoan());
//            preparedStatement.executeUpdate();
//            return true;
//        } catch (SQLException e) {
//        }
//        return false;
//    }
}
