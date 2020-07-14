package com.godfunc.paopao.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.godfunc.paopao.util.MD5Utils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author Godfunc
 * @since 2020-02-07
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private String id;

    /**
     * 用户名
     */
    private String nikeName;

    /**
     * 微信openid
     */
    private String openid;

    /**
     * 凭证
     */
    private String token;

    /**
     * 别名
     */
    private String alias;

    /**
     * 注册时间
     */
    private LocalDateTime registrationTime;

    /**
     * 最后登陆时间
     */
    private LocalDateTime lastLoginTime;

    public String generateAlias(String md5Key) {
        return MD5Utils.encoder(this.getNikeName() + IdWorker.getIdStr(), md5Key);
    }

    public String generateToken(String md5Key) {
        return MD5Utils.encoder(this.getOpenid() + IdWorker.getIdStr(), md5Key);
    }

}
