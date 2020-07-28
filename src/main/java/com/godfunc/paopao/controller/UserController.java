package com.godfunc.paopao.controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.godfunc.paopao.annotation.Login;
import com.godfunc.paopao.annotation.LoginUser;
import com.godfunc.paopao.entity.User;
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

    @Login
    @GetMapping("console")
    public String console(@LoginUser User user, Model model) {
        return userService.console(user, model);
    }


    @Login
    @PostMapping("create")
    @ResponseBody
    public R create(@LoginUser User user, GroupCreateParam param) {
        return userService.createGroup(user, param);

    }

    @Login
    @GetMapping("groupQrCode/{groupUid}")
    @ResponseBody
    public R getGroupQrCode(@LoginUser User user, @PathVariable String groupUid) {
        return userService.getGroupQrCode(user, groupUid);
    }

    @Login
    @PostMapping("deleteGroup/{groupUid}")
    @ResponseBody
    public R deleteGroup(@LoginUser User user, @PathVariable String groupUid) {
        return userService.deleteGroup(user, groupUid);
    }

    @Login
    @PostMapping("groupNumberDelete/{groupId}")
    @ResponseBody
    public R groupNumberDelete(@LoginUser User user, @PathVariable String groupId) {
        return userService.groupNumberDelete(user, groupId);
    }

    @Login
    @PostMapping("leaveGroup/{groupUid}")
    @ResponseBody
    public R leaveGroup(@LoginUser User user, @PathVariable String groupUid) {
        return userService.leaveGroup(user, groupUid);
    }

}
