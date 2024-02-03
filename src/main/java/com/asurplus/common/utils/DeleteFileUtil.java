package com.asurplus.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: ljh
 * Date: 2021/5/19
 * Time: 17:06
 * To change this template use File | Settings | File Templates.
 * Description:
 */
@Slf4j
public class DeleteFileUtil {

    public static void deleteFile(File file){
        //判断文件不为null或文件目录存在
        if (file == null || !file.exists()){
            log.warn("文件删除失败,请检查文件路径是否正确");
            return;
        }
        //取得这个目录下的所有子文件对象
        File[] files = file.listFiles();
        //遍历该目录下的文件对象
        for (File f: files){
            //打印文件名
            String name = file.getName();
            System.out.println(name);
            //判断子目录是否存在子目录,如果是文件则删除
            if (f.isDirectory()){
                deleteFile(f);
            }else {
                f.delete();
            }
        }
        //删除空文件夹  for循环已经把上一层节点的目录清空。
        file.delete();
    }
}
