package com.godfunc.paopao.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author Godfunc
 * @date 2020/2/7 20:00
 */
public class MD5Utils {

    public static String encoder(String content, String key) {
        return DigestUtils.md5Hex(content + key);
    }
}
