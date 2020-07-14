package com.godfunc.paopao.service;

import com.baomidou.mybatisplus.extension.api.R;

/**
 * @author Godfunc
 * @date 2020/2/8 17:40
 */
public interface IMessageService {
    R send(String token, String type, String msg, String link);

    R send2Alias(String token, String alias, String type, String msg, String link);

    R send2Group(String token, String groupUid, String type, String msg, String link);
}
