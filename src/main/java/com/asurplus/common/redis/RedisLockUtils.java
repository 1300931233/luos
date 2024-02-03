package com.asurplus.common.redis;

import com.asurplus.common.consts.SystemConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class RedisLockUtils {

    /**
     * 锁名称
     */
    public static final String LOCK_PREFIX = SystemConst.SYSTEM_ITEM_NAME + ":apiLock:";
    /**
     * 锁失效时间，毫秒
     */
    public static final int LOCK_EXPIRE = 5000;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 最终加强分布式锁
     */
    public boolean setLock(String key) {
        String lock = LOCK_PREFIX + key;
        // 利用lambda表达式
        return (Boolean) redisTemplate.execute((RedisCallback) connection -> {
            long expireAt = System.currentTimeMillis() + LOCK_EXPIRE + 1;
            Boolean acquire = connection.setNX(lock.getBytes(), String.valueOf(expireAt).getBytes());
            if (acquire) {
                return true;
            } else {
                byte[] value = connection.get(lock.getBytes());
                if (Objects.nonNull(value) && value.length > 0) {
                    long expireTime = Long.parseLong(new String(value));
                    // 如果锁已经过期
                    if (expireTime < System.currentTimeMillis()) {
                        // 重新加锁，防止死锁
                        byte[] oldValue = connection.getSet(lock.getBytes(), String.valueOf(System.currentTimeMillis() + LOCK_EXPIRE + 1).getBytes());
                        return Long.parseLong(new String(oldValue)) < System.currentTimeMillis();
                    }
                }
            }
            return false;
        });
    }

    /**
     * 删除锁
     */
    public void delete(String key) {
        redisTemplate.delete(key);
    }

}
