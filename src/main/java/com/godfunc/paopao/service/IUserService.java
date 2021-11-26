package com.godfunc.paopao.service;

import com.godfunc.paopao.result.R;
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

    String console(User user, Model model);

    User getByToken(String token);

    R createGroup(User user, GroupCreateParam param);

    User getByOpenId(String openId);

    R getGroupQrCode(User user, String groupUid);

    R groupNumberDelete(User user, String groupId);

    User getByAlias(String alias);

    R deleteGroup(User user, String groupUid);

    R leaveGroup(User user, String groupUid);

}
