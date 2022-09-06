CREATE TABLE IF NOT EXISTS `t_user`
(
    `id`                VARCHAR(32)  NOT NULL,
    `nike_name`         VARCHAR(512) NOT NULL COMMENT '用户名',
    `token`             VARCHAR(64)  NOT NULL COMMENT '凭证',
    `alias`             VARCHAR(64)  NOT NULL COMMENT '别名',
    `openid`            VARCHAR(128) COMMENT '微信openid',
    `registration_time` DATETIME     NOT NULL COMMENT '注册时间',
    `last_login_time`   DATETIME     NOT NULL COMMENT '最后登陆时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `u_alias` (`alias`),
    UNIQUE KEY `u_token` (`token`),
    UNIQUE KEY `u_openid` (`openid`)
) COMMENT '用户表';

CREATE TABLE IF NOT EXISTS `t_group`
(
    `id`          VARCHAR(32)  NOT NULL COMMENT '组id',
    `group_uid`   VARCHAR(32)  NOT NULL COMMENT '组uid',
    `name`        VARCHAR(512) NOT NULL COMMENT '组名称',
    `user_id`     VARCHAR(16)  NOT NULL COMMENT '用户',
    `nike_name`   VARCHAR(512) NOT NULL COMMENT '用户名',
    `email`       VARCHAR(64) COMMENT 'email',
    `type`        TINYINT(4)   NOT NULL COMMENT '类型，1发起人 2成员',
    `create_time` DATETIME     NOT NULL COMMENT '创建时间',
    PRIMARY KEY (`id`),
    INDEX `u_user_id_group_uid` (`user_id`, `group_uid`)
) COMMENT '组';