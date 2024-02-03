package com.luos.common.excel;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 读取excel工具类
 **/
@Slf4j
@Component
public class ReadExcelUtils {

    /**
     * 读取excel文件
     */
    public List<Object> readExcelData(String path) {
        if (StringUtils.isBlank(path)) {
            return null;
        }
        try {
            InputStream inputStream = new FileInputStream(path);
            // 读取文件
            ExcelReader excelReader = ExcelUtil.getReader(inputStream);
            /**
             * startRowIndex，1 表示从第二行开始读
             */
            List<List<Object>> readList = excelReader.read(1, excelReader.getRowCount());
            List<Object> resList = new ArrayList<>();
            JSONObject object = null;
            // 每一行的数据
            for (List<Object> objects : readList) {
                if (!isInteger(String.valueOf(objects.get(0)))) {
                    break;
                }
                object = new JSONObject();
                object.put("name", String.valueOf(objects.get(1)).trim());
                object.put("age", String.valueOf(objects.get(2)).trim());
                resList.add(object);
            }
            return resList;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("excel 解析异常");
        }
        return null;
    }

    /**
     * 判断是否为数字
     */
    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }
}
