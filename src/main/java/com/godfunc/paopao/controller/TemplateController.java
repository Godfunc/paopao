package com.godfunc.paopao.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Godfunc
 * @date 2020/2/19 19:39
 */

@Controller
public class TemplateController {

    @Value("${mpQrCode}")
    private String mpQrCode;
    @Value("${host}")
    private String host;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("mp", mpQrCode);
        return "home";
    }

    @GetMapping("/message")
    public String message(Model model) {
        model.addAttribute("host", host);
        return "message";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }
}
