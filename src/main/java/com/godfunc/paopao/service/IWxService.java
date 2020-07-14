package com.godfunc.paopao.service;

import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.ui.Model;

/**
 * @author Godfunc
 * @date 2020/2/7 17:08
 */

public interface IWxService {

    WxMpOAuth2AccessToken getAccessToken(String code);

    WxMpUser getUserInfo(WxMpOAuth2AccessToken accessToken);

    boolean auth(String code, String state);

    boolean sendKfMsg(String openId, String msg, String link) throws WxErrorException;

    String sendTempMsg(String openid, String msg, String link) throws WxErrorException;

    void bindGroup(String code, String state, Model model);
}
