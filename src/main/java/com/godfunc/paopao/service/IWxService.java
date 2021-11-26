package com.godfunc.paopao.service;

import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;
import me.chanjar.weixin.common.bean.oauth2.WxOAuth2AccessToken;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.ui.Model;

/**
 * @author Godfunc
 * @date 2020/2/7 17:08
 */

public interface IWxService {

    WxOAuth2AccessToken getAccessToken(String code);

    WxOAuth2UserInfo getUserInfo(WxOAuth2AccessToken accessToken);

    boolean auth(String code, String state);

    boolean sendKfMsg(String openId, String msg, String link) throws WxErrorException;

    String sendTempMsg(String openid, String msg, String link) throws WxErrorException;

    void bindGroup(String code, String state, Model model);
}
