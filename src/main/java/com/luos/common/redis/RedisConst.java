package com.luos.common.redis;


import com.luos.common.consts.SystemConst;

/**
 * redis常量类
 */
public final class RedisConst {

    /**
     * Redis 键
     */
    public static final class Key {
        /**
         * 系统信息缓存
         */
        public static final String SYS_CONFIGURE = SystemConst.SYSTEM_ITEM_NAME + ":sys:configure";

        /**
         * 系统参数配置
         */
        public static final String SYS_PARAM_CONF = SystemConst.SYSTEM_ITEM_NAME + ":sys:paramConf";

        /**
         * 服务监控信息
         */
        public static final String SYS_SERVER_INFO = SystemConst.SYSTEM_ITEM_NAME + ":sys:server_info";

        /**
         * 字典信息缓存
         */
        public static final String SYS_DICT = SystemConst.SYSTEM_ITEM_NAME + ":sys:dict:";

        /**
         * 前台用户token缓存
         */
        public static final String API_USER_TOKEN = "resai:user:token";

        /**
         * WinRAR.exe解压程序安装路径
         */
        public static final String WINRAR_INSTALL_PATH = SystemConst.SYSTEM_ITEM_NAME + ":install:path";
    }

    /**
     * Redis 通道
     */
    public static final class Channel {

    }
}
