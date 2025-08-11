package com.example.servingwebcontent;

import com.example.servingwebcontent.model.*;
import java.time.LocalDate;
import java.util.UUID;

public class HotelSmokeTest {
    public static void main(String[] args) {
        // 1) Thêm phòng
        PhongKhachSan p = new PhongKhachSan(
                "P101", RoomType.DOI, 800000, RoomStatus.TRONG, 2, "TV,Wifi,MiniBar"
        );
        System.out.println("Insert phong: " + PhongKhachSan.insert(p));

        // 2) Thêm khách
        KhachHang kh = new KhachHang(
                "012345678901", "Nguyen Van A", "Nam",
                LocalDate.of(1995,1,15), "0901234567",
                "a@example.com", "123 ABC, HN", "Viet Nam"
        );
        System.out.println("Insert khach: " + KhachHang.insert(kh));

        // 3) Tạo đặt phòng (2 đêm)
        String maDP = "DP-" + UUID.randomUUID().toString().substring(0,8).toUpperCase();
        DatPhong dp = new DatPhong(
                maDP, kh.getDinhDanh(), p.getMaPhong(),
                LocalDate.now().plusDays(1), LocalDate.now().plusDays(3),
                2, BookingStatus.DA_DAT
        );
        System.out.println("Insert dat_phong: " + DatPhong.insert(dp));

        // 4) Thêm dịch vụ & gán cho booking
        DichVu dv = new DichVu("LAUNDRY", "Giặt ủi", 50000, ServiceType.THEO_LAN);
        DichVu.insert(dv);
        ChiTietDichVu.insert(new ChiTietDichVu(maDP, "LAUNDRY", 3, 3 * 50000));

        // 5) Tạo hóa đơn (VAT = 10%, giảm giá 0, thanh toán tiền mặt)
        String maHD = "HD-" + UUID.randomUUID().toString().substring(0,8).toUpperCase();
        boolean okHD = HoaDon.taoVaLuuHoaDonTuBooking(maHD, maDP, 0, PaymentMethod.TIEN_MAT);
        System.out.println("Tao + luu hoa_don: " + okHD);

        // 6) Check-in rồi check-out (giả lập)
        System.out.println("Check-in: " + DatPhong.checkIn(maDP));
        System.out.println("Check-out: " + DatPhong.checkOut(maDP));

        // 7) Xem hóa đơn vừa tạo
        HoaDon hd = HoaDon.findById(maHD);
        System.out.println("HoaDon: " + hd);
    }
}
