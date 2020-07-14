package com.godfunc.paopao.service.impl;

import com.godfunc.paopao.constant.CommonConstant;
import com.godfunc.paopao.entity.Group;
import com.godfunc.paopao.entity.User;
import com.godfunc.paopao.service.IGroupService;
import com.godfunc.paopao.service.IUserService;
import com.godfunc.paopao.service.IWxService;
import com.godfunc.paopao.util.CacheUtils;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.kefu.WxMpKefuMessage;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.LocalDateTime;
import java.util.Arrays;

/**
 * @author Godfunc
 * @date 2020/2/7 17:08
 */
@Service
@Slf4j
public class WxServiceImpl implements IWxService {

    @Value("${md5Key}")
    private String md5Key;

    @Autowired
    private WxMpService wxMpService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IGroupService groupService;


    @Override
    public WxMpOAuth2AccessToken getAccessToken(String code) {
        try {
            WxMpOAuth2AccessToken accessToken = wxMpService.oauth2getAccessToken(code);
            return accessToken;
        } catch (WxErrorException e) {
            log.error("获取accessToken异常", e);
        }
        return null;
    }

    @Override
    public WxMpUser getUserInfo(WxMpOAuth2AccessToken accessToken) {
        try {
            WxMpUser userInfo = wxMpService.oauth2getUserInfo(accessToken, "zh_CN");
            return userInfo;
        } catch (WxErrorException e) {
            log.error("获取userInfo异常", e);
        }
        return null;
    }

    @Override
    public boolean auth(String code, String state) {
        if (StringUtils.isBlank(code)) {
            return false;
        }
        WxMpOAuth2AccessToken accessToken = getAccessToken(code);
        if (accessToken == null || StringUtils.isBlank(accessToken.getAccessToken())) {
            return false;
        }
        WxMpUser userInfo = getUserInfo(accessToken);

        if (userInfo == null) {
            return false;
        }
        User user = userService.getByOpenId(accessToken.getOpenId());
        if (user == null) {
            user = new User();
            user.setNikeName(userInfo.getNickname())
                    .setOpenid(accessToken.getOpenId())
                    .setAlias(user.generateAlias(md5Key))
                    .setToken(user.generateToken(md5Key))
                    .setRegistrationTime(LocalDateTime.now())
                    .setLastLoginTime(LocalDateTime.now());
            userService.save(user);
        } else {
            if (userInfo.getNickname().equals(user.getNikeName())) {
                user.setNikeName(userInfo.getNickname()).setLastLoginTime(LocalDateTime.now());
                userService.updateById(user);
            }
        }
        CacheUtils.getInstance().putCache(state, user.getToken());
        return true;
    }

    @Override
    public boolean sendKfMsg(String openId, String msg, String link) throws WxErrorException {
        WxMpKefuMessage wxMpKefuMessage = new WxMpKefuMessage();
        wxMpKefuMessage.setToUser(openId);
        wxMpKefuMessage.setMsgType(WxConsts.MassMsgType.TEXT);
        wxMpKefuMessage.setContent(link);
        wxMpKefuMessage.setContent(link == null ? msg : msg + "\n" + link);
        return wxMpService.getKefuService().sendKefuMessage(wxMpKefuMessage);
    }

    @Override
    public String sendTempMsg(String openid, String msg, String link) throws WxErrorException {
        WxMpTemplateMessage wxMpTemplateMessage = WxMpTemplateMessage.builder()
                .toUser(openid)
                .templateId("3WdYYS4jpz3nPWWVRehUK0oLBo7WN4A_6L56FlDVIUM")
                .url(link)
                .data(Arrays.asList(new WxMpTemplateData("msg", msg)))
                .build();
        return wxMpService.getTemplateMsgService().sendTemplateMsg(wxMpTemplateMessage);
    }

    @Override
    public void bindGroup(String code, String state, Model model) {
        WxMpOAuth2AccessToken accessToken = getAccessToken(code);
        if (accessToken == null) {
            model.addAttribute("status", CommonConstant.STATUS_FAIL);
            model.addAttribute("msg", "绑定失败");
        } else {
            User user = userService.getByOpenId(accessToken.getOpenId());
            if (user == null) {
                WxMpUser userInfo = getUserInfo(accessToken);
                user = new User();
                user.setNikeName(userInfo.getNickname())
                        .setOpenid(userInfo.getOpenId())
                        .setAlias(user.generateAlias(md5Key))
                        .setToken(user.generateToken(md5Key))
                        .setLastLoginTime(LocalDateTime.now())
                        .setRegistrationTime(LocalDateTime.now());
                userService.save(user);
            } else {
                Group group = groupService.getCreatorByGroupUid(state);
                if (group == null) {
                    model.addAttribute("status", CommonConstant.STATUS_FAIL);
                    model.addAttribute("msg", "组不存在或已被删除");
                } else {
                    if (groupService.checkNumber(user.getId(), group.getGroupUid())) {
                        model.addAttribute("status", CommonConstant.STATUS_SUCCESS);
                        model.addAttribute("msg", "您已是【" + group.getName() + "】中的成员了");
                    } else {
                        groupService.createNumber(group.getName(), group.getGroupUid(), user.getId(), user.getNikeName(), user.getOpenid());
                        model.addAttribute("status", CommonConstant.STATUS_SUCCESS);
                        model.addAttribute("msg", "您已进入到【" + group.getName() + "】组中");
                    }
                }
            }
        }
    }

}
