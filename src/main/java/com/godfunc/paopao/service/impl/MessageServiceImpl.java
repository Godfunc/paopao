package com.godfunc.paopao.service.impl;

import com.baomidou.mybatisplus.extension.api.R;
import com.godfunc.paopao.constant.CommonConstant;
import com.godfunc.paopao.entity.Group;
import com.godfunc.paopao.entity.User;
import com.godfunc.paopao.result.SendInfoResult;
import com.godfunc.paopao.service.IGroupService;
import com.godfunc.paopao.service.IMessageService;
import com.godfunc.paopao.service.IUserService;
import com.godfunc.paopao.service.IWxService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Godfunc
 * @date 2020/2/8 17:40
 */
@Service
@Slf4j
public class MessageServiceImpl implements IMessageService {

    @Autowired
    private IWxService wxService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IGroupService groupService;


    @Override
    public R send(String token, String type, String msg, String link) {
        User user = userService.getByToken(token);
        if (user == null) {
            return R.failed("接收用户不存在");
        } else if (StringUtils.isBlank(user.getOpenid())) {
            return R.failed("接收用户未完成微信绑定");
        }
        try {
            sendMsg(user.getOpenid(), msg, type, link);
            return R.ok("消息发送成功");
        } catch (WxErrorException e) {
            log.error("消息发送失败，openId={}, msg={}", user.getOpenid(), msg);
            if(e.getError().getErrorCode() == 45015) {
                return R.failed("KF消息需要接收方先向微信公众号主动发送一条消息");
            } else {
                return R.failed("消息发送失败，msg：" + e.getMessage());
            }
        }
    }

    @Override
    public R send2Alias(String token, String alias, String type, String msg, String link) {
        User user = userService.getByToken(token);
        if (user == null) {
            return R.failed("无效的token");
        } else if (StringUtils.isBlank(user.getOpenid())) {
            return R.failed("请您先进行微信绑定");
        }
        User receiver = userService.getByAlias(alias);
        if (receiver == null) {
            return R.failed("接收方不存在，请检查alias是否正确");
        } else if (StringUtils.isBlank(receiver.getOpenid())) {
            return R.failed("接收方未进行微信绑定，请先进行微信绑定");
        } else {
            try {
                msg = msg +
                        CommonConstant.SENDER_INFO +
                        "【" + user.getNikeName() +
                        "】";
                sendMsg(receiver.getOpenid(), msg, type, link);
                return R.ok("消息发送成功");
            } catch (WxErrorException e) {
                log.error("消息发送失败，openId={}, msg={}", receiver.getOpenid(), msg);
                if(e.getError().getErrorCode() == 45015) {
                    return R.failed("KF消息需要接收方先向微信公众号主动发送一条消息");
                } else {
                    return R.failed("消息发送失败，msg：" + e.getMessage());
                }
            }
        }
    }

    @Override
    public R send2Group(String token, String groupUid, String type, String msg, String link) {
        User user = userService.getByToken(token);
        if (user == null) {
            return R.failed("接收用户不存在");
        } else if (StringUtils.isBlank(user.getOpenid())) {
            return R.failed("接收用户未完成微信绑定");
        }
        Group creator = groupService.getCreatorByGroupUid(groupUid);
        if (creator == null) {
            return R.failed("您输入的组不存在");
        } else {
            List<Group> numbers = groupService.getNumberByGroupUid(groupUid);
            if (CollectionUtils.isNotEmpty(numbers)) {
                numbers.add(creator);
                // 判断发送者在不在组里面
                if (numbers.stream().noneMatch(g -> g.getUserId().equals(user.getId()))) {
                    return R.failed("您输入的组不存在");
                }
                Map<String, SendInfoResult> sendInfo = new HashMap<>();
                msg = msg +
                        CommonConstant.SENDER_INFO +
                        "【" + user.getNikeName() +
                        "】";
                for (int i = 0; i < numbers.size(); i++) {
                    if (!numbers.get(i).getUserId().equals(user.getId())) {
                        try {
                            sendMsg(numbers.get(i).getOpenid(), msg, type, link);
                            sendInfo.put(numbers.get(i).getNikeName(), new SendInfoResult().setStatus(CommonConstant.STATUS_SUCCESS).setMsg(CommonConstant.SUCCESS_MSG));
                        } catch (WxErrorException e) {
                            log.error("消息发送失败，openId={}, msg={}", user.getOpenid(), msg);
                            if(e.getError().getErrorCode() == 45015) {
                                sendInfo.put(numbers.get(i).getNikeName(), new SendInfoResult().setStatus(CommonConstant.STATUS_FAIL).setMsg("KF消息需要接收方先向微信公众号主动发送一条消息"));
                            } else {
                                sendInfo.put(numbers.get(i).getNikeName(), new SendInfoResult().setStatus(CommonConstant.STATUS_FAIL).setMsg(e.getMessage()));
                            }
                        }
                    }
                }
                return R.ok(sendInfo);
            } else {
                return R.failed("该组没有其他成员，请先添加成员");
            }
        }
    }


    public void sendMsg(String openId, String msg, String type, String link) throws WxErrorException {
        if (CommonConstant.MSG_TYPE_KF.equals(type)) {
            wxService.sendKfMsg(openId, msg, link);
        } else {
            String s = wxService.sendTempMsg(openId, msg, link);
            log.info("tempMsg = {}", s);
        }
    }
}