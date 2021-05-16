package DAO;

import NhatKy.NguoiDung;
import NhatKy.ThuMuc;
import NhatKy.NhatKy;
import java.sql.SQLException;
import java.util.List;
/**
 *
 * @author Administrator
 */
public class NhatKyServices {

    private final NhatKyDAO nhatkyDAO;

    public NhatKyServices() {
        nhatkyDAO = new NhatKyDAO();
    }

    public List<NhatKy> getAllNhatKyByMaThuMuc(ThuMuc thumuc)throws ClassNotFoundException, SQLException{
        return nhatkyDAO.getAllNhatKyByMaThuMuc(thumuc);
    }
    public List<NhatKy> getAllNhatKyByTaiKhoan(NguoiDung nguoidung)throws ClassNotFoundException, SQLException{
        return nhatkyDAO.getAllNhatKyByTaiKhoan(nguoidung);
    }
    public NhatKy getNhatKyByMaNhatKy(int MaNhatKy)throws ClassNotFoundException, SQLException{
        return nhatkyDAO.getNhatKyByMaNhatKy(MaNhatKy);
    }
    public boolean KiemTraNhatKyTonTai(NhatKy NK,NguoiDung ND)throws ClassNotFoundException, SQLException{
        return nhatkyDAO.getNhatKyByTenNhatKy(NK,ND);
    }
    public boolean ThemNhatKyMoi(NhatKy NK)throws ClassNotFoundException, SQLException{
        return nhatkyDAO.addNhatKy(NK);
    }
    public boolean XoaMotNhatKy(NhatKy NK)throws ClassNotFoundException, SQLException{
        return nhatkyDAO.DeleteNhatKy(NK);
    }
    public boolean SuaNhatKy(NhatKy NK)throws ClassNotFoundException, SQLException{
        return nhatkyDAO.updateNhatKy(NK);
    }
}
