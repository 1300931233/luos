package com.luos.common.utils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * @description：BeanUtils拓展工具类
 */

public class BeanUtil extends BeanUtils {
    public BeanUtil() {
    }

    public static <T> T propertiesCopy(Object source, Class<T> clazz) {
        if (null == source) {
            return null;
        } else {
            try {
                T obj = clazz.newInstance();
                BeanUtils.copyProperties(source, obj);
                return obj;
            } catch (IllegalAccessException | InstantiationException var3) {
                throw new RuntimeException(var3);
            }
        }
    }

    /**
     * list中对象的copy
     * @param source
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> CollectionCopy(Collection source, Class<T> clazz) {
        if (null == source) {
            return new ArrayList();
        } else {
            List<T> list = new ArrayList();
            Iterator var3 = source.iterator();

            while(var3.hasNext()) {
                Object o = var3.next();
                list.add(propertiesCopy(o, clazz));
            }

            return list;
        }
    }

    /**
     * IPage(分页)对象中list中对象的copy
     * @param page
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> IPage<T> PageCollectionCopy(IPage<?> page, Class<T> clazz) {
        if (page == null) {
            return null;
        } else {
            Collection source = page.getRecords();
            Iterator var3 = source.iterator();
            List<T> list = new ArrayList<>();
            while(var3.hasNext()) {
                Object o = var3.next();
                list.add(propertiesCopy(o, clazz));
            }
            IPage<T> iPage = new Page<>();
            iPage.setRecords(list);
            iPage.setPages(page.getPages());
            iPage.setCurrent(page.getCurrent());
            iPage.setSize(page.getSize());
            iPage.setTotal(page.getTotal());
            return iPage;
        }
    }

    /**
     * 将对象转换为map
     * @param obj
     * @return
     */
    public static Map<String, Object> object2Map(Object obj) {
        Map<String, Object> map = new HashMap();
        if (obj == null) {
            return map;
        } else {
            Class clazz = obj.getClass();
            Field[] fields = clazz.getDeclaredFields();

            try {
                Field[] var4 = fields;
                int var5 = fields.length;

                for(int var6 = 0; var6 < var5; ++var6) {
                    Field field = var4[var6];
                    field.setAccessible(true);
                    map.put(field.getName(), field.get(obj));
                }

                return map;
            } catch (Exception var8) {
                throw new RuntimeException(var8);
            }
        }
    }


    /**
     * 将map转换为对象,必须保证属性名称相同
     * @return
     */
    public static Object map2Object(Map<Object,Object> map,Class<?> clzz){
        try {
            Object target = clzz.newInstance();
            if(CollectionUtils.isEmpty(map)){
                return target;
            }
            Field[] fields = clzz.getDeclaredFields();
            if(!CollectionUtils.isEmpty(Arrays.asList(fields))){
                Arrays.stream(fields).filter((Field field) -> map.containsKey(field.getName())).forEach(var -> {
                    //获取属性的修饰符
                    int modifiers = var.getModifiers();
                    if(Modifier.isStatic(modifiers) || Modifier.isFinal(modifiers)){
                        //在lambada中结束本次循环是用return,它不支持continue和break
                        return;
                    }
                    //设置权限
                    var.setAccessible(true);
                    try {
                        var.set(target,map.get(var.getName()));
                    } catch (IllegalAccessException e) {
                        //属性类型不对,非法操作,跳过本次循环,直接进入下一次循环
                        throw new RuntimeException(e);
                    }
                });
            }
            return target;
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }


}
