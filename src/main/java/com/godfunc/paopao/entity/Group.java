package com.godfunc.paopao.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 组
 * </p>
 *
 * @author Godfunc
 * @since 2020-02-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_group")
public class Group implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 组id
     */
    @TableId
    private String id;

    /**
     * 用户
     */
    private String userId;

    private String groupUid;

    /**
     * 组名称
     */
    private String name;

    /**
     * 用户名
     */
    private String nikeName;

    private String openid;

    /**
     * email
     */
    private String email;

    /**
     * 类型，1发起人 2成员
     */
    private Integer type;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    public String generateGroupUid() {
        return DigestUtils.md5Hex(this.getId() + this.getName() + this.getUserId() + createTime.toString());
    }

}
