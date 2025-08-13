package com.example.servingwebcontent.controller;

import com.example.servingwebcontent.core.HotelCoreService;
import com.example.servingwebcontent.core.HotelCoreService.KetQuaDatPhong;
import com.example.servingwebcontent.model.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/core-flow")
public class CoreFlowController {

    private final HotelCoreService hotelCoreService;

    public CoreFlowController(HotelCoreService hotelCoreService) {
        this.hotelCoreService = hotelCoreService;
    }

    // ===================== 1) Đặt phòng + (DV) + Xuất hoá đơn =====================
    public record FlowRequest(
            String dinhDanhKhach,
            String maPhong,
            String ngayNhan,            // yyyy-MM-dd
            String ngayTra,             // yyyy-MM-dd
            Integer soKhach,
            Map<String,Integer> dichVuSoLuong, // { "DV_...": 2, ... }
            Double giamGia,
            String phuongThuc           // TIEN_MAT | THE | CHUYEN_KHOAN | VI_DIEN_TU
    ) {}

    @PostMapping("/dat-phong-thanh-toan")
    public ResponseEntity<?> datPhongThanhToan(@RequestBody FlowRequest r) {
        if (r == null || isBlank(r.dinhDanhKhach()) || isBlank(r.maPhong()) ||
                isBlank(r.ngayNhan()) || isBlank(r.ngayTra())) {
            return ResponseEntity.badRequest().body("Thiếu trường bắt buộc");
        }

        final LocalDate nhan, tra;
        try {
            nhan = LocalDate.parse(r.ngayNhan().trim());
            tra  = LocalDate.parse(r.ngayTra().trim());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Định dạng ngày phải là yyyy-MM-dd");
        }

        int soKhach = (r.soKhach() == null || r.soKhach() <= 0) ? 1 : r.soKhach();

        PaymentMethod method = null; // để service tự default TIEN_MAT nếu null
        if (!isBlank(r.phuongThuc())) {
            try { method = PaymentMethod.valueOf(r.phuongThuc().trim().toUpperCase()); }
            catch (Exception e) { return ResponseEntity.badRequest().body("phuongThuc không hợp lệ"); }
        }

        try {
            KetQuaDatPhong kq = hotelCoreService.datPhongVaThanhToan(
                    r.dinhDanhKhach().trim(),
                    r.maPhong().trim(),
                    nhan,
                    tra,
                    soKhach,
                    r.dichVuSoLuong(),                         // có thể null/empty
                    r.giamGia() == null ? 0.0 : r.giamGia(),
                    method
            );
            return ResponseEntity.ok(kq);

        } catch (IllegalArgumentException iae) {
            return ResponseEntity.badRequest().body(iae.getMessage());
        } catch (IllegalStateException ise) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ise.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Lỗi không xác định: " + ex.getMessage());
        }
    }

    // ===================== 2) Thêm phòng (qua HotelCoreService) =====================
    public record NewRoomRequest(
            String maPhong,
            String loaiPhong,          // DON | DOI | SUITE | FAMILY | DELUXE
            Double giaMoiDem,
            String tinhTrang,          // TRONG | DA_DAT | DANG_O | BAO_TRI
            Integer soNguoiToiDa,
            String tienNghiKemTheo
    ) {}

    @PostMapping("/them-phong")
    public ResponseEntity<?> themPhong(@RequestBody NewRoomRequest r) {
        if (r == null || isBlank(r.maPhong()) || isBlank(r.loaiPhong()) || r.soNguoiToiDa() == null) {
            return ResponseEntity.badRequest().body("Thiếu maPhong/loaiPhong/soNguoiToiDa");
        }
        final RoomType loai;
        final RoomStatus status;
        try { loai = RoomType.valueOf(r.loaiPhong().trim().toUpperCase()); }
        catch (Exception e) { return ResponseEntity.badRequest().body("loaiPhong không hợp lệ"); }
        try {
            status = isBlank(r.tinhTrang()) ? RoomStatus.TRONG
                    : RoomStatus.valueOf(r.tinhTrang().trim().toUpperCase());
        } catch (Exception e) { return ResponseEntity.badRequest().body("tinhTrang không hợp lệ"); }

        try {
            PhongKhachSan saved = hotelCoreService.themPhong(
                    r.maPhong().trim(),
                    loai,
                    r.giaMoiDem() == null ? 0.0 : r.giaMoiDem(),
                    status,
                    r.soNguoiToiDa(),
                    r.tienNghiKemTheo()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (IllegalArgumentException iae) {
            return ResponseEntity.badRequest().body(iae.getMessage());
        } catch (IllegalStateException ise) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ise.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Lỗi không xác định: " + ex.getMessage());
        }
    }

    // ===================== 3) Thêm dịch vụ (qua HotelCoreService) =====================
    public record NewServiceRequest(
            String maDichVu,
            String tenDichVu,
            Double gia,
            String loai              // THEO_LAN | THEO_GIO
    ) {}

    @PostMapping("/them-dich-vu")
    public ResponseEntity<?> themDichVu(@RequestBody NewServiceRequest r) {
        if (r == null || isBlank(r.maDichVu()) || isBlank(r.tenDichVu())) {
            return ResponseEntity.badRequest().body("Thiếu maDichVu/tenDichVu");
        }
        final ServiceType type;
        try {
            type = isBlank(r.loai()) ? ServiceType.THEO_LAN
                                     : ServiceType.valueOf(r.loai().trim().toUpperCase());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("loai dịch vụ không hợp lệ");
        }

        try {
            DichVu saved = hotelCoreService.themDichVu(
                    r.maDichVu().trim(),
                    r.tenDichVu().trim(),
                    r.gia() == null ? 0.0 : r.gia(),
                    type
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(saved);
        } catch (IllegalArgumentException iae) {
            return ResponseEntity.badRequest().body(iae.getMessage());
        } catch (IllegalStateException ise) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ise.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Lỗi không xác định: " + ex.getMessage());
        }
    }

    // ===================== 4) Thanh toán & dọn phụ thuộc & xoá khách =====================
    public record PayAndCleanReq(String maHoaDon, Boolean checkoutFirst, Boolean deleteBooking) {}
    public record PayAndCleanRes(String maDatPhong, String dinhDanhKhach,
                                 int soDongDVXoa, boolean daXoaHoaDon, boolean daXoaBooking, boolean daXoaKhach,
                                 String message) {}

    @PostMapping("/pay-and-clean")
    public ResponseEntity<?> payAndClean(@RequestBody PayAndCleanReq req) {
        if (req == null || isBlank(req.maHoaDon())) {
            return ResponseEntity.badRequest().body("Thiếu maHoaDon");
        }

        // 1) Lấy hoá đơn
        HoaDon hd = HoaDon.findById(req.maHoaDon());
        if (hd == null) return ResponseEntity.badRequest().body("Không tìm thấy hoá đơn");

        // 2) Lấy booking & khách
        String maDP = hd.getMaDatPhong();
        DatPhong dp = DatPhong.findById(maDP);
        if (dp == null) return ResponseEntity.badRequest().body("Không tìm thấy booking gắn với hoá đơn");
        String khId = dp.getDinhDanhKhach();

        // 3) (tuỳ chọn) checkout trước
        if (Boolean.TRUE.equals(req.checkoutFirst())) {
            try { DatPhong.checkOut(maDP); } catch (Exception ignored) {}
        }

        // 4) Recalc hoá đơn để đảm bảo tổng mới nhất
        try {
            hd.recalcTotalsFromBooking();
            HoaDon.update(hd);
        } catch (Exception ignore) {}

        // 5) Xoá chi tiết DV của booking
        int deletedCT;
        try { deletedCT = ChiTietDichVu.deleteByBooking(maDP); }
        catch (Exception e) { return ResponseEntity.badRequest().body("Xoá chi tiết DV lỗi: " + e.getMessage()); }

        // 6) Xoá hoá đơn
        boolean delHdOk = HoaDon.deleteById(hd.getMaHoaDon());

        // 7) (tuỳ) Xoá booking
        boolean delDpOk = false; // <-- chỉ true nếu thực sự xoá
        if (Boolean.TRUE.equals(req.deleteBooking())) {
            delDpOk = DatPhong.deleteById(maDP);
            if (!delDpOk) {
                return ResponseEntity.badRequest().body("Xoá đặt phòng lỗi (có thể do ràng buộc khác)");
            }
        }

        // 8) Xoá khách (có thể fail nếu còn booking khác)
        boolean delKhOk = KhachHang.deleteById(khId);

        String msg = "Đã thanh toán (recalc), xoá " + deletedCT + " dòng DV, xoá HĐ"
                + (delDpOk ? ", xoá booking" : "")
                + (delKhOk ? ", xoá khách" : " (xoá khách thất bại hoặc còn dữ liệu liên quan)");

        return ResponseEntity.ok(new PayAndCleanRes(
                maDP, khId, deletedCT, delHdOk, delDpOk, delKhOk, msg
        ));
    }

    // ===================== Helpers =====================
    private static boolean isBlank(String s) { return s == null || s.trim().isEmpty(); }

}
