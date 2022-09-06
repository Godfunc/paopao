CREATE TABLE IF NOT EXISTS `t_user`
(
    `id`                VARCHAR(32)  NOT NULL,
    `nike_name`         VARCHAR(512) NOT NULL,
    `token`             VARCHAR(64)  NOT NULL,
    `alias`             VARCHAR(64)  NOT NULL,
    `openid`            VARCHAR(128),
    `registration_time` DATETIME     NOT NULL,
    `last_login_time`   DATETIME     NOT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `t_group`
(
    `id`          VARCHAR(32)  NOT NULL,
    `group_uid`   VARCHAR(32)  NOT NULL,
    `name`        VARCHAR(512) NOT NULL,
    `user_id`     VARCHAR(16)  NOT NULL,
    `nike_name`   VARCHAR(512) NOT NULL,
    `email`       VARCHAR(64),
    `type`        TINYINT(4)   NOT NULL,
    `create_time` DATETIME     NOT NULL,
    PRIMARY KEY (`id`)
);