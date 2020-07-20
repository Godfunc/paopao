package com.godfunc.paopao.param;

import java.security.Principal;

/**
 * websocket登录连接对象
 * 用于保存websocket连接过程中需要存储的业务参数
 **/
public class WebSocketUserAuthentication implements Principal{

    /**
     * 用户身份标识符
     */
    private String id;

    public WebSocketUserAuthentication(String id) {
        this.id = id;
    }


    /**
     * 获取用户登录令牌
     * @return
     */
    @Override
    public String getName() {
        return id;
    }

    @Override
    public String toString() {
        return "WebSocketUserAuthentication{" +
                "id='" + id + '\'' +
                '}';
    }
}
