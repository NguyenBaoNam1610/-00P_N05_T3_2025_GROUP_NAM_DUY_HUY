package com.example.servingwebcontent.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CoreFlowUIController {

    @GetMapping("/ui/core-flow")
    public String coreFlow() {
        // trả về templates/coreflow.html
        return "coreflow";
    }
}
