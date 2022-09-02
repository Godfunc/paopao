package com.godfunc.paopao.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Godfunc
 * @date 2020/2/19 19:39
 */

@Controller
public class TemplateController {

    @Value("${mpQrCode}")
    private String mpQrCode;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("mp", mpQrCode);
        return "home";
    }

    @GetMapping("/message")
    public String message(HttpServletRequest request, Model model) {
        model.addAttribute("host", ServletUriComponentsBuilder.fromRequestUri(request).replacePath(null).build().toString());
        return "message";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }
}
