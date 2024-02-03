package com.luos.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

/**
 * 文件上传
 */
@Component
public class UploadFileUtil {

    @Value("${server.host}")
    public String host;

    @Value("${server.port}")
    public String port;

    @Value("${server.servlet.context-path}")
    public String contextPath;

    /**
     * 上传文件
     *
     * @param multipartFile 文件对象
     * @param dir           上传目录
     * @return
     */
    public ResponseResult uploadFile(MultipartFile multipartFile, String dir) {
        try {
            if (multipartFile.isEmpty()) {
                return ResponseResult.error("请选择文件");
            }
            // 获取文件的名称
            String originalFilename = multipartFile.getOriginalFilename();
            // 文件后缀 例如：.png
            String fileSuffix = originalFilename.substring(originalFilename.lastIndexOf("."));
            // uuid 生成文件名
            String uuid = String.valueOf(UUID.randomUUID());
            // 根路径
            String basePath = ResourceUtils.getURL("classpath:").getPath() + "static/upload/" + (StringUtils.isNotBlank(dir) ? (dir + "/") : "");
            // 新的文件名
            String fileName = uuid + fileSuffix;
            // 创建新的文件
            File fileExist = new File(basePath);
            if (!fileExist.exists()) {
                fileExist.mkdirs();
            }
            // 获取文件对象
            File file = new File(basePath, fileName);
            // 完成文件的上传
            multipartFile.transferTo(file);
            // 返回绝对路径
            return ResponseResult.success("上传成功", "http://" + host + ":" + port + contextPath + "/upload/" + (StringUtils.isNotBlank(dir) ? (dir + "/") : "") + fileName);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseResult.error("上传失败");
    }
}
