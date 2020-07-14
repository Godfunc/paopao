package com.godfunc.paopao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.godfunc.paopao.entity.Group;
import com.godfunc.paopao.model.GroupModel;

import java.util.List;

/**
 * <p>
 * 组 Mapper 接口
 * </p>
 *
 * @author Godfunc
 * @since 2020-02-07
 */
public interface GroupMapper extends BaseMapper<Group> {

    List<GroupModel> selectMyGroup(String token);

    List<GroupModel> selectJoinedGroup(String token);
}
