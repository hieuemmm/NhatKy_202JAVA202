package DAO;

import NhatKy.NguoiDung;
import java.sql.SQLException;

/**
 *
 * @author Administrator
 */
public class NguoiDungServices {

    private final NguoiDungDAO nguoidungDAO;

    public NguoiDungServices() {
        nguoidungDAO = new NguoiDungDAO();
    }

    public boolean DangKyTaiKhoan(NguoiDung nguoidung) throws ClassNotFoundException, SQLException {
        if (nguoidungDAO.CheckNguoiDungTonTai(nguoidung)) {
            return false; 
        }else if (nguoidungDAO.addNguoiDung(nguoidung)) {
            return true;
        }
        return false;
    }

    public boolean CheckDangNhap(NguoiDung nguoidung) throws ClassNotFoundException, SQLException {
        return nguoidungDAO.CheckNguoiDungChinhXac(nguoidung);
    }

    public boolean DoiMatKhau(NguoiDung nguoidung) throws ClassNotFoundException, SQLException {
        return nguoidungDAO.updateNguoiDung(nguoidung);
    }
}
