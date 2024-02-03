package com.luos.common.redis;

import cn.hutool.core.collection.CollectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis操作工具
 **/
@Slf4j
@Component
public class RedisUtil {

    @Autowired
    private RedisTemplate<String, Object> template;

    /**
     * 根据key读取数据
     */
    public Object get(final String key) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        try {
            return template.opsForValue().get(key);
        } catch (Exception e) {
            e.printStackTrace();
            log.info("获取redis成功，key：{}", key);
        }
        return null;
    }

    /**
     * 写入数据
     */
    public boolean set(final String key, Object value) {
        if (StringUtils.isBlank(key)) {
            return false;
        }
        try {
            template.opsForValue().set(key, value);
            log.info("存入redis成功，key：{}，value：{}", key, value);
            return true;
        } catch (Exception e) {
            log.error("存入redis失败，key：{}，value：{}", key, value);
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 写入数据，并设置过期时间
     */
    public boolean set(final String key, Object value, long seconds) {
        if (StringUtils.isBlank(key)) {
            return false;
        }
        try {
            template.opsForValue().set(key, value, seconds, TimeUnit.SECONDS);
            log.info("存入redis成功，key：{}，value：{}，seconds：{}", key, value, seconds);
            return true;
        } catch (Exception e) {
            log.error("存入redis失败，key：{}，value：{}，seconds：{}", key, value, seconds);
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 批量写入数据
     */
    public boolean mset(Map<String, Object> map) {
        if (CollectionUtil.isEmpty(map)) {
            return false;
        }
        try {
            template.opsForValue().multiSet(map);
            log.info("批量存入redis成功，key：{}，value：{}", map);
            return true;
        } catch (Exception e) {
            log.error("批量存入redis失败，key：{}，value：{}", map);
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 将存储为字符串值的整数值相加
     */
    public long incrby(final String key, Long value) {
        return template.opsForValue().increment(key, value);
    }

    /**
     * 将存储为字符串值的整数值递增1
     */
    public long incr(final String key) {
        return template.opsForValue().increment(key);
    }

    /**
     * 将存储为字符串值的整数值相减
     */
    public long decrby(final String key, Long value) {
        return template.opsForValue().decrement(key, value);
    }

    /**
     * 将存储为字符串值的整数值递减1
     */
    public long decr(final String key) {
        return template.opsForValue().decrement(key);
    }

    /**
     * 根据key更新数据
     */
    public boolean update(final String key, Object value) {
        if (StringUtils.isBlank(key)) {
            return false;
        }
        try {
            template.opsForValue().getAndSet(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 根据key删除数据
     */
    public boolean del(final String key) {
        if (StringUtils.isBlank(key)) {
            return false;
        }
        try {
            template.delete(key);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 根据key批量删除数据
     */
    public boolean delBatch(final Set<String> keys) {
        if (CollectionUtil.isEmpty(keys)) {
            return false;
        }
        try {
            template.delete(keys);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 根据key批量删除数据
     */
    public boolean delBatch(final List<String> keys) {
        if (CollectionUtil.isEmpty(keys)) {
            return false;
        }
        try {
            template.delete(keys);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 是否存在key
     * <p>
     * true -- 存在
     * false -- 不存在
     */
    public boolean hasKey(final String key) {
        if (StringUtils.isBlank(key)) {
            return false;
        }
        try {
            return template.hasKey(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 给指定的key设置存活时间
     * <p>
     * 默认为-1，表示永久不失效
     */
    public boolean setExpire(final String key, long seconds) {
        if (StringUtils.isBlank(key)) {
            return false;
        }
        try {
            if (0 < seconds) {
                return template.expire(key, seconds, TimeUnit.SECONDS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获取指定key的剩余存活时间
     * <p>
     * -1，表示永久不失效（默认值）
     * -2表示该key不存在
     */
    public long getExpire(final String key) {
        if (StringUtils.isBlank(key)) {
            return -2;
        }
        try {
            return template.getExpire(key, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 移除指定key的有效时间
     * <p>
     * false -- 当key的有效时间为-1即永久不失效和当key不存在时返回
     * true -- 移除成功否则返回
     */
    public boolean persist(final String key) {
        if (StringUtils.isBlank(key)) {
            return false;
        }
        try {
            return template.persist(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 根据前缀获取所有的key
     * <p>
     * 例如：pro_*
     */
    public Set<String> getKeys(String prefix) {
        try {
            return template.keys(prefix.concat("*"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据前缀批量获取value
     */
    public List<Object> getValues(String prefix) {
        try {
            Set<String> keys = template.keys(prefix.concat("*"));
            return template.opsForValue().multiGet(keys);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 往key的列表键中左边放入一个元素，key不存在则新建
     */
    public long leftPush(final String key, Object value) {
        return template.opsForList().leftPush(key, value);
    }

    /**
     * 往key的列表键中右边放入一个元素，key不存在则新建
     */
    public long rightPush(final String key, Object value) {
        return template.opsForList().rightPush(key, value);
    }

    /**
     * 从key的列表键最右端弹出一个元素
     */
    public Object rightPop(final String key) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        try {
            return template.opsForList().rightPop(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 从key的列表键最左端弹出一个元素
     */
    public Object leftPop(final String key) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        try {
            return template.opsForList().leftPop(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 向通道发布消息
     */
    public boolean convertAndSend(String channel, Object value) {
        if (StringUtils.isBlank(channel)) {
            return false;
        }
        try {
            template.convertAndSend(channel, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获取数据 hashmap
     */
    public Object getHashMap(String key, Object filed) {
        if (StringUtils.isBlank(key)) {
            return null;
        }
        if (null == filed) {
            return null;
        }
        return template.opsForHash().get(key, filed);
    }

    /**
     * 通过has键查询键和值
     * @param key
     * @return
     */
    public Map<Object,Object> hmget(String key){
        return template.opsForHash().entries(key);
    }

    /**
     * 存入数据 hashmap
     */
    public boolean setHashMap(String key, Object filed, Object value) {
        if (StringUtils.isBlank(key)) {
            return false;
        }
        if (null == filed) {
            return false;
        }
        try {
            template.opsForHash().put(key, filed, value);
            log.info("存入redis成功，key：{}，filed:{}，value：{}", key, filed, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.info("存入redis失败，key：{}，filed:{}，value：{}", key, filed, value);
        }
        return false;
    }

    /**
     * 批量存入数据 hashmap
     */
    public boolean setHashMap(String key, Map<Object, Object> map) {
        if (StringUtils.isBlank(key)) {
            return false;
        }
        if (null == map || map.isEmpty()) {
            return false;
        }
        try {
            template.opsForHash().putAll(key, map);
            log.info("存入redis成功，key：{}，value：{}", key, map);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.info("存入redis失败，key：{}，value：{}", key, map);
        }
        return false;
    }

    /**
     * 删除数据 hashmap
     */
    public boolean deleteHashMap(String key, Object filed) {
        if (StringUtils.isBlank(key)) {
            return false;
        }
        if (null == filed) {
            return false;
        }
        try {
            template.opsForHash().delete(key, filed);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
