package com.luos.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MapUtils {
    /**
     * 返回Map相同的 value
     * @param map
     * @return
     */
    public static List<String> mapValueNum(Map<String, Object> map) {
          Map<String,Integer> res=new HashMap<>();
          List<String> returnMap=new ArrayList<>();
        for (Map.Entry entry : map.entrySet()) {
            if(null !=entry.getValue() && StringUtils.isNotBlank(String.valueOf(entry.getValue())) && !isNumeric(String.valueOf(entry.getValue()))){
                if (res.containsKey(entry.getValue())) {
                    int intNum=Integer.parseInt(String.valueOf(res.get(entry.getValue())));
                    res.put((String)entry.getValue(), intNum+1);
                } else {
                    res.put((String)entry.getValue(), 1);
                }
            }
        }
        for (Object tmp:res.keySet()) {
            if(res.get(tmp)>1){
                returnMap.add((String)tmp);
            }
        }
        return returnMap;
    }

    /**
     *
     * @description: 实体类转Map
     * @author: Jeff
     * @date: 2019年10月29日
     * @param object
     * @return
     */
    public static Map<String, Object> entityToMap(Object object) {
        Map<String, Object> map = new HashMap<>();
        for (Field field : object.getClass().getDeclaredFields()) {
            try {
                boolean flag = field.isAccessible();
                field.setAccessible(true);
                Object o = field.get(object);
                map.put(field.getName(), o);
                field.setAccessible(flag);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return map;
    }


    /**
     * 利用正则表达式判断字符串是否是数字
     * @param str
     * @return
     */
    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");

        Matcher isNum = pattern.matcher(str);

        if( !isNum.matches() ){
            return false;
        }
        return true;

    }

    public static void main(String[] args) {
        System.out.println(isNumeric("123123s"));
    }
}
