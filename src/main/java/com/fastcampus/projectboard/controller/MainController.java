package com.fastcampus.projectboard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String root() {
        return "forward:/articles"; // `/articles` 경로로 다시 보내는 역할
    }
}
