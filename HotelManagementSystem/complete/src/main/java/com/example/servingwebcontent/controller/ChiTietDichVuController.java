package com.example.servingwebcontent.controller;

import com.example.servingwebcontent.model.ChiTietDichVu;
import com.example.servingwebcontent.model.DatPhong;
import com.example.servingwebcontent.model.DichVu;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/ctdv")
public class ChiTietDichVuController {

    // ===== Lấy tất cả DV của 1 booking =====
    @GetMapping("/booking/{maDP}")
    public ResponseEntity<?> listByBooking(@PathVariable("maDP") String maDP) {
        if (DatPhong.findById(maDP) == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(ChiTietDichVu.findByBooking(maDP));
    }

    // ===== Tổng tiền dịch vụ của 1 booking =====
    @GetMapping("/booking/{maDP}/tong")
    public ResponseEntity<?> tongTien(@PathVariable("maDP") String maDP) {
        if (DatPhong.findById(maDP) == null) return ResponseEntity.notFound().build();
        double sum = ChiTietDichVu.sumThanhTienByBooking(maDP);
        return ResponseEntity.ok(sum);
    }

    // ===== Thêm dịch vụ vào booking (tự tính thanhTien = donGia * soLuong) =====
    @PostMapping("/booking/{maDP}")
    public ResponseEntity<?> addToBooking(@PathVariable("maDP") String maDP, @RequestBody AddRequest req) {
        if (DatPhong.findById(maDP) == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Booking không tồn tại");
        if (req == null || isBlank(req.maDichVu()) || req.soLuong() == null || req.soLuong() <= 0)
            return ResponseEntity.badRequest().body("Thiếu maDichVu hoặc soLuong > 0");

        DichVu dv = DichVu.findById(req.maDichVu());
        if (dv == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Dịch vụ không tồn tại");

        double thanhTien = dv.getGia() * req.soLuong();
        ChiTietDichVu c = new ChiTietDichVu(maDP, req.maDichVu(), req.soLuong(), thanhTien);
        Long id = ChiTietDichVu.insert(c);
        return (id != null) ? ResponseEntity.status(HttpStatus.CREATED).body(c)
                            : ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Không thể thêm dịch vụ");
    }

    // ===== Sửa 1 dòng dịch vụ (có thể đổi dịch vụ / số lượng, tự tính lại thanhTien) =====
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") long id, @RequestBody UpdateRequest req) {
        ChiTietDichVu old = ChiTietDichVu.findById(id);
        if (old == null) return ResponseEntity.notFound().build();

        String maDV = (req.maDichVu() != null && !req.maDichVu().isBlank()) ? req.maDichVu() : old.getMaDichVu();
        Integer soLuong = (req.soLuong() != null && req.soLuong() > 0) ? req.soLuong() : old.getSoLuong();

        DichVu dv = DichVu.findById(maDV);
        if (dv == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Dịch vụ không tồn tại");

        old.setMaDichVu(maDV);
        old.setSoLuong(soLuong);
        old.setThanhTien(dv.getGia() * soLuong);

        boolean ok = ChiTietDichVu.update(old);
        return ok ? ResponseEntity.ok(old) : ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Không thể cập nhật");
    }

    // ===== Xoá 1 dòng dịch vụ =====
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        boolean ok = ChiTietDichVu.deleteById(id);
        return ok ? ResponseEntity.noContent().build() : ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Không thể xoá");
    }

    // ===== Xoá tất cả dịch vụ của 1 booking =====
    @DeleteMapping("/booking/{maDP}")
    public ResponseEntity<?> deleteByBooking(@PathVariable("maDP") String maDP) {
        if (DatPhong.findById(maDP) == null) return ResponseEntity.notFound().build();
        int n = ChiTietDichVu.deleteByBooking(maDP);
        return ResponseEntity.ok("Đã xoá " + n + " dòng dịch vụ");
    }

    // ====== DTO ======
    public record AddRequest(String maDichVu, Integer soLuong) {}
    public record UpdateRequest(String maDichVu, Integer soLuong) {}

    private static boolean isBlank(String s) { return s == null || s.trim().isEmpty(); }
}
