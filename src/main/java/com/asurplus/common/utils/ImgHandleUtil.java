package com.asurplus.common.utils;

import com.asurplus.common.image.ImageManagerUtils;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * 
 * 根据图片源文压缩生成一个文件名_small.jpg的封面小图
 *
 * @author：wanglin
 *
 * @date：Mar 28, 2017
 */
@Slf4j
public class ImgHandleUtil {

    
    //此处的宽和高要与前台的显示尺寸保持等比例
    final public static int defaultWidth = 260;
    final public static int defaultHeight = 320;


	
	/**
	 * 压缩至指定图片尺寸（例如：横130高120），保持图片不变形，多余部分裁剪掉(这个是引的网友的代码) 
	 * 更多操作参考：http://blog.csdn.net/chenleixing/article/details/44685817
	 */
	public static String pictureHandle(String filepath, int w, int h, boolean srcKeep){
		return pictureHandle(filepath, null, w, h, srcKeep); 
	}
	
	public static String pictureHandle(String filepath, String outName, int w, int h, boolean srcKeep){
		File srcfile = new File(filepath);
		if(!srcfile.exists()){
            log.warn("文件：{} 不存在！",filepath);
			return null;
		}
		
		
		String srcName = srcfile.getName();
		String extName = srcName.substring(srcName.lastIndexOf(".") + 1);
		
		String destName = srcName;
		if(srcKeep){
			destName = srcName.substring(0, srcName.lastIndexOf("."));
			if(CommonUtil.isNotNull(outName)){
				destName = outName;
			}else{
				destName = destName + "_thumb." + extName;
			}
		}
		
		File tofile = new File(srcfile.getParent(), destName);
		try {
			BufferedImage image = ImageIO.read(srcfile);
			Thumbnails.Builder<BufferedImage> builder = null;
			
			int imageWidth = image.getWidth();  
			int imageHeitht = image.getHeight();  
			if(imageWidth < w && imageHeitht < h){
				return srcfile.getPath();
				
			}
			
			if ((float)w / h != (float)imageWidth / imageHeitht) {  
				if (imageWidth > imageHeitht) {  
					image = Thumbnails.of(srcfile).height(h).asBufferedImage();  
				} else {  
					image = Thumbnails.of(srcfile).width(w).asBufferedImage();  
				}  
				builder = Thumbnails.of(image).sourceRegion(Positions.CENTER, w, h).size(w, h);  
			} else {  
				builder = Thumbnails.of(image).size(w, h);  
			}  
			builder.outputFormat(extName).toFile(tofile); 
			return tofile.getPath();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}  
	}
	
    
    /**
     * 压缩至指定图片尺寸（例如：横130高120），保持图片不变形，多余部分裁剪掉(这个是引的网友的代码) 
     * 更多操作参考：http://blog.csdn.net/chenleixing/article/details/44685817
     */
    public static String pictureHandle(File srcfile, boolean srcKeep){
        return pictureHandle(srcfile, defaultWidth, defaultHeight, srcKeep); 
    }
    
    
    public static String pictureHandle(File srcfile, int w, int h, boolean srcKeep){
        if(!srcfile.exists()){
            log.warn("文件：{} 不存在！",srcfile.getPath());
            return null;
        }
        
        
        String srcName = srcfile.getName();
        String extName = srcName.substring(srcName.lastIndexOf(".") + 1);
        
        String destName = srcName;
        if(srcKeep){
            String befname = srcName.substring(0, srcName.lastIndexOf("."));
            File copyfile = new File(srcfile.getParent(), befname+"_src." + extName);
            try {
                FileUtils.copyFile(srcfile, copyfile);
            } catch (IOException e) {
                srcfile.renameTo(copyfile);
                e.printStackTrace();
            }
//          destName = srcName.substring(0, srcName.lastIndexOf("."));
//          destName = destName + "_small." + extName;
        }
        
        return pictureHandle(srcfile, w, h, destName);  
    }
    
