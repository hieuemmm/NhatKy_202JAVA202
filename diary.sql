-- phpMyAdmin SQL Dump
-- version 5.1.0
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th6 02, 2021 lúc 07:27 PM
-- Phiên bản máy phục vụ: 10.4.19-MariaDB
-- Phiên bản PHP: 8.0.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `diary`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `nguoidung`
--

CREATE TABLE `nguoidung` (
  `TaiKhoan` varchar(50) COLLATE utf8mb4_unicode_ci NOT NULL,
  `MatKhau` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `nguoidung`
--

INSERT INTO `nguoidung` (`TaiKhoan`, `MatKhau`) VALUES
('admin', 'admin'),
('hieudiary', '1');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `nhatky`
--

CREATE TABLE `nhatky` (
  `MaNhatKy` int(11) NOT NULL,
  `MaThuMuc` int(11) DEFAULT NULL,
  `TenNhatKy` varchar(200) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `NgayTao` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `NgayChinhSuaCuoiCung` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `NoiDung` longtext COLLATE utf8mb4_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `nhatky`
--

INSERT INTO `nhatky` (`MaNhatKy`, `MaThuMuc`, `TenNhatKy`, `NgayTao`, `NgayChinhSuaCuoiCung`, `NoiDung`) VALUES
(27, 8, 'Phiến Lá Tĩnh Lặng', '2021-05-31 Lúc 15:07', '2021-05-31 Lúc 15:17', 'Trời xanh áng mây trôi tựa lúc đầu\nThời gian giống như biển dài vô tận\nNgày mà dẫu bao lâu thì ta chẳng thể quên được\nNgày mà nắng đưa Em gặp Anh\n\nTừ giây phút ấy, em tự ước nguyện…\nDùng trăng với sao, viết thành câu chuyện…\nỞ nơi lá rơi con đường hoa, Anh xuất hiện…\nLà khi em biết mình nên bên nhau…\n\nDẫu biết sẽ đến lúc này\nNgày ánh nắng xuân qua đi\nNgày con tim phụ lý trí\nƯớt đôi mi mình biệt ly\nLòng thương anh bị trời mây kia cùng thổi đi xa.\nRồi đến lúc người chẳng bên ta\nMang theo bài thơ câu hát thiết tha…\n\nDẫu phiến lá tĩnh dưới đường, còn cánh én bay trên trời…\nTựa như đến từ hai thế giới, nói lời từ biệt chẳng buông lơi…\nPhải chăng khi mình càng yêu nhau thì càng chia xa\nCàng dễ đến lại càng mau đi\nQuay lưng mà chẳng kịp tiếc nuối chi.\n\nTừ giây phút ấy, em tự ước nguyện…\nDùng trăng với sao, viết thành câu chuyện…\nỞ nơi lá rơi con đường hoa, Anh xuất hiện…\nLà khi em biết mình nên bên nhau…\n\nDẫu biết sẽ đến lúc này\nNgày ánh nắng xuân qua đi\nNgày con tim phụ lý trí\nƯớt đôi mi mình biệt ly\nLòng thương anh bị trời mây kia cùng thổi đi xa.\nRồi đến lúc người chẳng bên ta\nMang theo bài thơ câu hát thiết tha…\nDẫu phiến lá tĩnh dưới đường, còn cánh én bay trên trời…\nTựa như đến từ hai thế giới, nói lời từ biệt chẳng buông lơi…\nPhải chăng khi mình càng yêu nhau thì càng chia xa\nCàng dễ đến lại càng mau đi\nQuay lưng mà chẳng kịp tiếc nuối chi.\n\nDẫu biết sẽ đến lúc này\nNgày ánh nắng xuân qua đi\nNgày con tim phụ lý trí\nƯớt đôi mi mình biệt ly\nLòng thương anh bị trời mây kia cùng thổi đi xa.\nRồi đến lúc người chẳng bên ta\nMang theo bài thơ câu hát thiết tha…\nDẫu phiến lá tĩnh dưới đường, còn cánh én bay trên trời…\nTựa như đến từ hai thế giới, nói lời từ biệt chẳng buông lơi…\nPhải chăng khi mình càng yêu nhau thì càng chia xa\nCàng dễ đến lại càng mau đi\nQuay lưng mà chẳng kịp tiếc nuối chi.'),
(28, 8, 'Chuyện Anh Sinh Viên', '2021-05-31 Lúc 15:09', '2021-05-31 Lúc 15:18', 'Thu đi qua thật nhanh gió Đông bất chợt ùa về, đôi tay thêm lạnh giá vì anh vẫn còn cô đơn\nAnh cao to đẹp trai và anh không chơi bời đua đòi, nhưng anh là sinh viên trường giao thông nên anh vẫn chưa có người yêu\nNụ cười thật rộn ràng tràn niềm vui bước xuống phố, chẳng ngại ngần điều gì gạt sầu lo đi anh hát\nKhông có gấu anh đỡ tốn tiền, ăn cơm cá thay cháo ăn liền, mỗi sáng đi học đều rồi chiều về anh chơi game\nMai đây anh xây lên bao con đường\nMang yêu thương trong anh đến những cây cầu\nTừ đồng quê lúa vàng về phồn hoa phố thị nhộn nhịp tàu xe băng qua là niềm vui sinh viên giao thông\nCùng hát lên, cho tan đi lo âu\nNgày mới sang, cho đam mê bay cao\nVà hát lên chuyện anh sinh viên giao thông, nụ cười luôn luôn trên môi, là la lá la la lá là…….\nHòa tiếng ca, ước mơ thêm bay xa\nCùng sánh vai xây non sông quê ta\nCùng dựng xây lên đất Việt, giàu đẹp hơn không đói nghèo, nụ cười em thơ xinh tươi, rạng ngời bao nơi công trình lớn lao….\nLizay:\nNgày qua ngày anh vẫn cứ thế\nVẫn 1 mình thôi mà mấy chế\nChẳng biết phải làm sao cả\nBao lâu vẫn cơ đơn à\nSáng đánh răng rửa mặt rồi lại tới trường ngày nào cũng đều\nMãi chẳng có người yêu, mấy bà xóm trọ cứ suốt ngày trêu\n“Thằng này thì cũng vui tính\nNhìn thì cũng đẹp trai đó\nSao tới giờ chưa có ai…ai…aiii\nHay là mày hai phai à….\nÔi, ôi thôi mà\nNói vậy chết con nhà người ta\nChẳng qua là anh không thích thôi\nVì còn bao điều nghĩ ngợi\nTiền phòng tháng này còn chưa có\nVới tiền ăn bố mẹ cũng chưa cho\nLấy đâu ra mà còn nghĩ tới gấu\nNgười yêu anh là bản vẽ đường với cầu\nVì thế anh cứ alone cho khỏi phải đau đầu\nLil Wind:\nMẹ nói đúng mẹ ơi đỗ đại học là chơi mà trượt là học\nNhưng mà fb của con thực sự cũng có gì đâu mà đọc\nCũng chỉ tối anh em trà đá\nCứ thấy bóng đến chân là phá\nTối về ăn cơm đạm bạc\nĐeo luôn trợ thính cho tai chạm nhạc\nVà trong túi quần đằng sau bên phải còn vài xấp tiền lẻ mà con lười tiêu\nKhông cần phải khoe mình nghèo, họ vẫn biết, vì không có người yêu\nMỗi sáng đánh răng rửa mặt đến trường thật sớm để nghe thuyết giáo\nVà rồi đến tối ăn vội ăn vàng vào học tín chỉ để sau viết báo\nNhưng con vẫn vui, vì con luôn tin, sau hôm nay là một ngày mới\nCon thích như thế, vì con luôn tin, sau hôm nay là tiền đầy túi\nVà cũng một phần, là muốn thấy mẹ cười\nCùng với bố nữa, là con thấy nhẹ người\nNhững lo âu khác là căn bệnh kinh niên\nNhưng cứ tươi đã vì con là sinh viên'),
(29, 8, 'Mẩy Thật Mẩy', '2021-05-31 Lúc 15:11', '2021-05-31 Lúc 15:18', 'Yeah I love you too\nBem nhạc house nhưng mình hit the woah\nLuôn là đỉnh của chóp ngoài em ra không có ai là number 2\nEm ghét mùa đông vì em nóng bỏng và luôn hở hở hang hang\nEm thích đàn ông phải luôn nhắm thẳng vào chỗ nở nở nang nàng\nEm lồng lộn với anh là đúng gu\nThương em vất vả nâng cấp rồi trùng tu\nThương em nên chảy dãi mỗi khi em lột đồ\nMột người ngon đét là hai người cùng vui\nThế nên việc của em là cứ đẹp\nThẻ của anh đấy em cứ quẹt\nYêu thương anh thì em hãy cứ đẹp\nTừ đầu đến chân là phải luôn căng đét\nEm thích thì cứ sửa sang miễn là đẹp thật đẹp\nĐẹp thật đẹp, phải đẹp thật đẹp\nEm cứ thoải mái tân trang nhìn phải chẹp chẹp chẹp\nChẹp chẹp chẹp, chúng nó phải chẹp chẹp chẹp\nAnh thích ngắm những bộ loa ú nẩy thật nẩy\nNẩy thật nẩy trông kìa nẩy thật nẩy\nAnh thích ngắm những bàn tọa ú mẩy thật mẩy\nMẩy thật mẩy, nhìn phải mẩy thật mẩy\nAnh thích ngắm những bộ loa ú nẩy thật nẩy\nImma lucky man\nEm ngon ngon sao phải tiếc lời khen\nEm thon thon ôm vừa khít bộ ren\nHai tay anh mong sao được chạm vào tâm hồn em\nChỗ nào còn thiếu thì lại thêm vào, đoạn nào còn thiếu thì lại rút gọn\nFiller Tắm trắng thật là thơm nào vì đằng nào anh chả húp trọn\nMấy thằng hay phàn nàn bạn gái làm đẹp à, quá dở!\nAnh thì khác vì anh là Fan của những đường cong ná thở\nThế nên việc của em là cứ đẹp\nThẻ của anh đấy em cứ quẹt\nYêu thương anh thì em hãy cứ đẹp\nTừ đầu đến chân là phải luôn căng đét\nEm thích thì cứ sửa sang miễn là đẹp thật đẹp\nĐẹp thật đẹp, phải đẹp thật đẹp\nEm cứ thoải mái tân trang nhìn phải chẹp chẹp chẹp\nChẹp chẹp chẹp, chúng nó phải chẹp chẹp chẹp\nAnh thích ngắm những bộ loa ú nẩy thật nẩy\nNẩy thật nẩy trông kìa nẩy thật nẩy\nAnh thích ngắm những bàn tọa ú mẩy thật mẩy\nMẩy thật mẩy, nhìn phải mẩy thật mẩy\nEm iu bigdaddy\nThích Ăn món gì đây?'),
(30, 1, 'Còn Đây', '2021-05-31 Lúc 15:13', '2021-06-01 Lúc 06:56', 'Những cái nhìn ngây ngô từ những ngày đầu đời\nNay đã nhìn thấy rõ tận chân trời xa xôi\nNhìn được ra ý nghĩa của từng ánh mắt giọng nói\nTừng hành trình mới, len lỏi cảm hứng trong tôi\nRong chơi như dòng nước bôn ba qua trăm thước\nKhông ngại để cho thân tôi xây xước, luôn nhìn về phía trướcfa\nĐể sẽ đến ngày, ký ức này được sớm lấp đầy\nBởi ngàn áng chuyện hay, những bài học mà tạo hoá là thầy\n\nRong chơi như dòng nước trong vươn qua trăm thước\nKhông ngại để cho thân ta thêm xước\nDang đôi vai ngấm thêm (hơi) sương\nCàng mưa nắng, càng giữ khí chất trăm năm\n\nXin được theo xuống mưa ngàn\nXin được như thác trắng ngân vang\nĐiều gì quý giá hơn cả bạc vàng\nĐiều gì sống mãi theo thời gian\nNhư dòng nước khoáng chất vô vàn\nNhư người mang khí chất hiên ngang\nĐiều quý giá nhất trong muôn vàn\nLà nơi bão giông không thể lay khí chất ta còn đây\n\nBao hiểm trở gian nan dễ làm nhụt chí\nKhiến cho những cái chất sớm bị mất đi\nKhiến cho chữ Duy Nhất trở thành Bất Kì\nĐã bao nhiêu va vấp ta phải cất kĩ\nDù khi vui hay khi buồn, trắng đen thật giả\nDù nốt thăng hay nốt trầm vẫn sẽ ngân nga\nCuộc đời là bản hòa tấu của những thanh âm lạ\nVà ta sẽ hát lên những giai điệu của riêng ta\n\nXin được theo xuống mưa ngàn\nXin được như thác trắng hiên ngang\nNgày ta quý giá hơn cả bạc vàng\nNgày ta sống mãi theo thời gian\nVươn mình cho gió cuốn, mây quàng\nRiêng mình ta thách thức (tiếp bước) gian nan\nĐiều quý giá nhất trong muôn vàn\nLà nơi bão giông không thể lay khí chất ta còn đây\n\nPlease follow the rain down thousands\nXin được như thác trắng hiên ngang\nĐiều gì quý giá hơn cả bạc vàng\nĐiều gì sống mãi theo thời gian\nVươn mình cho gió cuốn, mây quàng\nRiêng mình ta thách thức (tiếp bước) gian nan\nĐiều quý giá nhất trong muôn vàn\nLà nơi bão giông không thể lay khí chất ta còn đây');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `thumuc`
--

CREATE TABLE `thumuc` (
  `MaThuMuc` int(11) NOT NULL,
  `TaiKhoan` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `TenThuMuc` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Đang đổ dữ liệu cho bảng `thumuc`
--

INSERT INTO `thumuc` (`MaThuMuc`, `TaiKhoan`, `TenThuMuc`) VALUES
(1, 'admin', 'Gia đình'),
(8, 'admin', 'Bạn bè');

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `nguoidung`
--
ALTER TABLE `nguoidung`
  ADD PRIMARY KEY (`TaiKhoan`);

--
-- Chỉ mục cho bảng `nhatky`
--
ALTER TABLE `nhatky`
  ADD PRIMARY KEY (`MaNhatKy`),
  ADD KEY `MaThuMuc` (`MaThuMuc`);

--
-- Chỉ mục cho bảng `thumuc`
--
ALTER TABLE `thumuc`
  ADD PRIMARY KEY (`MaThuMuc`),
  ADD KEY `TaiKhoan` (`TaiKhoan`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `nhatky`
--
ALTER TABLE `nhatky`
  MODIFY `MaNhatKy` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;

--
-- AUTO_INCREMENT cho bảng `thumuc`
--
ALTER TABLE `thumuc`
  MODIFY `MaThuMuc` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `nhatky`
--
ALTER TABLE `nhatky`
  ADD CONSTRAINT `nhatky_ibfk_1` FOREIGN KEY (`MaThuMuc`) REFERENCES `thumuc` (`MaThuMuc`);

--
-- Các ràng buộc cho bảng `thumuc`
--
ALTER TABLE `thumuc`
  ADD CONSTRAINT `thumuc_ibfk_1` FOREIGN KEY (`TaiKhoan`) REFERENCES `nguoidung` (`TaiKhoan`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
