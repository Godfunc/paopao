package com.godfunc.paopao.controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.godfunc.paopao.param.GroupCreateParam;
import com.godfunc.paopao.service.IUserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author Godfunc
 * @since 2020-02-07
 */
@Controller
@RequestMapping("user")
public class UserController {

    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping("login")
    public String login(HttpSession session, Model model) {
        model.addAttribute("qrCode", userService.getLoginQrCode(session.getId()));
        model.addAttribute("id", session.getId());
        return "login";
    }

    @GetMapping("console")
    public String console(@CookieValue(required = false) String token, Model model) {
        return userService.console(token, model);
    }


    @PostMapping("create")
    @ResponseBody
    public R create(@CookieValue(required = false) String token, GroupCreateParam param) {
        return userService.createGroup(token, param);

    }

    @GetMapping("groupQrCode/{groupUid}")
    @ResponseBody
    public R getGroupQrCode(@CookieValue(required = false) String token, @PathVariable String groupUid) {
        return userService.getGroupQrCode(token, groupUid);
    }
    @PostMapping("deleteGroup/{groupUid}")
    @ResponseBody
    public R deleteGroup(@CookieValue(required = false) String token, @PathVariable String groupUid) {
        return userService.deleteGroup(token, groupUid);
    }
    @PostMapping("groupNumberDelete/{groupId}")
    @ResponseBody
    public R groupNumberDelete(@CookieValue(required = false) String token, @PathVariable String groupId) {
        return userService.groupNumberDelete(token, groupId);
    }

}
