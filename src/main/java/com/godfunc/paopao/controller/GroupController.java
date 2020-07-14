package com.godfunc.paopao.controller;

import com.baomidou.mybatisplus.extension.api.R;
import com.godfunc.paopao.entity.User;
import com.godfunc.paopao.service.IGroupService;
import com.godfunc.paopao.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Godfunc
 * @date 2020/2/10 13:27
 */
@Controller
@RequestMapping("group")
public class GroupController {

    private final IGroupService groupService;
    private final IUserService userService;

    public GroupController(IGroupService groupService, IUserService userService) {
        this.groupService = groupService;
        this.userService = userService;
    }
}
