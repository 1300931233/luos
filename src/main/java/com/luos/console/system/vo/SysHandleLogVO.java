package com.luos.console.system.vo;

import com.luos.console.system.entity.SysHandleLog;
import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName SysHandleLogVO
 * @Description
 **/
@Getter
@Setter
public class SysHandleLogVO extends SysHandleLog {

    private String createUserName;
}
