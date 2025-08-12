package com.example.servingwebcontent;

import com.example.servingwebcontent.config.GlobalExceptionHandler;
import com.example.servingwebcontent.core.HotelCoreService;
import com.example.servingwebcontent.core.HotelCoreService.KetQuaDatPhong;
import com.example.servingwebcontent.controller.CoreFlowController;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CoreFlowController.class)
@Import(GlobalExceptionHandler.class)
class CoreFlowControllerTest {

    @Autowired MockMvc mvc;

    @Test
    void datPhongVaThanhToan_ok_200() throws Exception {
        KetQuaDatPhong fake = new KetQuaDatPhong(
                "DP-TEST", "HD-TEST", 1_000_000, 200_000, 120_000, 50_000, 1_270_000
        );

        try (MockedStatic<HotelCoreService> m = Mockito.mockStatic(HotelCoreService.class)) {
            m.when(() -> HotelCoreService.datPhongVaThanhToan(
                    anyString(), anyString(), any(), any(), anyInt(), anyMap(), anyDouble(), any()))
             .thenReturn(fake);

            String body = """
                {
                  "dinhDanhKhach":"012345678901",
                  "maPhong":"P101",
                  "ngayNhan":"2025-08-20",
                  "ngayTra":"2025-08-22",
                  "soKhach":2,
                  "dichVu":{"SPA":1},
                  "giamGia":50000,
                  "phuongThuc":"TIEN_MAT"
                }
                """;

            mvc.perform(post("/api/core/dat-phong-va-thanh-toan")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(body))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.maDatPhong").value("DP-TEST"))
               .andExpect(jsonPath("$.maHoaDon").value("HD-TEST"))
               .andExpect(jsonPath("$.tongThanhToan").value(1_270_000.0));
        }
    }
}
