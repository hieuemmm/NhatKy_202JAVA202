package Class;

/**
 *
 * @author Administrator
 */
public class ThuMuc {

    int MaThuMuc = -1;
    String TaiKhoan;
    String TenThuMuc;

    public ThuMuc() {
    }

    public ThuMuc(int MaThuMuc, String TaiKhoan, String TenThuMuc) {
        this.MaThuMuc = MaThuMuc;
        this.TaiKhoan = TaiKhoan;
        this.TenThuMuc = TenThuMuc;
    }

    public int getMaThuMuc() {
        return MaThuMuc;
    }

    public void setMaThuMuc(int MaThuMuc) {
        this.MaThuMuc = MaThuMuc;
    }

    public String getTaiKhoan() {
        return TaiKhoan;
    }

    public void setTaiKhoan(String TaiKhoan) {
        this.TaiKhoan = TaiKhoan;
    }

    public String getTenThuMuc() {
        return TenThuMuc;
    }

    public void setTenThuMuc(String TenThuMuc) {
        this.TenThuMuc = TenThuMuc;
    }

}
