package NhatKy;

/**
 *
 * @author Administrator
 */
public class NhatKy {

    int MaNhatKy;
    int MaThuMuc;
    String TenNhatKy;
    String NgayTao;
    String NgayChinhSuaCuoiCung;
    String NoiDung;

    public NhatKy() {
    }

    public NhatKy(int MaNhatKy, int MaThuMuc, String TenNhatKy, String NgayTao, String NgayChinhSuaCuoiCung, String NoiDung) {
        this.MaNhatKy = MaNhatKy;
        this.MaThuMuc = MaThuMuc;
        this.TenNhatKy = TenNhatKy;
        this.NgayTao = NgayTao;
        this.NgayChinhSuaCuoiCung = NgayChinhSuaCuoiCung;
        this.NoiDung = NoiDung;
    }

    public int getMaNhatKy() {
        return MaNhatKy;
    }

    public void setMaNhatKy(int MaNhatKy) {
        this.MaNhatKy = MaNhatKy;
    }

    public int getMaThuMuc() {
        return MaThuMuc;
    }

    public void setMaThuMuc(int MaThuMuc) {
        this.MaThuMuc = MaThuMuc;
    }

    public String getTenNhatKy() {
        return TenNhatKy;
    }

    public void setTenNhatKy(String TenNhatKy) {
        this.TenNhatKy = TenNhatKy;
    }

    public String getNgayTao() {
        return NgayTao;
    }

    public void setNgayTao(String NgayTao) {
        this.NgayTao = NgayTao;
    }

    public String getNgayChinhSuaCuoiCung() {
        return NgayChinhSuaCuoiCung;
    }

    public void setNgayChinhSuaCuoiCung(String NgayChinhSuaCuoiCung) {
        this.NgayChinhSuaCuoiCung = NgayChinhSuaCuoiCung;
    }

    public String getNoiDung() {
        return NoiDung;
    }

    public void setNoiDung(String NoiDung) {
        this.NoiDung = NoiDung;
    }

    @Override
    public String toString() {
        return "NhatKy{" + "TenNhatKy=" + TenNhatKy + ", NgayTao=" + NgayTao + ", NgayChinhSuaCuoiCung=" + NgayChinhSuaCuoiCung + '}';
    }

}
