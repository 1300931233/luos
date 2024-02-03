package com.asurplus.common.office;

import com.aspose.cells.Workbook;
import com.aspose.slides.Presentation;
import com.aspose.words.Document;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

/**
 * 文件转PDF
 * <p>
 * Aspose下载地址：https://repository.aspose.com/repo/com/aspose/
 */
public class PdfUtils {

    /**
     * word 转为 pdf 输出
     *
     * @param inPath  word文件
     * @param outPath pdf 输出文件目录
     */
    public static String word2pdf(String inPath, String outPath) {
        // 验证License
        if (!isWordLicense()) {
            return null;
        }
        FileOutputStream os = null;
        try {
            String path = outPath.substring(0, outPath.lastIndexOf(File.separator));
            File file = new File(path);
            // 创建文件夹
            if (!file.exists()) {
                file.mkdirs();
            }
            // 新建一个空白pdf文档
            file = new File(outPath);
            os = new FileOutputStream(file);
            // Address是将要被转化的word文档
            Document doc = new Document(inPath);
            // 全面支持DOC, DOCX, OOXML, RTF HTML, OpenDocument, PDF,
            doc.save(os, com.aspose.words.SaveFormat.PDF);
            os.close();
        } catch (Exception e) {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            e.printStackTrace();
        }
        return outPath;
    }

    /**
     * excel 转为 pdf 输出
     *
     * @param inPath  excel 文件
     * @param outPath pdf 输出文件目录
     */
    public static String excel2pdf(String inPath, String outPath) {
        // 验证License
        if (!isWordLicense()) {
            return null;
        }
        FileOutputStream os = null;
        try {
            String path = outPath.substring(0, outPath.lastIndexOf(File.separator));
            File file = new File(path);
            // 创建文件夹
            if (!file.exists()) {
                file.mkdirs();
            }
            // 新建一个空白pdf文档
            file = new File(outPath);
            os = new FileOutputStream(file);
            // Address是将要被转化的excel表格
            Workbook workbook = new Workbook(new FileInputStream(getFile(inPath)));
            workbook.save(os, com.aspose.cells.SaveFormat.PDF);
            os.close();
        } catch (Exception e) {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            e.printStackTrace();
        }
        return outPath;
    }

    /**
     * ppt 转为 pdf 输出
     *
     * @param inPath  ppt 文件
     * @param outPath pdf 输出文件目录
     */
    public static String ppt2pdf(String inPath, String outPath) {
        // 验证License
        if (!isWordLicense()) {
            return null;
        }
        FileOutputStream os = null;
        try {
            String path = outPath.substring(0, outPath.lastIndexOf(File.separator));
            File file = new File(path);
            // 创建文件夹
            if (!file.exists()) {
                file.mkdirs();
            }
            // 新建一个空白pdf文档
            file = new File(outPath);
            os = new FileOutputStream(file);
            // Address是将要被转化的PPT幻灯片
            Presentation pres = new Presentation(new FileInputStream(getFile(inPath)));
            pres.save(os, com.aspose.slides.SaveFormat.Pdf);
            os.close();
        } catch (Exception e) {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            e.printStackTrace();
        }
        return outPath;
    }

    /**
     * 验证 Aspose.word 组件是否授权
     * 无授权的文件有水印和试用标记
     */
    public static boolean isWordLicense() {
        boolean result = false;
        try {
            // 避免文件遗漏
            String licensexml = "<License>\n" +
                    "<Data>\n" +
                    "<Products>\n" +
                    "<Product>Aspose.Total for Java</Product>\n" +
                    "<Product>Aspose.Words for Java</Product>\n" +
                    "</Products>\n" +
                    "<EditionType>Enterprise</EditionType>\n" +
                    "<SubscriptionExpiry>20991231</SubscriptionExpiry>\n" +
                    "<LicenseExpiry>20991231</LicenseExpiry>\n" +
                    "<SerialNumber>8bfe198c-7f0c-4ef8-8ff0-acc3237bf0d7</SerialNumber>\n" +
                    "</Data>\n" +
                    "<Signature>sNLLKGMUdF0r8O1kKilWAGdgfs2BvJb/2Xp8p5iuDVfZXmhppo+d0Ran1P9TKdjV4ABwAgKXxJ3jcQTqE/2IRfqwnPf8itN8aFZlV3TJPYeD3yWE7IT55Gz6EijUpC7aKeoohTb4w2fpox58wWoF3SNp6sK6jDfiAUGEHYJ9pjU=</Signature>\n" +
                    "</License>";
            InputStream inputStream = new ByteArrayInputStream(licensexml.getBytes());
            com.aspose.words.License license = new com.aspose.words.License();
            license.setLicense(inputStream);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * OutputStream 转 InputStream
     */
    public static ByteArrayInputStream parse(OutputStream out) {
        ByteArrayOutputStream baos = (ByteArrayOutputStream) out;
        ByteArrayInputStream swapStream = new ByteArrayInputStream(baos.toByteArray());
        return swapStream;
    }

    /**
     * InputStream 转 File
     */
    public static File inputStreamToFile(InputStream ins, String name) throws Exception {
        File file = new File(System.getProperty("java.io.tmpdir") + File.separator + name);
        if (file.exists()) {
            return file;
        }
        OutputStream os = new FileOutputStream(file);
        int bytesRead;
        int len = 8192;
        byte[] buffer = new byte[len];
        while ((bytesRead = ins.read(buffer, 0, len)) != -1) {
            os.write(buffer, 0, bytesRead);
        }
        os.close();
        ins.close();
        return file;
    }

    /**
     * 根据网络地址获取 File 对象
     */
    public static File getFile(String url) throws Exception {
        String suffix = url.substring(url.lastIndexOf("."));
        HttpURLConnection httpUrl = (HttpURLConnection) new URL(url).openConnection();
        httpUrl.connect();
        return PdfUtils.inputStreamToFile(httpUrl.getInputStream(), UUID.randomUUID().toString() + suffix);
    }

}