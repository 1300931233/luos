package com.luos.common.excel;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * @version V1.0
 * @Description: Excel导出工具类
 */
public class ExportExcelUtil {

    /**
     * 导出
     *
     * @param list           数据集合
     * @param title          内容标题
     * @param sheetName      excel名称
     * @param pojoClass      实体类
     * @param fileName       导出的文件名
     * @param isCreateHeader 是否创建excel表头
     * @param response       响应
     */
    public static void exportExcel(List<?> list,
                                   String title,
                                   String sheetName,
                                   Class<?> pojoClass,
                                   String fileName,
                                   boolean isCreateHeader,
                                   HttpServletResponse response) {
        ExportParams exportParams = new ExportParams(title, sheetName);
        exportParams.setCreateHeadRows(isCreateHeader);
        defaultExport(list, pojoClass, fileName, response, exportParams);
    }

    /**
     * 导出
     *
     * @param list      数据集合
     * @param title     内容标题
     * @param sheetName excel名称
     * @param pojoClass 实体类
     * @param fileName  导出的文件名
     * @param response  响应
     */
    public static void exportExcel(List<?> list,
                                   String title,
                                   String sheetName,
                                   Class<?> pojoClass,
                                   String fileName,
                                   HttpServletResponse response) {
        defaultExport(list, pojoClass, fileName, response, new ExportParams(title, sheetName));
    }

    /**
     * 导出 无内容标题和excel表名
     *
     * @param list
     * @param pojoClass
     * @param fileName
     * @param response
     */
    public static void exportExcel(List<?> list,
                                   Class<?> pojoClass,
                                   String fileName,
                                   HttpServletResponse response) {
        defaultExport(list, pojoClass, fileName, response, new ExportParams());
    }

    /**
     * 导出 无内容标题和excel表名
     *
     * @param list
     * @param pojoClass
     * @param fileName
     * @param response
     */
    public static void exportExcel(List<?> list,
                                   Class<?> pojoClass,
                                   String fileName,
                                   String title,
                                   HttpServletResponse response) {
        defaultExport(list, pojoClass, fileName, response, new ExportParams(title, null));
    }

    /**
     * 导出
     *
     * @param list     数据集合
     * @param fileName 导出的文件名
     * @param response 响应
     */
    public static void exportExcel(List<Map<String, Object>> list,
                                   String fileName,
                                   HttpServletResponse response) {
        defaultExport(list, fileName, response);
    }

    /**
     * 导出
     *
     * @param list         数据集合
     * @param pojoClass    实体类
     * @param fileName     导出的文件名
     * @param response     响应
     * @param exportParams
     */
    private static void defaultExport(List<?> list,
                                      Class<?> pojoClass,
                                      String fileName,
                                      HttpServletResponse response,
                                      ExportParams exportParams) {
        // 自定义字典查询
        exportParams.setDictHandler(new IExcelDictHandlerImpl());
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, pojoClass, list);
        if (workbook != null) {
            doExport(fileName, response, workbook);
        }
    }

    /**
     * 导出
     *
     * @param list     数据集合
     * @param fileName 导出的文件名
     * @param response 响应
     */
    private static void defaultExport(List<Map<String, Object>> list,
                                      String fileName,
                                      HttpServletResponse response) {
        Workbook workbook = ExcelExportUtil.exportExcel(list, ExcelType.HSSF);
        if (workbook != null) {
            doExport(fileName, response, workbook);
        }
    }

    /**
     * 导出
     *
     * @param fileName 导出的文件名
     * @param response 响应
     * @param workbook 工作表
     */
    private static void doExport(String fileName,
                                 HttpServletResponse response,
                                 Workbook workbook) {
        try {
            response.setCharacterEncoding("UTF-8");
            response.setHeader("content-Type", "application/vnd.ms-excel");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8") + ".xls");
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 导入
     *
     * @param filePath
     * @param titleRows
     * @param headerRows
     * @param pojoClass
     * @param <T>
     * @return
     */
    public static <T> List<T> importExcel(String filePath,
                                          Integer titleRows,
                                          Integer headerRows,
                                          Class<T> pojoClass) {
        if (StringUtils.isBlank(filePath)) {
            return null;
        }
        ImportParams params = new ImportParams();
        params.setTitleRows(titleRows);
        params.setHeadRows(headerRows);
        List<T> list = null;
        try {
            list = ExcelImportUtil.importExcel(new File(filePath), pojoClass, params);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 导入
     *
     * @param file
     * @param titleRows
     * @param headerRows
     * @param pojoClass
     * @param <T>
     * @return
     */
    public static <T> List<T> importExcel(MultipartFile file,
                                          Integer titleRows,
                                          Integer headerRows,
                                          Class<T> pojoClass) {
        if (file == null) {
            return null;
        }
        ImportParams params = new ImportParams();
        params.setTitleRows(titleRows);
        params.setHeadRows(headerRows);
        List<T> list = null;
        try {
            list = ExcelImportUtil.importExcel(file.getInputStream(), pojoClass, params);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 用于将Excel表格中列号字母转成列索引，从1对应A开始
     *
     * @param column
     *            列号
     * @return 列索引
     */
    public static int columnToIndex(String column) {
        if(null == column){
            return 0;
        }
        if (!column.matches("[A-Z]+")) {
            try {
                throw new Exception("Invalid parameter");
            } catch (Exception e) {
                //e.printStackTrace();
                //不满足直接返回0
                return 0;
            }
        }
        int index = 0;
        char[] chars = column.toUpperCase().toCharArray();
        for (int i = 0; i < chars.length; i++) {
            index += ((int) chars[i] - (int) 'A' + 1)
                    * (int) Math.pow(26, chars.length - i - 1);
        }
        return index;
    }

    /**
     * Excel 导出 通过浏览器下载的形式
     * @param response 响应
     * @param workbook 工作簿
     * @param fileName 文件名
     * @throws IOException
     */
    public static void export(HttpServletResponse response, Workbook workbook, String fileName) throws IOException {
        response.setHeader("Content-Disposition",
                "attachment;filename=" + new String(fileName.getBytes("UTF-8"), "iso8859-1"));
        response.setContentType("application/vnd.ms-excel;charset=UTF-8");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        BufferedOutputStream bufferedOutPut = new BufferedOutputStream(response.getOutputStream());
        workbook.write(bufferedOutPut);
        bufferedOutPut.flush();
        bufferedOutPut.close();
    }

}