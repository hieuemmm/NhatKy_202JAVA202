package Controllers;

import Models.NguoiDungModel;
import Class.NguoiDung;
import java.sql.SQLException;

/**
 *
 * @author Administrator
 */
public class NguoiDungController {

    private final NguoiDungModel nguoidungDAO;

    public NguoiDungController() {
        nguoidungDAO = new NguoiDungModel();
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
