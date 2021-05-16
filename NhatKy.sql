CREATE DATABASE NhatKy;
USE NhatKy;
CREATE TABLE NguoiDung
(
    TaiKhoan NVARCHAR(50) NOT NULL,
    MatKhau NVARCHAR(50),
    PRIMARY KEY(TaiKhoan)
);
CREATE TABLE ThuMuc
(
    MaThuMuc INT AUTO_INCREMENT,
    TaiKhoan NVARCHAR(50),
    TenThuMuc NVARCHAR(50),
    FOREIGN KEY(TaiKhoan) REFERENCES NguoiDung(TaiKhoan),
    PRIMARY KEY(MaThuMuc)
);
CREATE TABLE NhatKy
(
    MaNhatKy INT AUTO_INCREMENT,
    MaThuMuc INT,
    TenNhatKy NVARCHAR(200),
    NgayTao NVARCHAR(50),
    NgayChinhSuaCuoiCung NVARCHAR(50),
    NoiDung LONGTEXT,
    FOREIGN KEY(MaThuMuc) REFERENCES ThuMuc(MaThuMuc),
    PRIMARY KEY(MaNhatKy)
);

INSERT INTO NguoiDung
VALUES 
    ('admin','admin');
INSERT INTO ThuMuc (TaiKhoan,TenThuMuc)
VALUES 
    ('admin','Gia đình');
INSERT INTO ThuMuc (TaiKhoan,TenThuMuc)
VALUES 
    ('admin','Bạn Bè');
INSERT INTO NhatKy (MaThuMuc,TenNhatKy,NgayTao,NgayChinhSuaCuoiCung,NoiDung)
VALUES 
    (2,'Nhật Ký - Triệu Hoàng (#ACENS cover) | MV Lyrics HD','15/05/2021 13:28PM','15/05/2021 13:28PM','Về nhà làm gì khi đau thương giày xéo cõi lòng
Đã cất bước ra đi, em chớ có hoài nghi
Cho dẫu ước muốn không thành
Em tiếc thương chi cho buồn hơn
Hãy cố quên hết đi nào.
Về nhà làm gì khi mây đen vùi lấp lối mòn
Tìm chốn yên vui đi, nơi đâu cũng có anh kề bên
Nếu nhớ ân ái hôm nào, em cứ khóc cho vơi niềm đau
Sẽ thấy trong tim nhẹ vơi bao nỗi sầu.
Nhắn với mây trời, mây có bay về
Hãy mang theo từng cánh thư xanh đã trao nhau thuở nào
Anh vẫn trông ngóng em hoài
Anh vẫn chờ mong em mãi
Cho dẫu xa cách muôn trùng.
Welcome to Yeucahat.com
Nhắn với mây trời, mây có bay về
Hãy mang theo từng giấc mơ hoa mà ta dấu trong tim
Nhớ nhung biết bao con đường
Đã in bước chân người xưa
Ước mơ dắt ta về bên nhau hoài.');
INSERT INTO NhatKy (MaThuMuc,TenNhatKy,NgayTao,NgayChinhSuaCuoiCung,NoiDung)
VALUES 
    (2,'HÁT VỚI DÒNG SÔNG - MỸ TÂM (LÀN SÓNG XANH 2002 )','13/05/2021 14:28PM','12/05/2021 14:28PM','Mỗi khi chiều về em ngồi hát bên dòng sông
Dòng sđasaỗi đau trong từng đêm vắng
Tình yêu đến em không mong đợi gì
Tình yêu đi em không hề hối tiếc
Ngày xa xưa em hát với dòng sông
Và giờ đây em hát giữa dòng đời
Dù dòng đời... không êm ái như dòng sông...');
SELECT count(*) AS DemTaiKhoan FROM NguoiDung WHERE TaiKhoan = 'admin' AND MatKhau = 'admin' 