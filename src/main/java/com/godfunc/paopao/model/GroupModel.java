package com.godfunc.paopao.model;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Godfunc
 * @date 2020/2/7 20:17
 */
@Data
public class GroupModel implements Serializable {

    private String id;

    private String name;

    private String groupUid;

    private String nikeName;

    private String userId;

    private Integer type;

    private LocalDateTime createTime;
}
