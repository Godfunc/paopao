package com.godfunc.paopao.util;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

public class EncryptUtils {

    private static final StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();

    public static String encrypt(String origin, String password) {
        encryptor.setPassword(password);
        return encryptor.encrypt(origin);
    }

    public static String decrypt(String origin, String password) {
        encryptor.setPassword(password);
        return encryptor.decrypt(origin);
    }
}
