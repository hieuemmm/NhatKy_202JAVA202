package Models;

import static Core.ConnectMySQL.getJDBCConnection;
import Class.NguoiDung;
import Class.NhatKy;
import Class.ThuMuc;
import com.mysql.jdbc.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class NhatKyModel {

    public List<NhatKy> getAllNhatKyByMaThuMuc(ThuMuc thumuc) throws ClassNotFoundException, SQLException {
        List<NhatKy> NhatKys = new ArrayList<NhatKy>();
        Connection connection = getJDBCConnection();
        String Sql = "SELECT * FROM NhatKy WHERE MaThuMuc = ?;";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(Sql);
            preparedStatement.setInt(1, thumuc.getMaThuMuc());
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                NhatKy nhatky = new NhatKy();
                nhatky.setMaNhatKy(rs.getInt("MaNhatKy"));
                nhatky.setMaThuMuc(rs.getInt("MaThuMuc"));
                nhatky.setTenNhatKy(rs.getString("TenNhatKy"));
                nhatky.setNgayTao(rs.getString("NgayTao"));
                nhatky.setNgayChinhSuaCuoiCung(rs.getString("NgayChinhSuaCuoiCung"));
                nhatky.setNoiDung(rs.getString("NoiDung"));
                NhatKys.add(nhatky);
            }
        } catch (SQLException e) {
        }
        return NhatKys;
    }

    public List<NhatKy> getAllNhatKyByTaiKhoan(NguoiDung nguoidung) throws ClassNotFoundException, SQLException {
        List<NhatKy> NhatKys = new ArrayList<>();
        Connection connection = getJDBCConnection();
        String Sql = "SELECT NhatKy.* FROM NguoiDung"
                + " INNER JOIN ThuMuc ON NguoiDung.TaiKhoan = ThuMuc.TaiKhoan"
                + " INNER JOIN NhatKy ON NhatKy.MaThuMuc = ThuMuc.MaThuMuc"
                + " WHERE NguoiDung.TaiKhoan = ? ;";
        PreparedStatement preparedStatement = connection.prepareStatement(Sql);
        preparedStatement.setString(1, nguoidung.getTaiKhoan());
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            NhatKy nhatky = new NhatKy();
            nhatky.setMaNhatKy(rs.getInt("MaNhatKy"));
            nhatky.setMaThuMuc(rs.getInt("MaThuMuc"));
            nhatky.setTenNhatKy(rs.getString("TenNhatKy"));
            nhatky.setNgayTao(rs.getString("NgayTao"));
            nhatky.setNgayChinhSuaCuoiCung(rs.getString("NgayChinhSuaCuoiCung"));
            nhatky.setNoiDung(rs.getString("NoiDung"));
            NhatKys.add(nhatky);
        }
        return NhatKys;
    }

    public NhatKy getNhatKyByMaNhatKy(int MNK) throws ClassNotFoundException, SQLException {
        Connection connection = getJDBCConnection();
        String Sql = "SELECT * FROM NhatKy WHERE MaNhatKy = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(Sql);
        preparedStatement.setInt(1, MNK);
        ResultSet rs = preparedStatement.executeQuery();
        rs.first();
        NhatKy nhatky = new NhatKy();
        nhatky.setMaNhatKy(rs.getInt("MaNhatKy"));
        nhatky.setMaThuMuc(rs.getInt("MaThuMuc"));
        nhatky.setTenNhatKy(rs.getString("TenNhatKy"));
        nhatky.setNgayTao(rs.getString("NgayTao"));
        nhatky.setNgayChinhSuaCuoiCung(rs.getString("NgayChinhSuaCuoiCung"));
        nhatky.setNoiDung(rs.getString("NoiDung"));
        return nhatky;
    }

    public NhatKy getNhatKyByTenNhatKy(String TenNhatKy) throws ClassNotFoundException, SQLException {
        Connection connection = getJDBCConnection();
        String Sql = "SELECT * FROM NhatKy WHERE TenNhatKy = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(Sql);
        preparedStatement.setString(1, TenNhatKy);
        ResultSet rs = preparedStatement.executeQuery();
        rs.first();
        NhatKy nhatky = new NhatKy();
        nhatky.setMaNhatKy(rs.getInt("MaNhatKy"));
        nhatky.setMaThuMuc(rs.getInt("MaThuMuc"));
        nhatky.setTenNhatKy(rs.getString("TenNhatKy"));
        nhatky.setNgayTao(rs.getString("NgayTao"));
        nhatky.setNgayChinhSuaCuoiCung(rs.getString("NgayChinhSuaCuoiCung"));
        nhatky.setNoiDung(rs.getString("NoiDung"));
        return nhatky;
    }

    public boolean getNhatKyByTenNhatKy(NhatKy NK, NguoiDung ND) throws ClassNotFoundException, SQLException {
        Connection connection = getJDBCConnection();
        String Sql = "SELECT count(*) AS DemNhatKy FROM NguoiDung"
                + " INNER JOIN ThuMuc ON NguoiDung.TaiKhoan = ThuMuc.TaiKhoan"
                + " INNER JOIN NhatKy ON NhatKy.MaThuMuc = ThuMuc.MaThuMuc"
                + " WHERE NhatKy.TenNhatKy = ? AND NguoiDung.TaiKhoan = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(Sql);
        preparedStatement.setString(1, NK.getTenNhatKy());
        preparedStatement.setString(2, ND.getTaiKhoan());
        ResultSet rs = preparedStatement.executeQuery();
        rs.first();
        if (rs.getInt("DemNhatKy") > 0) {
            return true;
        }
        return false;
    }

    public boolean addNhatKy(NhatKy nhatky) throws ClassNotFoundException, SQLException {
        Connection connection = getJDBCConnection();
        String Sql = "INSERT INTO NhatKy (`MaThuMuc`, `TenNhatKy`, `NgayTao`,`NgayChinhSuaCuoiCung`,`NoiDung`) VALUES (?, ?, ?, ?, ?);";
        PreparedStatement preparedStatement = connection.prepareStatement(Sql);
        preparedStatement.setInt(1, nhatky.getMaThuMuc());
        preparedStatement.setString(2, nhatky.getTenNhatKy());
        preparedStatement.setString(3, nhatky.getNgayTao());
        preparedStatement.setString(4, nhatky.getNgayChinhSuaCuoiCung());
        preparedStatement.setString(5, nhatky.getNoiDung());
        preparedStatement.executeUpdate();
        return true;
    }

    public boolean updateNhatKy(NhatKy nhatky) throws ClassNotFoundException, SQLException {
        Connection connection = getJDBCConnection();
        String Sql = "UPDATE NhatKy SET TenNhatKy = ?, NgayChinhSuaCuoiCung = ?,NoiDung = ?WHERE MaNhatKy = ? ;";
        PreparedStatement preparedStatement = connection.prepareStatement(Sql);
        preparedStatement.setString(1, nhatky.getTenNhatKy());
        preparedStatement.setString(2, nhatky.getNgayChinhSuaCuoiCung());
        preparedStatement.setString(3, nhatky.getNoiDung());
        preparedStatement.setInt(4, nhatky.getMaNhatKy());
        preparedStatement.executeUpdate();
        return true;
    }

    public boolean DeleteNhatKy(NhatKy NK) throws ClassNotFoundException, SQLException {
        Connection connection = getJDBCConnection();
        String Sql = "DELETE FROM NhatKy WHERE MaNhatKy = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(Sql);
        preparedStatement.setInt(1, NK.getMaNhatKy());
        preparedStatement.executeUpdate();
        return true;
    }

    public boolean DeleteNhatKyByMaThuMuc(ThuMuc thumuc) throws ClassNotFoundException, SQLException {
        Connection connection = getJDBCConnection();
        String Sql = "DELETE FROM NhatKy WHERE MaThuMuc = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(Sql);
        preparedStatement.setInt(1, thumuc.getMaThuMuc());
        preparedStatement.executeUpdate();
        return true;
    }
}
