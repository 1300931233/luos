package com.luos.common.utils;

import org.apache.shiro.crypto.hash.Md5Hash;


/**
 * 密码加密的处理工具类
 */
public class PasswordUtils {

    /**
     * 迭代次数
     */
    private static final int ITERATIONS = 6;

    private PasswordUtils() {
        throw new AssertionError();
    }

    /**
     * 字符串加密函数MD5实现
     *
     * @param password  密码
     * @param loginName 用户名
     * @return
     */
    public static String getPassword(String password, String loginName) {
        return new Md5Hash(password, loginName, ITERATIONS).toString();
    }

    public static void main(String[] args) {
        System.out.println(getPassword("123456", "13888888888"));
    }
}