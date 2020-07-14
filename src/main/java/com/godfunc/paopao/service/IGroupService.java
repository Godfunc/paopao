package com.godfunc.paopao.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.godfunc.paopao.entity.Group;
import com.godfunc.paopao.model.GroupModel;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 组 服务类
 * </p>
 *
 * @author Godfunc
 * @since 2020-02-07
 */
public interface IGroupService extends IService<Group> {

    Map<String, List<GroupModel>> selectMyGroup(String token);

    Group getCreatorByGroupUid(String groupUid);

    boolean createNumber(String name, String groupUid, String userId, String nikeName, String openid);

    boolean checkNumber(String userId, String groupUid);

    Map<String, List<GroupModel>> selectJoinedGroup(String token);

    List<Group> getNumberByGroupUid(String groupUid);

    boolean deleteByGroupUid(String groupUid);

}
