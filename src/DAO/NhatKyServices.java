package DAO;

import NhatKy.NguoiDung;
import NhatKy.ThuMuc;
import NhatKy.NhatKy;
import java.sql.SQLException;
import java.util.ArrayList;
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
        List<NhatKy> NhatKyTimThay = new ArrayList<NhatKy>();
        List<NhatKy> NhatKys = getAllNhatKyByTaiKhoan(nguoidung);
        for (NhatKy nhatky : NhatKys) {
            if (nhatky.getTenNhatKy().indexOf(TuKhoa)!=-1 ||nhatky.getNoiDung().indexOf(TuKhoa)!=-1 ||nhatky.getNgayTao().indexOf(TuKhoa)!=-1 ||nhatky.getNgayChinhSuaCuoiCung().indexOf(TuKhoa)!=-1 ){
                NhatKyTimThay.add(nhatky);
            }   
        }
        return NhatKyTimThay;
    }

    private int DemTu(String Root, String Sub) {
        int lastIndex = 0;
        int count = 0;
        while (lastIndex != -1) {
            lastIndex = Root.indexOf(Sub, lastIndex);
            if (lastIndex != -1) {
                count++;
                lastIndex += Sub.length();
            }
        }
        return count;
    }
}
