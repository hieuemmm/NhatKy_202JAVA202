package DAO;

import NhatKy.NguoiDung;
import NhatKy.ThuMuc;
import java.sql.SQLException;
import java.util.List;
/**
 *
 * @author Administrator
 */
public class ThuMucServices {

    private final ThuMucDAO thumucDAO;

    public ThuMucServices() {
        thumucDAO = new ThuMucDAO();
    }

    public List<ThuMuc> getAllThuMuc(NguoiDung nguoidung)throws ClassNotFoundException, SQLException{
        return thumucDAO.getAllThuMucByTaiKhoan(nguoidung);
    }
    public ThuMuc getThuMucByID(int MaThuMuc)throws ClassNotFoundException, SQLException{
        return thumucDAO.getThuMucByMaThuMuc(MaThuMuc);
    }
    public Boolean KiemTraThuMucTonTai(ThuMuc thumuc)throws ClassNotFoundException, SQLException{
        return thumucDAO.CheckThuMucTonTai(thumuc);
    }
    public Boolean ThemThuMuc(ThuMuc thumuc)throws ClassNotFoundException, SQLException{
        return thumucDAO.AddThuMuc(thumuc);
    }
    public Boolean DoiTenThuMuc(ThuMuc thumuc)throws ClassNotFoundException, SQLException{
        return thumucDAO.UpdateThuMuc(thumuc);
    }
}
