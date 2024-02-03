package com.luos.common.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class CommonUtil {
	
	final private static List<String> CHARLIST = new ArrayList<String>();
	static{
		CHARLIST.add("z");
		CHARLIST.add("K");
		CHARLIST.add("U");
		CHARLIST.add("O");
		CHARLIST.add("J");
		CHARLIST.add("M");
		CHARLIST.add("w");
		CHARLIST.add("A");
		CHARLIST.add("s");
		CHARLIST.add("E");
	}

	public static boolean isNotNull(String value) {
		return value != null && !"".equals(value) && !"null".equals(value.toLowerCase());
	}
	
	
	final public static String nullToBlank(String data){
		if(null == data){
			return "";
		}else{
			return data;
		}
	}



	/**
	 * 编码
	 *
	 * @param str
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String encode(String str) {
		if(isNotNull(str) == false){
			return "";
		}
		Base64 base64 = new Base64();
		try {
			str = java.net.URLEncoder.encode(str, "utf-8");
			return new String(base64.encode(str.getBytes()));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return str;
		}
	}

	/**
	 * 解码
	 *
	 * @param str
	 * @return
	 * @throws IOException
	 */
	public static String decode(String str) throws IOException {
		if(isNotNull(str) == false){
			return "";
		}
		Base64 base64 = new Base64();
		str = new String(base64.decode(str.getBytes()));
		return java.net.URLDecoder.decode(str, "utf-8");
	}
	
	
	// 处理TextArea的换行符
	public static String hangleTextArea(String in) {
		StringBuffer out = new StringBuffer();
		for(int i = 0; in != null && i < in.length(); i++) {
			char c = in.charAt(i);
			if(c == '\'')
				out.append("&#039;");
			else if(c == '\"')
				out.append("&#034;");
			else if(c == '<')
				out.append("&lt;");
			else if(c == '>')
				out.append("&gt;");
			else if(c == '&')
				out.append("&amp;");
			else if(c == ' ')
				out.append("&nbsp;");
			else if(c == '\n')
				out.append("<br/>");
			else
				out.append(c);
		}
		return out.toString();
	}
	

	/**
	 * 将数字ID转换为字符串
	 * @param idStr
	 * @return
	 */
	public static String idToString(String idStr){
		if(!CommonUtil.isNotNull(idStr)){
			return "";
		}
		char[] charArr = idStr.toCharArray();
		StringBuffer sb = new StringBuffer();
		for(char c : charArr){
			if(Character.isDigit(c)){
				sb.append(CHARLIST.get(Integer.valueOf(String.valueOf(c))));
			}else{
				sb.append(c);
			}
		}
		String str = sb.toString();
		sb = null;
		return str;
	}
	
	/**
	 * 将字符串ID转换为long
	 * @param idStr
	 * @return
	 */
	public static Long strToId(String idStr){
		if(!CommonUtil.isNotNull(idStr)){
			return null;
		}
		try {
			char[] charArr = idStr.toCharArray();
			StringBuffer sb = new StringBuffer();
			String s = null;
			for(char c : charArr){
				s = String.valueOf(c);
				if(CHARLIST.contains(s)){
					sb.append(CHARLIST.indexOf(s));
				}else{
					sb.append(s);
				}
			}
			String str = sb.toString();
			sb = null;
			return Long.parseLong(str);			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}


	/**
	 * 获取头部中的语种 并转换
	 * @return
	 */
	public static String getlanguage(){
		HttpServletRequest request = SpringContextUtils.getHttpServletRequest();
		String language = request.getHeader("ResourceLanguage");
		//0:中文、1：藏文、2：英文     默认中文
		if(!StringUtils.isNotBlank(language)){
			language = "0";
		}
		if(language.equals("zh")){
			language = "0";
		}else if (language.equals("zw")){
			language = "1";
		}else if(language.equals("en")){
			language = "2";
		}

		return  language;
	}

	/**
	 * 转换项目文件上传路径
	 * @return
	 */
	public static String uploadFilePath(String uploadPath){
		if(StringUtils.isBlank(uploadPath)){
			return "";
		}
		String lastChar = uploadPath.substring(uploadPath.length() - 1);
		if(!lastChar.equals("/") && !lastChar.equals("\\")){
			uploadPath += "/";
		}
		return uploadPath;
	}
}
