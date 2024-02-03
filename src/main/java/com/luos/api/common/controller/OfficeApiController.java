package com.luos.api.common.controller;

import com.luos.common.office.PdfUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

/**
 * 在线office预览
 **/
//@Api(tags = "在线office预览")
//@Controller
@RequestMapping("api/office")
public class OfficeApiController {

    @GetMapping("previewPdf")
    public void pdf(String url, HttpServletResponse response) throws Exception {
        if (StringUtils.isBlank(url)) {
            return;
        }
        File file = null;
        // 文件后缀
        String suffix = url.substring(url.lastIndexOf(".") + 1);
        // 如果是图片
        if (isPicture(suffix)) {
            HttpURLConnection httpUrl = (HttpURLConnection) new URL(url).openConnection();
            httpUrl.connect();
            file = PdfUtils.inputStreamToFile(httpUrl.getInputStream(), UUID.randomUUID().toString() + suffix);
            response.setContentType("image/jpeg");
        }
        // 如果是PDF
        else if ("pdf".equals(suffix)) {
            HttpURLConnection httpUrl = (HttpURLConnection) new URL(url).openConnection();
            httpUrl.connect();
            file = PdfUtils.inputStreamToFile(httpUrl.getInputStream(), UUID.randomUUID().toString() + ".pdf");
            response.setContentType("application/pdf");
        }
        // 如果是文本
        else if ("txt".equals(suffix)) {
            HttpURLConnection httpUrl = (HttpURLConnection) new URL(url).openConnection();
            httpUrl.connect();
            file = PdfUtils.inputStreamToFile(httpUrl.getInputStream(), UUID.randomUUID().toString() + ".txt");
            response.setContentType("text/html");
        }
        // 如果是doc
        else if ("doc".equals(suffix) || "docx".equals(suffix)) {
            file = new File(PdfUtils.word2pdf(url, System.getProperty("user.dir") + UUID.randomUUID().toString() + ".pdf"));
            response.setContentType("application/pdf");
        }
        // 如果是excel
        else if ("xls".equals(suffix) || "xlsx".equals(suffix)) {
            file = new File(PdfUtils.excel2pdf(url, System.getProperty("user.dir") + UUID.randomUUID().toString() + ".pdf"));
            response.setContentType("application/pdf");
        }
        // 如果是ppt
        else if ("ppt".equals(suffix) || "pptx".equals(suffix)) {
            file = new File(PdfUtils.ppt2pdf(url, System.getProperty("user.dir") + UUID.randomUUID().toString() + ".pdf"));
            response.setContentType("application/pdf");
        }
        // 如果文件为空
        if (null == file) {
            return;
        }
        OutputStream outputStream = null;
        InputStream inputStream = null;
        try {
            response.setCharacterEncoding("UTF-8");
            inputStream = new FileInputStream(file);
            outputStream = response.getOutputStream();
            byte buff[] = new byte[1024];
            int length = 0;
            while ((length = inputStream.read(buff)) > 0) {
                outputStream.write(buff, 0, length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != inputStream) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != outputStream) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 判断文件是否为图片
     *
     * @fileSuffix 文件后缀
     */
    public boolean isPicture(String fileSuffix) {
        if (StringUtils.isBlank(fileSuffix)) {
            return false;
        }
        String[] arr = {"bmp", "dib", "gif", "jfif", "jpe", "jpeg", "jpg", "png", "tif", "tiff", "ico"};
        for (String item : arr) {
            if (item.equals(fileSuffix)) {
                return true;
            }
        }
        return false;
    }
}
