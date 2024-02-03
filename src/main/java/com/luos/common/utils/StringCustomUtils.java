package com.luos.common.utils;

/**
 * 自定义字符串处理工具
 **/
public class StringCustomUtils {

    /**
     * 判断字符串是否为数字
     */
    public static boolean isNumber(String str) {
        return ((str != null) && (((str.matches("[+-]?[0-9]+\\.?[0-9]*")) || (str.matches("[+-]?[0-9]*\\.?[0-9]+")))));
    }

    /**
     * 判断字符串是否为整数
     */
    public static boolean isInteger(String str) {
        return ((str != null) && (str.matches("[+-]?[0-9]+")));
    }

    /**
     * 判断字符串是否为自然数（非负整数）
     */
    public static boolean isNaturalNumber(String str) {
        return ((str != null) && (str.matches("+?[0-9]+")));
    }

    /**
     * 判断字符串是否为boolean类型
     */
    public static boolean isBoolean(String str) {
        return ((!"".equals(str) && str != null) && ((("true".equals(str)) || ("false".equals(str)))));
    }

    /**
     * 判断字符串是否全为字母
     */
    public static boolean isLetters(String str) {
        return ((!"".equals(str) && str != null) && (str.matches("[a-zA-z]+")));
    }

    public static void main(String[] args) {
        System.out.println(isLetters("aaa1"));
    }
}
