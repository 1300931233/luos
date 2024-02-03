package com.luos.common.utils;

import com.luos.common.image.ImageManagerUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.FileImageInputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


@Slf4j
public class ImageUtil {
	
    private static final ThreadPoolExecutor singleThreadExecutor =  new ThreadPoolExecutor(16, 16, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
	
	public final static Set<String> IMAGE_SUFFIX_SET = new HashSet<String>() {
		private static final long serialVersionUID = 1L;

		{
			add("png");
			add("jpg");
			add("jpeg");
			add("tif");
			add("tiff");
		}
	};   

	/**
	 * 判断文件是否为图片
	 * @fileSuffix 文件后缀
	 */
	public static boolean isPicture(File file) {
		return isPicture(FilenameUtils.getExtension(file.getName()));
	}
	
    /**
     * 判断文件是否为图片
     * @fileSuffix 文件后缀
     */
    public static boolean isPicture(String ext) {
        if (StringUtils.isBlank(ext)) {
            return false;
        }
        return IMAGE_SUFFIX_SET.contains(ext.toLowerCase().trim());
    }

    /**
     * 判断图片是否是缩率图和封面
     * @param baseName
     * @return
     */
    public static boolean isThumbAndCover(String baseName){
        if (StringUtils.isBlank(baseName)) {
            return false;
        }
        if(baseName.endsWith("_thumb")||baseName.equals("cover")||baseName.endsWith("_cut")){
            return true;
        }
        return false;
    }
    
    /**
     * 创建封面和缩略图
     */
    public static void createThumbAndCover(String imgPath){
    	File imgDir = new File(imgPath);
    	if (imgDir == null || !imgDir.exists() || !imgDir.isDirectory()) {
    		return;
    	}
    	File cover = new File(imgDir, "cover.jpg");
    	if(cover == null || !cover.exists() || !cover.isFile()) {
    		List<File> fileList = ImageManagerUtils.findImageFileList(imgDir);
    		if(fileList.isEmpty()) {
    			return;
    		}
			ImgHandleUtil.coverPicture(fileList.get(0).getAbsolutePath());
    	}
    	
    	singleThreadExecutor.execute(new Runnable() {
            @Override
            public void run() {
            	ImgHandleUtil.scalePicture(imgDir.getAbsolutePath());
            }
        });
    }
    
	/**
	 * 	将tiff图片转化为jpg，生成新的文件
	 * @param oldPath	原图片的全路径
	 * @param newPath	生成新的图片的全路径
	 */
	public static void tiffToJpg(String oldPath,String newPath) {
		try {
			//BufferedImage bufferegImage= ImageIO.read(new File(oldPath));
			ImageReader reader = ImageIO.getImageReadersBySuffix("tif").next();
			FileImageInputStream stream = new FileImageInputStream(new File(oldPath));
			reader.setInput(stream);
			BufferedImage image = reader.read(0);
			ImageIO.write(image,"jpg",new File(newPath));//可以是png等其它图片格式

		}catch(IOException e) {
			e.printStackTrace();
		}
	}

}
