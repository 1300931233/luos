package com.luos.console.system.vo;

import lombok.Data;

@Data
public class LoginVO {

    private String account;

    private String password;

    private String resCode;

    private Boolean rememberMe;
}
