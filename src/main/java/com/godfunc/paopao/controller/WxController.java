package com.godfunc.paopao.controller;

import com.godfunc.paopao.constant.CommonConstant;
import com.godfunc.paopao.service.IWxService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Godfunc
 * @date 2020/2/7 18:20
 */
@Controller
public class WxController {

    private final IWxService wxService;

    public WxController(IWxService wxService) {
        this.wxService = wxService;
    }

    @RequestMapping("auth")
    public String auth(@RequestParam(required = false) String code, @RequestParam(required = false) String state, Model model) {
        if (wxService.auth(code, state)) {
            model.addAttribute("status", CommonConstant.STATUS_SUCCESS);
        } else {
            model.addAttribute("status", CommonConstant.STATUS_FAIL);
        }
        return "auth";
    }

    @RequestMapping("bind")
    public String bind(@RequestParam(required = false) String code, @RequestParam(required = false) String state, Model model) {
        wxService.bindGroup(code, state, model);
        return "bind";
    }
}
