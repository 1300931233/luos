package com.asurplus.common.image;

import com.asurplus.common.utils.CommonUtil;
import com.asurplus.common.utils.ImageUtil;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileFilter;
import java.util.*;

/**
 * 资源图片管理工具类
 * @author 	马义雯
 * @date	2021/09/29
 * @version 1.0
 *
 */
public class ImageManagerUtils {
	
	/**
	 * 获取指定目录下的图片数量(不包括封面与缩略图)
	 * @param dir	图片目录
	 * @return
	 */
	public static int getImageSize(String dir){
		return getImageSize(new File(dir));
	}
	
	/**
	 * 获取指定目录下的图片数量(不包括封面与缩略图)
	 * @param dir	图片目录
	 * @return
	 */
	public static int getImageSize(File dir){
		return findImageFileList(dir).size();
	}
	
	/**
	 * 读取目录下的图片
	 * @param dir	图片目录
	 * @return
	 */
	public static List<ImageInfo<File>> findImageInfoFileList(String dir){
		return findImageInfoFileList(new File(dir));
	}
	
	/**
	 * 读取目录下的图片
	 * @param dir	图片目录
	 * @return
	 */
	public static List<ImageInfo<File>> findImageInfoFileList(File dir){
		List<File> fileList = findImageFileList(dir);
		if(fileList.isEmpty()) {
			return new ArrayList<ImageInfo<File>>(0);
		}
		
		File thumbFile = null;
		String filename = null, ext = null;
		List<ImageInfo<File>> imageInfoList = new ArrayList<ImageInfo<File>>(fileList.size());
		for(File file:fileList) {
			ext = FilenameUtils.getExtension(file.getName());
			filename = FilenameUtils.getBaseName(file.getName());
			
			// 缩略图
			thumbFile = new File(file.getParentFile(), filename+"_thumb"+ext);
			imageInfoList.add(new ImageInfo<File>(file, thumbFile.exists() == false ? null:thumbFile, file.getName()));
		}
		return imageInfoList;
	}
	
	/**
	 * 读取目录下的图片
	 * @param dir	图片目录
	 * @return
	 */
	public static List<ImageInfo<String>> findImageInfoPathList(String dir){
		return findImageInfoPathList(new File(dir));
	}
	
	/**
	 * 读取目录下的图片
	 * @param dir	图片目录
	 * @return
	 */
	public static List<ImageInfo<String>> findImageInfoPathList(File dir){
		List<File> fileList = findImageFileList(dir);
		if(fileList.isEmpty()) {
			return new ArrayList<ImageInfo<String>>(0);
		}
		
		File thumbFile = null;
		String filename = null, ext = null, src = null, thumb = null;
		List<ImageInfo<String>> imageInfoList = new ArrayList<ImageInfo<String>>(fileList.size());
		for(File file:fileList) {
			
			src = file.getAbsolutePath();
			ext = FilenameUtils.getExtension(file.getName());
			filename = FilenameUtils.getBaseName(file.getName());
			
			// 缩略图如果不存在则使用原图路径作为缩略图路径
			thumbFile = new File(file.getParentFile(), filename+"_thumb."+ext);
			thumb = thumbFile.exists() == false ? src:thumbFile.getAbsolutePath();
			
			// 路径使用base64编码
			src = CommonUtil.encode(src);
			thumb = CommonUtil.encode(thumb);
			if(StringUtils.isBlank(src) || StringUtils.isBlank(thumb)) {
				continue;
			}
			imageInfoList.add(new ImageInfo<String>(src, thumb, file.getName()));
		}
    	return imageInfoList;
	}
	
	/**
	 * 读取目录下的图片(不包括封面与缩略图)
	 * @param dir	图片目录
	 * @return
	 */
	public static List<File> findImageFileList(String dir){
		return findImageFileList(new File(dir));
	}
	
	/**
	 * 读取目录下的图片(不包括封面与缩略图)
	 * @param dir	图片目录
	 * @return
	 */
	public static List<File> findImageFileList(File dir){
		if(dir == null || !dir.exists() || !dir.isDirectory()) {
    		return new ArrayList<File>(0);
    	}
    	File[] files = dir.listFiles(new FileFilter() {
			@Override
			public boolean accept(File file) {
				if(!file.isFile()) {
					return false;
				}
				String ext = FilenameUtils.getExtension(file.getName());
				if(ImageUtil.isPicture(ext) == false) {
					return false;
				}
				String filename = FilenameUtils.getBaseName(file.getName());
				if(filename.equals("cover") || filename.endsWith("_thumb") || filename.endsWith("_cut")) {
					return false;
				}
				return true;
			}
		});
    	
    	List<File> fileList = Arrays.asList(files);
    	Collections.sort(fileList, new Comparator<File>() {
            @Override
            public int compare(File o1, File o2) {
                String o1name = o1.getName();
                String o2name = o2.getName();
                if(o1name.length() == o2name.length()) {
                    return o1name.compareTo(o2name);
                }
                return o1name.length() - o2name.length();
            }
        });
    	return fileList;
	}
}
