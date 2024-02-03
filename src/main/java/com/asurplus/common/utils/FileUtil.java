package com.asurplus.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Random;

/**
 * 文件操作工具类
 */
@Component
public class FileUtil {

    @Value("${sysconfig.file-root-dir}")
    public String uploadFilePath;

    @Value("${sysconfig.winrar-install-dir}")
    public String winrar;

    /**
     * @Description: 文件创建
     * @Author: 韩文沅
     * @Date: 2022/12/3 15:56
     * @param multipartFile:
     * @param file:
     * @return: void
     **/
    private void fileUpload(MultipartFile multipartFile,File file){
        if (!file.exists()) {
            file.mkdirs();
        }
        // 文件上传
        try {
            multipartFile.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 文件 通过浏览器下载的形式
     * @param response 响应
     * @param file 文件
     * @param fileName 下载文件名
     * @param suffix 文件后缀
     * @throws IOException
     */
    public void downloadFile(HttpServletResponse response, File file, String fileName, String suffix) throws IOException {

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

    /**
     * @Description: 根据文件路径删除文件
     * @Author: 韩文沅
     * @Date: 2022/11/8 14:21
     * @param file: 文件路径
     * @return: void
     **/
    public Boolean deleteFile(File file){

        try {
            if(file != null && !file.isFile()) {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        file.delete();
        return true;
    }

    /**
     * @Description: 删除文件夹以及文件夹包含的所有数据
     * @Author: 韩文沅
     * @Date: 2022/11/8 13:36
     * @param file:
     * @return: java.lang.Boolean
     **/
    public Boolean deleteDirectory(File file) {
        try {
            //判断文件不为null或文件目录存在
            if (file != null && !file.isDirectory()) {
                return false;
            }
            //获取目录下子文件
            File[] files = file.listFiles();
            //遍历该目录下的文件对象
            for (File f : files) {
                //判断子目录是否存在子目录,如果是文件则删除
                if (f.isDirectory()) {
                    //递归删除目录下的文件
                    deleteFile(f);
                } else {
                    //文件删除
                    f.delete();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //文件夹删除
        file.delete();
        return true;
    }

    /**
     * @Description: 创建文件夹
     * @Author: 韩文沅
     * @Date: 2022/11/8 15:02

     * @return: java.lang.String
     **/
    public String createFolder() {
        int y, m, d;
        Calendar cal = Calendar.getInstance();
        y = cal.get(Calendar.YEAR);
        m = (cal.get(Calendar.MONTH) + 1);
        d = cal.get(Calendar.DAY_OF_MONTH);

        // 月份 天数不足两位补0
        String m_str = String.format("%02d", m);
        String d_str = String.format("%02d", d);

        //加上两位随机数
        Random random = new Random();
        int end2 = random.nextInt(99);

        String folder = "";
        if(uploadFilePath.endsWith("/")){
            folder = "resource/"+ y + "/" + y + m_str + d_str;
        }else {
            folder = "/resource/"+y + "/" + y + m_str + d_str;
        }
        return folder;
    }

    /**
     * 压缩文件归一化处理
     * @param dir   解压后的文件路径
     */
    private void compressedFileNormalized(File dir){
        if(dir == null){
            return;
        }
        // 读取所有子文件
        File[] files = dir.listFiles();
        if(files == null || files.length < 1){
            return;
        }

        // 如果子文件数量大于1个，说明压缩包内直接是文件，未嵌套文件夹压缩。直接跳过
        if(files.length > 1){
            return;
        }

        // 读取嵌套文件
        File childDir = files[0];
        if (childDir.isDirectory() == false) {
            return;
        }

        // 将所有文件迁移至上一层级
        Arrays.stream(childDir.listFiles()).parallel().forEach(file -> {
            // 截取相对路径
            String filename = childDir.toPath().relativize(file.toPath()).toString().replaceAll("..\\\\", "");
            // 移动文件位置
            file.renameTo(new File(dir, filename));
        });
        // PDF源文修改为与文件夹同名
        pdfnameNormalized(dir, childDir.getName());
        // 删除文件夹
        childDir.delete();
    }

    /**
     * pdf文件重命名
     * @param dir           文件夹路径
     * @param sourcename    不带扩展名的pdf名称，例如: xxx文件
     */
    private void pdfnameNormalized(File dir, String sourcename){
        if(dir == null || StringUtils.isBlank(sourcename)){
            return;
        }
        File pdfFile = new File(dir, sourcename+".pdf");
        if(pdfFile == null || pdfFile.isDirectory() || pdfFile.exists() == false){
            return;
        }
        pdfFile.renameTo(new File(pdfFile.getParentFile(), dir.getName()+".pdf"));
    }

    /**
     * 获取某个目录下所有的文件,不进行递归
     * @return
     */
    public static File getAllFilesWithNoDg(){
        return null;
    }

    /**
     * 获取某个目录下所有的文件，进行递归
     */
    public static File getAllFilesWithDg(){
        return null;
    }
}
