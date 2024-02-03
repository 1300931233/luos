package com.luos.common.utils;

import com.luos.common.redis.RedisConst;
import com.luos.common.redis.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @Description 压缩工具
 **/
@Slf4j
public class ZipUtils {
    public static void main(String[] args) {
        File file = new File("D:\\MP4test\\test\\a94b93ab-3b34-49ad-9eef-1e6353946763");
        System.out.println(ZipUtils.createZip(file));

    }

    private static RedisUtil redisUtil;

    @Autowired
    private void setRedisUtil(RedisUtil redisUtil){
        ZipUtils.redisUtil = redisUtil;
    }

    public static final int BUFFER_SIZE = 1024;

    /**
     * 创建ZIP文件
     * @param sourceFile 文件
     */
    public static String createZip(File sourceFile) {
        FileOutputStream fos = null;
        ZipOutputStream zos = null;
        try {
           String  zipPath = sourceFile.getPath()+".zip";//生成的zip文件存在路径（包括文件名）
            //判断文件是否是文件夹
            if(!sourceFile.isDirectory()){
                zipPath = sourceFile.getParent()+"/"+sourceFile.getName().substring(0,sourceFile.getName().lastIndexOf("."))+".zip";
            }
            fos = new FileOutputStream(zipPath);
            zos = new ZipOutputStream(fos);
            log.info("------------ 文件压缩中！------------");
            writeZip(sourceFile, "", zos);
            log.info("------------ 文件压缩完成！------------");
            return  zipPath;
        } catch (FileNotFoundException e) {
            log.error("创建ZIP文件失败",e);
        } finally {
            try {
                if (zos != null) {
                    zos.close();
                }
            } catch (IOException e) {
                log.error("创建ZIP文件失败",e);
            }
        }
        return "";
    }

