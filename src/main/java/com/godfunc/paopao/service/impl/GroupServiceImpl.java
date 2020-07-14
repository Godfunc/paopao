package com.godfunc.paopao.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.godfunc.paopao.constant.CommonConstant;
import com.godfunc.paopao.entity.Group;
import com.godfunc.paopao.mapper.GroupMapper;
import com.godfunc.paopao.model.GroupModel;
import com.godfunc.paopao.service.IGroupService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 组 服务实现类
 * </p>
 *
 * @author Godfunc
 * @since 2020-02-07
 */
@Service
public class GroupServiceImpl extends ServiceImpl<GroupMapper, Group> implements IGroupService {



    @Override
    public Map<String, List<GroupModel>> selectMyGroup(String token) {
        List<GroupModel> list = this.baseMapper.selectMyGroup(token);
        if (CollectionUtils.isNotEmpty(list)) {
            return list.stream().collect(Collectors.groupingBy(GroupModel::getGroupUid));
        } else {
            return null;
        }
    }

    @Override
    public Map<String, List<GroupModel>> selectJoinedGroup(String token) {
        List<GroupModel> list = this.baseMapper.selectJoinedGroup(token);
        if (CollectionUtils.isNotEmpty(list)) {
            return list.stream().collect(Collectors.groupingBy(GroupModel::getGroupUid));
        } else {
            return null;
        }
    }

    @Override
    public List<Group> getNumberByGroupUid(String groupUid) {
        return list(Wrappers.<Group>lambdaQuery().eq(Group::getGroupUid, groupUid).eq(Group::getType, CommonConstant.NUMBER));

    }

    @Override
    public boolean deleteByGroupUid(String groupUid) {
        return remove(Wrappers.<Group>lambdaQuery().eq(Group::getGroupUid, groupUid));
    }

    @Override
    public Group getCreatorByGroupUid(String groupUid) {
        return getOne(Wrappers.<Group>lambdaQuery().eq(Group::getGroupUid, groupUid).eq(Group::getType, CommonConstant.CREATOR));
    }

    @Override
    public boolean createNumber(String name, String groupUid, String userId, String nikeName, String openid) {
        Group group = new Group()
                .setType(CommonConstant.NUMBER)
                .setNikeName(nikeName)
                .setName(name)
                .setUserId(userId)
                .setOpenid(openid)
                .setGroupUid(groupUid)
                .setCreateTime(LocalDateTime.now());
        return save(group);
    }

    @Override
    public boolean checkNumber(String userId, String groupUid) {
        return count(Wrappers.<Group>lambdaQuery().eq(Group::getUserId, userId).eq(Group::getGroupUid, groupUid).eq(Group::getType, CommonConstant.NUMBER)) > 0;
    }



}
