package com.godfunc.paopao.service;

import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.service.IService;
import com.godfunc.paopao.entity.User;
import com.godfunc.paopao.param.GroupCreateParam;
import org.springframework.ui.Model;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author Godfunc
 * @since 2020-02-07
 */
public interface IUserService extends IService<User> {

    String getAuthorizationUrl(String redirectUri, String state);

    String getLoginQrCode(String sessionId);

    String queryLogin(String sessionId);

    String console(String token, Model model);

    User getByToken(String token);

    R createGroup(String token, GroupCreateParam param);

    User getByOpenId(String openId);

    R getGroupQrCode(String token, String groupUid);

    R groupNumberDelete(String token, String groupId);

    User getByAlias(String alias);

    R deleteGroup(String token, String groupUid);
}
