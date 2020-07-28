package com.godfunc.paopao.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author Godfunc
 * @date 2020/2/7 20:15
 */
@Data
public class UserInfoModel implements Serializable {
    private String nikeName;

    private String token;

    private String alias;

    // 我的组
    private Map<String, List<GroupModel>> myGroups;

    // 我加入的组
    private Map<String, List<GroupModel>> joinedGroups;
}