    private static void writeZip(File file, String parentPath, ZipOutputStream zos) {

        if(file.exists()){
            if(file.isDirectory()){//处理文件夹
                parentPath+=file.getName()+File.separator;
                File [] files=file.listFiles();
                if(files.length != 0)
                {
                    for(File f:files){
                        writeZip(f, parentPath, zos);
                    }
                }
                else
                {       //空目录则创建当前目录
                    try {
                        zos.putNextEntry(new ZipEntry(parentPath));
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }else{
                FileInputStream fis=null;
                try {
                    fis=new FileInputStream(file);
                    ZipEntry ze = new ZipEntry(parentPath + file.getName());
                    zos.putNextEntry(ze);
                    byte [] content=new byte[1024];
                    int len;
                    while((len=fis.read(content))!=-1){
                        zos.write(content,0,len);
                        zos.flush();
                    }
                } catch (FileNotFoundException e) {
                    log.error("创建ZIP文件失败",e);
                } catch (IOException e) {
                    log.error("创建ZIP文件失败",e);
                }finally{
                    try {
                        if(fis!=null){
                            fis.close();
                        }
                    }catch(IOException e){
                        log.error("创建ZIP文件失败",e);
                    }
                }
            }
        }
    }


    /**
     * 经测试，通过java本身的纯jar包解压，rar版本高的无法解压，极不稳定
     * 采用系统自带的winrar软件更稳定，且可同时支持(rar、zip)
     *
     * @param packPath 压缩包路径
     * @param isSrcDel 解压后是否删除原始包，true：删除，false：不删除
     * @return
     */
    public static String unPackFile(String packPath, boolean isSrcDel) {
        File packFile = new File(packPath);
        if(packFile.exists() == false){
            System.out.println("压缩包文件不存在：" + packPath);
            return "";
        }


        String name = packFile.getName();
        if(name.indexOf(" ") != -1){
            // 由于文件名中包含空格会影响解压软件运行，此次将文件名称中的空格全部剔除
            name = name.replaceAll(" ", "").replaceAll(" ", "");
            File destFile = new File(packFile.getParent(), name);
            packFile.renameTo(destFile);
            packFile = destFile;
        }

        // 获取没有后缀的文件名称
        name = FilenameUtils.getBaseName(name);

        packPath = packFile.getAbsolutePath();
        // 使用zip的文件夹+name做为输出目录
        String destDir = packFile.getParent()+"/"+name;
        destDir = destDir.replace("\\", "/");
        File destDirFile = new File(destDir);
        if(destDirFile.exists() == false){
            destDirFile.mkdirs();
        }

        try {

            // 系统安装winrar的路径
//            String cmd = "D:\\Program Files\\WinRAR\\WinRAR.exe";
            String cmd =(String) redisUtil.get(RedisConst.Key.WINRAR_INSTALL_PATH);
            cmd = cmd.replace("\\", "/");
            System.out.println("cmd"+cmd);
            String unrarCmd = cmd + " x -r -p- -o+ " + packPath + " " + destDir;

            Runtime rt = Runtime.getRuntime();
            Process pre = rt.exec(unrarCmd);
            InputStreamReader isr = new InputStreamReader(pre.getInputStream());
            BufferedReader bf = new BufferedReader(isr);
            String line = null;
            while ((line = bf.readLine()) != null) {
                line = line.trim();
                if ("".equals(line)) {
                    continue;
                }
                System.out.println(line);
            }

            bf.close();

            //删除原始压缩包
            if(isSrcDel){
                FileUtils.forceDelete(packFile);

                // 获取所有文件
                File[] files = destDirFile.listFiles(new FileFilter() {
                    @Override
                    public boolean accept(File pathname) {
                        return pathname.isFile();
                    }
                });

                String filename;
                File file = null;
                for(int i=0; i<files.length; i++){
                    file = files[i];
                    filename = file.getName();
                    // 文件名不包含空格直接跳过
                    if(filename.indexOf(" ") == -1){
                        continue;
                    }
                    // 去掉文件名称中的空格
                    filename = filename.replaceAll(" ", "").replaceAll(" ", "");
                    destDirFile.renameTo(new File(file.getParent(), filename));
                }
            }
            return destDir;
        } catch (Exception e) {
            System.out.println("解压发生异常：" + e.getMessage());
            return "";
        }
    }

    public static void unPackFile1(String packPath, boolean isSrcDel) {
        File packFile = new File(packPath);
        if(packFile.exists() == false){
            System.out.println("压缩包文件不存在：" + packPath);
            return ;
        }

        // 输出目录
        String destDir = packFile.getParent();
        String cmd = null;
        try {

            // 系统安装winrar的路径
            cmd =(String) redisUtil.get(RedisConst.Key.WINRAR_INSTALL_PATH);
            cmd = cmd.replace("\\", "/");
            System.out.println("cmd"+cmd);
            String unrarCmd = cmd + " x -r -p- -o+ " + packPath + " " + destDir;

            Runtime rt = Runtime.getRuntime();
            Process pre = rt.exec(unrarCmd);
            InputStreamReader isr = new InputStreamReader(pre.getInputStream());
            BufferedReader bf = new BufferedReader(isr);
            String line = null;
            while ((line = bf.readLine()) != null) {
                line = line.trim();
                if ("".equals(line)) {
                    continue;
                }
                System.out.println(line);
            }

            bf.close();

            //3、把解压后的文件复制到根目录下，并删除
            String path = destDir+File.separator+ FilenameUtils.getBaseName(packFile.getName());;
            File file = new File(path);
            if(file.exists()){
                FileUtils.copyDirectory(file,packFile.getParentFile());//把解压的文件数据放在根目录下
                DeleteFileUtil.deleteFile(file); //删除解压的文件
            }

            //删除原始压缩包
            if(isSrcDel){
                packFile.delete();
            }
        } catch (Exception e) {
            System.out.println("解压发生异常：" + e.getMessage()+cmd);
        }
    }



    public static String unZip(String zipPath) throws IOException{
        File file = new File(zipPath);
        if(!file.exists()){
            return "";
        }
        String dest = unZip(file);
        return dest;
    }


    /**
     * 解压zip，返回输出文件夹
     * @param zipFile  待解压的文件夹
     * @return
     * @throws IOException
     */
    public static String unZip(File zipFile) throws IOException {
        if(zipFile == null || !zipFile.exists() || !zipFile.isFile()) {
            return "";
        }
        // 获取没有后缀的文件名称
        String name = FilenameUtils.getBaseName(zipFile.getName());

        // 使用zip的文件夹+name做为输出目录
        String destDir = zipFile.getParent()+"/"+name;
        unZip(zipFile, destDir, "UTF-8");
        return destDir;
    }

    /**
     * 解压zip文件
     * @param zipFile zip压缩文件路径
     * @param destDir	zip解压保存的路径文件夹
     * @return	zip解压后的文件名list
     * @throws Exception
     * @author Liming
     * @date Jun 29, 2020
     */
    public static List<String> unZip(File zipFile, String destDir) throws IOException {
        return unZip(zipFile, destDir, "UTF-8");
    }

    /**
     * 解压 zip 文件
     * @param zipFile	zip压缩文件
     * @param destDir	zip压缩文件解压后保存的目录
     * @param encoding	zip文件的编码
     * @return	zip压缩文件里的文件名的list
     * @throws IOException
     * @throws Exception
     */
    public static List<String> unZip(File zipFile, String destDir, String encoding) throws IOException {
        // 如果 destDir 为 null, 空字符串, 或者全是空格, 则解压到压缩文件所在目录
        if (destDir == null || destDir.length() == 0) {
            destDir = zipFile.getParent();
        }

        destDir = destDir.endsWith(File.separator) ? destDir : destDir + File.separator;
        ZipArchiveInputStream is = null;
        List<String> fileNames = new ArrayList<String>();

        try {
            is = new ZipArchiveInputStream(new BufferedInputStream(new FileInputStream(zipFile), BUFFER_SIZE), encoding);
            ZipArchiveEntry entry = null;

            while ((entry = is.getNextZipEntry()) != null) {
                fileNames.add(entry.getName());
                File file = new File(destDir, entry.getName());

                if (entry.isDirectory()) {
                    FileUtils.forceMkdir(file); // 创建文件夹，如果中间有路径会自动创建
                } else {
                    OutputStream os = null;
                    try {
                        FileUtils.touch(file);
                        os = new FileOutputStream(new File(destDir, entry.getName()));
                        IOUtils.copy(is, os);
                    } finally {
                        IOUtils.closeQuietly(os);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            IOUtils.closeQuietly(is);
        }
        return fileNames;
    }

}