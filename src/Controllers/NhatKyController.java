package Controllers;

import Models.NhatKyModel;
import Class.NguoiDung;
import Class.ThuMuc;
import Class.NhatKy;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Administrator
 */
public class NhatKyController {

    private final NhatKyModel nhatkyDAO;

    public NhatKyController() {
        nhatkyDAO = new NhatKyModel();
    }

    public List<NhatKy> getAllNhatKyByMaThuMuc(ThuMuc thumuc) throws ClassNotFoundException, SQLException {
        return nhatkyDAO.getAllNhatKyByMaThuMuc(thumuc);
    }

    public List<NhatKy> getAllNhatKyByTaiKhoan(NguoiDung nguoidung) throws ClassNotFoundException, SQLException {
        return nhatkyDAO.getAllNhatKyByTaiKhoan(nguoidung);
    }

    public NhatKy getNhatKyByMaNhatKy(int MaNhatKy) throws ClassNotFoundException, SQLException {
        return nhatkyDAO.getNhatKyByMaNhatKy(MaNhatKy);
    }
    public NhatKy getNhatKyByTenNhatKy(String TenNhatKy) throws ClassNotFoundException, SQLException {
        return nhatkyDAO.getNhatKyByTenNhatKy(TenNhatKy);
    }

    public boolean KiemTraNhatKyTonTai(NhatKy NK, NguoiDung ND) throws ClassNotFoundException, SQLException {
        return nhatkyDAO.getNhatKyByTenNhatKy(NK, ND);
    }

    public boolean ThemNhatKyMoi(NhatKy NK) throws ClassNotFoundException, SQLException {
        return nhatkyDAO.addNhatKy(NK);
    }

    public boolean XoaMotNhatKy(NhatKy NK) throws ClassNotFoundException, SQLException {
        return nhatkyDAO.DeleteNhatKy(NK);
    }

    public boolean XoaNhieuNhatKy(ThuMuc TM) throws ClassNotFoundException, SQLException {
        return nhatkyDAO.DeleteNhatKyByMaThuMuc(TM);
    }

    public boolean SuaNhatKy(NhatKy NK) throws ClassNotFoundException, SQLException {
        return nhatkyDAO.updateNhatKy(NK);
    }

    public List<NhatKy> TimKiemNangCao(String TuKhoa, NguoiDung nguoidung) throws ClassNotFoundException, SQLException {
        List<NhatKy> NhatKyTimThay = new ArrayList<>();
        List<NhatKy> NhatKys = getAllNhatKyByTaiKhoan(nguoidung);
        NhatKys.stream().filter(nhatky -> (nhatky.getTenNhatKy().contains(TuKhoa) ||nhatky.getNoiDung().contains(TuKhoa) ||nhatky.getNgayTao().contains(TuKhoa) ||nhatky.getNgayChinhSuaCuoiCung().contains(TuKhoa) )).forEachOrdered(nhatky -> {
            NhatKyTimThay.add(nhatky);
        });
        return NhatKyTimThay;
    }
}
