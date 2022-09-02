package com.godfunc.paopao.service.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.godfunc.paopao.result.R;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.godfunc.paopao.constant.CommonConstant;
import com.godfunc.paopao.entity.Group;
import com.godfunc.paopao.entity.User;
import com.godfunc.paopao.mapper.UserMapper;
import com.godfunc.paopao.model.GroupModel;
import com.godfunc.paopao.model.UserInfoModel;
import com.godfunc.paopao.param.GroupCreateParam;
import com.godfunc.paopao.service.IGroupService;
import com.godfunc.paopao.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.api.WxMpService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author Godfunc
 * @since 2020-02-07
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Value("${authUrl}")
    private String authUrl;
    @Value("${bindUrl}")
    private String bindUrl;
    @Value("${md5Key}")
    private String md5Key;

    Pattern emailPattern = Pattern.compile("^[a-z\\d]+(\\.[a-z\\d]+)*@([\\da-z](-[\\da-z])?)+(\\.{1,2}[a-z]+)+$");

    @Autowired
    private WxMpService wxMpService;
    @Autowired
    private IGroupService groupService;

    @Override
    public String getLoginQrCode(String sessionId, String baseUrl) {
        log.debug("getLoginQrCode sessionId={}", sessionId);
        return getAuthorizationUrl(baseUrl + authUrl, sessionId);
    }

    @Override
    public String console(User user, Model model) {
        log.debug("console token = {}", user);
        if (user == null) {
            // 用户不存在
            return "redirect:/user/login";
        } else {
            UserInfoModel userInfoModel = new UserInfoModel();
            userInfoModel.setAlias(user.getAlias());
            userInfoModel.setNikeName(user.getNikeName());
            userInfoModel.setToken(user.getToken());
            Map<String, List<GroupModel>> myGroup = groupService.selectMyGroup(userInfoModel.getToken());
            Map<String, List<GroupModel>> joinedGroup = groupService.selectJoinedGroup(userInfoModel.getToken());
            userInfoModel.setMyGroups(myGroup);
            userInfoModel.setJoinedGroups(joinedGroup);
            model.addAttribute("info", userInfoModel);
            return "console";
        }
    }

    @Override
    public User getByToken(String token) {
        return getOne(Wrappers.<User>lambdaQuery().eq(User::getToken, token));
    }

    @Override
    public R createGroup(User user, GroupCreateParam param) {
        if (user == null) {
            return R.failed("登陆已过期或用户不存在，请重新扫码登陆");
        } else if (StringUtils.isBlank(user.getOpenid())) {
            return R.failed("请先进行微信绑定");
        }
        if (param == null) {
            return R.failed("请输入组参数");
        } else if (StringUtils.isBlank(param.getName())) {
            return R.failed("请将组名称补充完整");
        } else if (StringUtils.isBlank(param.getEmail())) {
            return R.failed("请将邮箱补充完整");
        }
        Matcher matcher = emailPattern.matcher(param.getEmail());
        if (!matcher.matches()) {
            return R.failed("邮箱格式不正确");
        }
        int count = groupService.count(Wrappers.<Group>lambdaQuery().eq(Group::getUserId, user.getId()).eq(Group::getName, param.getName()));
        if (count > 0) {
            return R.failed("当前组以存在");
        } else {
            Group group = new Group()
                    .setId(IdWorker.getIdStr())
                    .setName(param.getName())
                    .setUserId(user.getId())
                    .setEmail(param.getEmail())
                    .setNikeName(user.getNikeName())
                    .setOpenid(user.getOpenid())
                    .setType(CommonConstant.CREATOR)
                    .setCreateTime(LocalDateTime.now());
            group.setGroupUid(group.generateGroupUid(md5Key));
            groupService.save(group);
            return R.ok(group.getId());
        }
    }

    @Override
    public User getByOpenId(String openId) {
        return getOne(Wrappers.<User>lambdaQuery().eq(User::getOpenid, openId));
    }

    @Override
    public R getGroupQrCode(User user, String groupUid, String baseUrl) {
        if (user == null) {
            return R.failed("请重新登陆");
        }
        Group group = groupService.getCreatorByGroupUid(groupUid);
        if (group == null || !group.getUserId().equals(user.getId())) {
            return R.failed("您选择的组不存在或已被删除");
        } else {
            return R.ok(getAuthorizationUrl(baseUrl + bindUrl, groupUid));
        }
    }

    @Override
    public R groupNumberDelete(User user, String groupId) {
        if (user == null) {
            return R.failed("请重新登陆");
        }
        Group group = groupService.getById(groupId);

        if (group == null) {
            return R.failed("您选择的组员不存在或已被删除");
        } else {
            Group creator = groupService.getCreatorByGroupUid(group.getGroupUid());
            if (creator == null) {
                return R.failed("该组不存在或已被删除");
            } else if (creator.getUserId().equals(user.getId())) {
                groupService.removeById(groupId);
                return R.ok("删除组员【" + group.getNikeName() + "】成功");
            } else if (!creator.getUserId().equals(user.getId())) {
                return R.failed("您不是该组的组长，无法进行操作");
            } else {
                return R.failed("删除组员【" + group.getNikeName() + "】失败");
            }
        }
    }

    @Override
    public User getByAlias(String alias) {
        return getOne(Wrappers.<User>lambdaQuery().eq(User::getAlias, alias));
    }

    @Override
    public R deleteGroup(User user, String groupUid) {
        if (user == null) {
            return R.failed("请重新登陆");
        }
        Group creator = groupService.getCreatorByGroupUid(groupUid);
        if (creator == null) {
            return R.failed("您选择的组不存在或已被删除");
        } else if (!user.getId().equals(creator.getUserId())) {
            return R.failed("您选择的组不存在或已被删除");
        } else if (user.getId().equals(creator.getUserId())) {
            groupService.deleteByGroupUid(groupUid);
            return R.ok("删除删除");
        }
        return R.failed("删除组失败");
    }

    @Override
    public R leaveGroup(User user, String groupUid) {
        if (user == null) {
            return R.failed("请重新登陆");
        }
        if (groupService.levelGroup(user.getId(), groupUid)) {
            return R.ok("离开成功");
        } else {
            return R.failed("改组不存在或您已不在组中");
        }
    }

    @Override
    public String getAuthorizationUrl(String redirectUri, String state) {
        String url = wxMpService.getOAuth2Service().buildAuthorizationUrl(redirectUri, WxConsts.OAuth2Scope.SNSAPI_USERINFO, state);
        log.info("AuthorizationUrl {}", url);
        return url;
    }
}