    public static String pictureHandle(File srcfile, String destName, int w, int h, boolean srcKeep){
        if(!srcfile.exists()){
            log.warn("文件：{} 不存在！",srcfile.getPath());
            return null;
        }
        
        
        String srcName = srcfile.getName();
        String extName = srcName.substring(srcName.lastIndexOf(".") + 1);
        
        if(srcKeep){
            String befname = srcName.substring(0, srcName.lastIndexOf("."));
            File copyfile = new File(srcfile.getParent(), befname+"_src." + extName);
            try {
                FileUtils.copyFile(srcfile, copyfile);
            } catch (IOException e) {
                srcfile.renameTo(copyfile);
                e.printStackTrace();
            }
        }
        
        return pictureHandle(srcfile, w, h, destName);  
    }
    
    public static String pictureHandle_tosmall(File srcfile, int w, int h){
        if(!srcfile.exists()){
            log.warn("文件：{} 不存在！",srcfile.getPath());
            return srcfile.getAbsolutePath();
        }
        
        
        String srcName = srcfile.getName();
        String extName = srcName.substring(srcName.lastIndexOf(".") + 1);
        
        String destName = srcName.substring(0, srcName.lastIndexOf("."));
        destName = destName + "_small." + extName;
        
        return pictureHandle(srcfile, w, h, destName);
    }
    
    public static String pictureHandle(File srcfile, int w, int h, String destName){
        File tofile = new File(srcfile.getParent(), destName);
        try {
            BufferedImage image = ImageIO.read(srcfile);
            Thumbnails.Builder<BufferedImage> builder = null;
            
            int imageWidth = image.getWidth();  
            int imageHeitht = image.getHeight();  
            if(imageWidth < w && imageHeitht < h){
                return srcfile.getPath();
                
            }
            
            if ((float)w / h != (float)imageWidth / imageHeitht) {  
                if (imageWidth > imageHeitht) {  
                    image = Thumbnails.of(srcfile).height(h).asBufferedImage();  
                } else {  
                    image = Thumbnails.of(srcfile).width(w).asBufferedImage();  
                }  
                builder = Thumbnails.of(image).sourceRegion(Positions.CENTER, w, h).size(w, h);  
            } else {  
                builder = Thumbnails.of(image).size(w, h);  
            }  
            String extName = destName.substring(destName.lastIndexOf(".") + 1);
            builder.outputFormat(extName).toFile(tofile); 
            return tofile.getPath();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } 
    }

    /**
     * 生产缩率的封面图片
     * @param path
     */
    public static void coverPicture(String path){
        File f = new File(path);
        ImgHandleUtil.pictureHandleCover(f, 180, 250);
        log.info( "缩率封面生成成功:{}",f.getName());
    }

    /**
     * 生产古籍缩率的封面图片
     * @param path
     */
    public static void coverAncientPicture(String path){
        File f = new File(path);
        ImgHandleUtil.pictureHandleCover(f, 1050, 240);
        log.info( "缩率封面生成成功:{}",f.getName());
    }


    public static String pictureHandleCover(File srcfile, int w, int h){
        if(!srcfile.exists()){
            log.warn("文件不存在： {}",srcfile.getPath());
            return srcfile.getAbsolutePath();
        }

        String destName = "cover.jpg";

        return pictureHandle(srcfile, w, h, destName);
    }

    public static String pictureHandleThumb(File srcfile){
        return pictureHandleThumb(srcfile, 130, 200);
    }

    public static String pictureHandleThumb(File srcfile, int w, int h){
        if(!srcfile.exists()){
            log.warn("文件不存在： {}",srcfile.getPath());
            return srcfile.getAbsolutePath();
        }

        String destName = FilenameUtils.getBaseName(srcfile.getName()) + "_thumb." + FilenameUtils.getExtension(srcfile.getName());
        return pictureHandle(srcfile, w, h, destName);
    }

    public static  void scalePicture(String path){
        if(StringUtils.isBlank(path)) {
            return;
        }
        List<File> fileList = ImageManagerUtils.findImageFileList(path);
        if(fileList == null) {
            return;
        }

        File thumb = null;
        String ext = null;
        String filename = null;
        for(File f : fileList){
            ext = FilenameUtils.getExtension(f.getName());
            filename = FilenameUtils.getBaseName(f.getName());
            thumb = new File(f.getParentFile(), filename+"_thumb."+ext);
            if (thumb.exists()) {
                continue;
            }
            ImgHandleUtil.pictureHandleThumb(f, 130, 200);
        }
    }
	
}
