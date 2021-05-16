package NhatKy;

/**
 *
 * @author Administrator
 */
public class NguoiDung {
    String TaiKhoan;
    String MạtKhau;
    String MạtKhauMoi;

    public NguoiDung() {
    }

    public NguoiDung(String TaiKhoan, String MạtKhau) {
        this.TaiKhoan = TaiKhoan;
        this.MạtKhau = MạtKhau;
    }

    public NguoiDung(String TaiKhoan, String MạtKhau, String MạtKhauMoi) {
        this.TaiKhoan = TaiKhoan;
        this.MạtKhau = MạtKhau;
        this.MạtKhauMoi = MạtKhauMoi;
    }

    public String getMạtKhauMoi() {
        return MạtKhauMoi;
    }

    public void setMạtKhauMoi(String MạtKhauMoi) {
        this.MạtKhauMoi = MạtKhauMoi;
    }

    public String getTaiKhoan() {
        return TaiKhoan;
    }

    public void setTaiKhoan(String TaiKhoan) {
        this.TaiKhoan = TaiKhoan;
    }

    public String getMạtKhau() {
        return MạtKhau;
    }

    public void setMạtKhau(String MạtKhau) {
        this.MạtKhau = MạtKhau;
    }

    @Override
    public String toString() {
        return "NguoiDung{" + "TaiKhoan=" + TaiKhoan + ", M\u1ea1tKhau=" + MạtKhau + ", M\u1ea1tKhauMoi=" + MạtKhauMoi + '}';
    }
    
}
