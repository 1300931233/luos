package com.asurplus.common.enums;

import lombok.AllArgsConstructor;
import lombok.Setter;

/**
 * 异常枚举类
 */
@AllArgsConstructor
public enum StatusEnums implements BaseEnums {

    /**
     * 业务枚举
     */
    OK(200, "操作成功"),
    ERROR(500, "操作失败"),

    LOGIN_SUCCESS(200, "登录成功"),
    LOGIN_ERROR(500, "登录失败"),

    SELECT_SUCCESS(200, "查询成功"),
    SELECT_ERROR(500, "查询失败"),

    SAVE_SUCCESS(200, "保存成功"),
    SAVE_ERROR(500, "保存失败"),

    UPDATE_SUCCESS(200, "更新成功"),
    UPDATE_ERROR(500, "更新失败"),

    DELETE_SUCCESS(200, "删除成功"),
    DELETE_ERROR(500, "删除失败"),

    UPLOAD_SUCCESS(200, "上传成功"),
    UPLOAD_ERROR(500, "上传失败"),

    AUDIT_SUCCESS(200, "审批成功"),
    AUDIT_ERROR(500, "审批失败"),

    KAPTCH_ERROR(703, "验证码不正确"),

    UNAUTHORIZED_ERROR(401, "登录信息过期"),

    FORBIDDEN_ERROR(403, "未授权"),

    SUCCESS(1000, "请求成功"),
    PARAMETER_ERROR(1001, "请求参数有误!"),
    UNKNOWN_ERROR(9999, "未知的错误!");

    @Setter
    private int code;

    @Setter
    private String msg;

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }
}