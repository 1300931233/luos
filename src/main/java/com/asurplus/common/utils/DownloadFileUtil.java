package com.asurplus.common.utils;

import org.apache.poi.util.IOUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

/**
 * 文件下载工具类
 */
public class DownloadFileUtil {
    /**
     * 文件 通过浏览器下载的形式
     * @param response 响应
     * @param file 文件
     * @param fileName 下载文件名
     * @param suffix 文件后缀
     * @throws IOException
     */
    public static void downloadFile(HttpServletResponse response, File file, String fileName,String suffix) throws IOException {

        response.setContentType("application/msfile");//msfile可以随便设置,别和当前项目别的下载冲突接口
        response.setCharacterEncoding("UTF-8");
        String encode = URLEncoder.encode(fileName, "UTF-8");//这是文件名,这样处理是解决乱码
        response.addHeader("Content-Disposition", "attachment;filename=\"" + encode +suffix +"\"");
        response.flushBuffer();
        OutputStream outputStream = response.getOutputStream();

        //输出文件
        InputStream inputStream  = new FileInputStream(file);
        IOUtils.copy(inputStream, outputStream);
        //关闭流
        IOUtils.closeQuietly(inputStream);
        IOUtils.closeQuietly(outputStream);

    }

    /**
     * InputStream 下载文件
     * @param response 响应对象
     * @param inputStream 二进制流
     * @param fileName 导出文件名
     * @param suffix 文件后缀
     * @throws IOException
     */
    public static void downloadFileStream(HttpServletResponse response, InputStream inputStream, String fileName,String suffix) throws IOException {

        response.setContentType("application/msfile");//msfile可以随便设置,别和当前项目别的下载冲突接口
        response.setCharacterEncoding("UTF-8");
        String encode = URLEncoder.encode(fileName, "UTF-8");//这是文件名,这样处理是解决乱码
        response.addHeader("Content-Disposition", "attachment;filename=\"" + encode +suffix +"\"");
        response.flushBuffer();
        OutputStream outputStream = response.getOutputStream();

        //输出文件
        IOUtils.copy(inputStream, outputStream);
        //关闭流
        IOUtils.closeQuietly(inputStream);
        IOUtils.closeQuietly(outputStream);

    }
}
