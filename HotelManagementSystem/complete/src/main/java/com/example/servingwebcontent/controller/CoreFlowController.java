package com.example.servingwebcontent.controller;

import com.example.servingwebcontent.core.HotelCoreService;
import com.example.servingwebcontent.model.PaymentMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/core")
public class CoreFlowController {

    public record Req(
            String dinhDanhKhach,
            String maPhong,
            String ngayNhan,  // yyyy-MM-dd
            String ngayTra,   // yyyy-MM-dd
            Integer soKhach,
            Map<String,Integer> dichVu, // {"SPA":1,"LAUNDRY":2}
            Double giamGia,
            String phuongThuc // TIEN_MAT | CHUYEN_KHOAN | THE | E_WALLET
    ) {}

    @PostMapping("/dat-phong-va-thanh-toan")
    public ResponseEntity<?> run(@RequestBody Req r) {
        var kq = HotelCoreService.datPhongVaThanhToan(
                r.dinhDanhKhach,
                r.maPhong,
                LocalDate.parse(r.ngayNhan),
                LocalDate.parse(r.ngayTra),
                r.soKhach == null ? 1 : r.soKhach,
                r.dichVu,
                r.giamGia == null ? 0.0 : r.giamGia,
                r.phuongThuc == null ? PaymentMethod.TIEN_MAT : PaymentMethod.valueOf(r.phuongThuc)
        );
        return ResponseEntity.ok(kq);
    }
}
