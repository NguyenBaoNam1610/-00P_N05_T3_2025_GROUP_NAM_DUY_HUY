package com.example.servingwebcontent.core;

import com.example.servingwebcontent.model.*;

import java.time.LocalDate;
import java.util.*;

/**
 * Mục 5 – Phương thức hoạt động CHÍNH của ứng dụng:
 * - Kiểm tra phòng trống
 * - Tạo đặt phòng
 * - (Tuỳ chọn) Thêm dịch vụ
 * - Tính tiền & xuất hoá đơn
 *
 * Ghi chú:
 * - Dùng các CRUD/logic sẵn có trong Model (JDBC).
 * - Hiện chạy tuần tự; nếu muốn "gói" transaction 1 kết nối duy nhất, ta có thể
 * nâng cấp sau.
 */
public class HotelCoreService {

    /** DTO kết quả tổng hợp cho thầy/khách hàng xem */
    public record KetQuaDatPhong(
            String maDatPhong,
            String maHoaDon,
            double tongTienPhong,
            double tongTienDichVu,
            double thue,
            double giamGia,
            double tongThanhToan) {
    }

    /**
     * Orchestrator: ĐẶT PHÒNG + (DV) + XUẤT HOÁ ĐƠN
     *
     * @param dinhDanhKhach CCCD/Passport của khách (đã có trong bảng khach_hang)
     * @param maPhong       mã phòng muốn đặt
     * @param ngayNhan      ngày nhận (yyyy-MM-dd)
     * @param ngayTra       ngày trả (phải sau ngày nhận)
     * @param soKhach       số khách ở
     * @param dichVuSoLuong map {maDichVu -> soLuong}; có thể null/empty
     * @param giamGia       số tiền giảm giá (>= 0)
     * @param phuongThuc    phương thức thanh toán (PaymentMethod)
     * @return thông tin mã đặt phòng + mã hoá đơn + tổng tiền
     */
    public static KetQuaDatPhong datPhongVaThanhToan(
            String dinhDanhKhach,
            String maPhong,
            LocalDate ngayNhan,
            LocalDate ngayTra,
            int soKhach,
            Map<String, Integer> dichVuSoLuong,
            double giamGia,
            PaymentMethod phuongThuc) {
        // === 0) Validate input cơ bản ===
        if (dinhDanhKhach == null || dinhDanhKhach.isBlank())
            throw new IllegalArgumentException("Thiếu định danh khách");
        if (maPhong == null || maPhong.isBlank())
            throw new IllegalArgumentException("Thiếu mã phòng");
        if (ngayNhan == null || ngayTra == null || !ngayTra.isAfter(ngayNhan))
            throw new IllegalArgumentException("ngayTra phải sau ngayNhan");
        if (soKhach <= 0)
            throw new IllegalArgumentException("soKhach phải > 0");
        if (giamGia < 0)
            throw new IllegalArgumentException("giamGia không được âm");

        // === 1) Kiểm tra khách & phòng tồn tại ===
        KhachHang kh = KhachHang.findById(dinhDanhKhach);
        if (kh == null)
            throw new IllegalArgumentException("Khách hàng chưa tồn tại: " + dinhDanhKhach);

        PhongKhachSan p = PhongKhachSan.findById(maPhong);
        if (p == null)
            throw new IllegalArgumentException("Phòng không tồn tại: " + maPhong);
        if (soKhach > p.getSoNguoiToiDa())
            throw new IllegalArgumentException("Vượt quá sức chứa phòng: " + p.getSoNguoiToiDa());

        // === 2) Đảm bảo phòng TRỐNG trong khoảng ngày ===
        // Nếu dự án đã có DatPhong.isPhongTrong(maPhong, ngayNhan, ngayTra) thì dùng;
        // nếu chưa, fallback bằng timPhongTrong
        boolean phongTrong;
        try {
            phongTrong = DatPhong.isPhongTrong(maPhong, ngayNhan, ngayTra);
        } catch (Throwable ignore) {
            // fallback: kiểm tra phòng có nằm trong danh sách phòng trống không
            List<PhongKhachSan> ds = PhongKhachSan.timPhongTrong(ngayNhan, ngayTra, null, null);
            phongTrong = ds.stream().anyMatch(r -> r.getMaPhong().equals(maPhong));
        }
        if (!phongTrong)
            throw new IllegalStateException("Phòng không trống trong khoảng ngày đã chọn");

        // === 3) Tạo đặt phòng ===
        String maDatPhong = "DP-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        DatPhong dp = new DatPhong(
                maDatPhong,
                dinhDanhKhach,
                maPhong,
                ngayNhan,
                ngayTra,
                soKhach,
                BookingStatus.DA_DAT);
        if (!DatPhong.insert(dp)) {
            throw new RuntimeException("Không tạo được đặt phòng");
        }

        // (Tuỳ chọn) cập nhật trạng thái phòng “DA_DAT” cho dễ nhìn ở màn quản lý
        try {
            PhongKhachSan.updateStatus(maPhong, RoomStatus.DA_DAT);
        } catch (Exception ignored) {
        }

        // === 4) Thêm dịch vụ (nếu có) ===
        if (dichVuSoLuong != null && !dichVuSoLuong.isEmpty()) {
            for (var e : dichVuSoLuong.entrySet()) {
                String maDv = e.getKey();
                int soLuong = e.getValue() == null ? 0 : e.getValue();
                if (soLuong <= 0)
                    continue;

                DichVu dv = DichVu.findById(maDv);
                if (dv == null)
                    throw new IllegalArgumentException("Dịch vụ không tồn tại: " + maDv);

                double thanhTien = dv.getGia() * soLuong;
                Long ret = ChiTietDichVu.insert(new ChiTietDichVu(maDatPhong, maDv, soLuong, thanhTien));
                if (ret == null || ret <= 0L) {
                    throw new RuntimeException("Không thêm được dịch vụ: " + maDv);
                }

            }
        }

        // === 5) Xuất hoá đơn từ đặt phòng ===
        String maHoaDon = "HD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        boolean ok = HoaDon.taoVaLuuHoaDonTuBooking(maHoaDon, maDatPhong, giamGia, phuongThuc);
        if (!ok)
            throw new RuntimeException("Không tạo được hoá đơn");

        // === 6) Đọc lại hoá đơn để trả kết quả tổng hợp ===
        HoaDon hd = HoaDon.findById(maHoaDon);
        if (hd == null)
            throw new IllegalStateException("Không đọc được hoá đơn vừa tạo");

        return new KetQuaDatPhong(
                maDatPhong,
                maHoaDon,
                hd.getTongTienPhong(),
                hd.getTongTienDichVu(),
                hd.getThue(),
                hd.getGiamGia(),
                hd.getTongThanhToan());
    }
}
