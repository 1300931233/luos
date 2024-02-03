package com.asurplus.common.image;

import java.io.Serializable;

/**
 * 资源图片
 * @author 	马义雯
 * @date	2021-09-29
 * @version	1.0
 *
 */
public class ImageInfo<T> implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * 原图路径
	 */
	private T src;
	
	/**
	 * 缩略图路径
	 */
	private T thumb;

	private String fileName;
	

	public ImageInfo(T src, T thumb, String fileName) {
		super();
		this.src = src;
		this.thumb = thumb;
		this.fileName = fileName;
	}

	public T getSrc() {
		return src;
	}

	public void setSrc(T src) {
		this.src = src;
	}

	public T getThumb() {
		return thumb;
	}

	public void setThumb(T thumb) {
		this.thumb = thumb;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public String toString() {
		return "ImageInfo [src=" + src + ", thumb=" + thumb + "]";
	}
	
	
}
