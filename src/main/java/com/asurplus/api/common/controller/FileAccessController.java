package com.asurplus.api.common.controller;

import com.asurplus.common.utils.CommonUtil;
import com.asurplus.common.utils.FileCommonUtil;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * 
 * 服务器本地资源（文件）访问与下载
 *
 * @author：wanglin
 *
 * @date：Aug 9, 2018
 */
@Api(tags = "文件访问接口")
@Controller
public class FileAccessController {

	@Value("${sysconfig.file-root-dir}")
	private String uploadFilePath;

	@ApiOperation("文件在线访问，需要对访问地址进行解码")
	@ApiOperationSupport(order = 1)
	//文件在线访问，需要对访问地址进行解码
	@GetMapping(value="/file/access/{filepath}")
	public void fileAccess1(@PathVariable String filepath, HttpServletRequest request, HttpServletResponse response) throws Exception {
		filepath = CommonUtil.decode(filepath);
		downRangeFile(filepath, request, response);
	}

	@ApiOperation("文件在线访问，不需要对访问地址进行解码")
	@ApiOperationSupport(order = 2)
	//文件在线访问，不需要对访问地址进行解码
	@GetMapping(value="/file/viewer")
	public void fileAccess2(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String filepath = ServletRequestUtils.getStringParameter(request, "path", "");
		downRangeFile(filepath, request, response);
	}


	//文件在线访问
	@ApiOperation("文件下载，根据文件绝对路径")
	@ApiOperationSupport(order = 3)
	@GetMapping(value="/file/download/{filepath}")
	public void fileDownload(@PathVariable String filepath, HttpServletRequest request, HttpServletResponse response) throws IOException {
		if(!CommonUtil.isNotNull(filepath)){
			printError("文件名不能为空！", response);
			return;
		}
		
		filepath = CommonUtil.decode(filepath);
		File f = new File(filepath);
		readFileToStream(f, request, response);
	}

	//文件在线访问
	@ApiOperation("本地固定文件下载，根据文件名称")
	@ApiOperationSupport(order = 4)
	@GetMapping(value="/file/name/download")
	public void localFileDownload(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String fileName = ServletRequestUtils.getStringParameter(request,"fileName", "");
		if(!CommonUtil.isNotNull(fileName)){
			printError("文件名不能为空！", response);
			return;
		}

		String filePath = CommonUtil.uploadFilePath(uploadFilePath)+"download/" + fileName;
		File f = new File(filePath);
		readFileToStream(f, request, response);
	}
	
	
	//项目内部案例资源下载
	@ApiOperation("项目内部案例资源下载")
	@ApiOperationSupport(order = 5)
	@GetMapping(value="/res/demo/{filecode}")
	public void resDemoDownload(@PathVariable String filecode, HttpServletRequest request, HttpServletResponse response) throws IOException {
		if(!CommonUtil.isNotNull(filecode)){
			printError("文件编码不能为空！", response);
			return;
		}
		
		String filename = null;
		if(filecode.equals("file")){
			filename = "文件格式案例资源.zip";
		}else if (filecode.equals("img")){
			filename = "图片预览案例资源.zip";
		}else if (filecode.equals("html")){
			filename = "html格式案例资源.zip";
		}else if (filecode.equals("dynamic")){
			filename = "dynamic-template.xls";
		}
		
		String filepath = request.getSession().getServletContext().getRealPath("/");
		filepath = filepath.replaceAll("\\\\", "/");
		filepath += "resdemo/" + filename;
		
		File f = new File(filepath);
		
		if(f.exists()) {
			readFileToStream(f, request, response);
		}else {
			System.out.println("指定文件(" + filepath + ")不存在！");
		}
	}

	private void readFileToStream(File file, HttpServletRequest request, HttpServletResponse response) throws IOException {
		if(file.exists() == false){
			printError("文件“"+file.getAbsolutePath()+"”不存在", response);
			return;
		}

		String filename = file.getName();
		// response.reset();
		try {
			// 处理不同浏览器对文件名的解析问题
			String agent = (String)request.getHeader("USER-AGENT");
			if(agent != null && agent.indexOf("MSIE") == -1) {
				// FF
				filename = "=?UTF-8?B?" + (new String(Base64.encodeBase64(filename.getBytes("UTF-8")))) + "?=";
			}else {
				// IE
				filename = new String(filename.getBytes("GBK"), "ISO-8859-1");
			}
		}catch(UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		response.setContentType("application/x-msdownload; charset=utf-8");
		response.setHeader("Content-Disposition", "attachment; filename=" + filename);
		response.setContentLengthLong(file.length());
		byte[] buf = new byte[4096];
		int len = 0;
		BufferedInputStream br = null;
		OutputStream out = null;
		try {
			br = new BufferedInputStream(new FileInputStream(file));
			out = response.getOutputStream();
			while((len = br.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			out.flush();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			if(br != null) {
				try {
					br.close();
				}catch(IOException e) {
					e.printStackTrace();
				}
				br = null;
			}
			if(out != null) {
				try {
					out.close();
				}catch(IOException e) {
					e.printStackTrace();
				}
				out = null;
			}
		}
	}
	
	/**
	 * 分片下载文件
	 * @author MaChao
	 * @time 2019-6-18
	 */
	public void downRangeFile(@PathVariable String filePath, HttpServletRequest request, HttpServletResponse response) throws Exception{
		String extName = "";
		File downloadFile = new File(filePath);
		// 设置文件Content-Type
        String contentType = FileCommonUtil.setContentType(downloadFile.getName());
        if (contentType != null) {
            response.setContentType(contentType);
        } else {
            response.setContentType("application/pdf;charset=UTF-8");// set the  
        }
        // 文件不存在  
        if (!downloadFile.exists()) {
        	System.out.println("文件：" + filePath + "  不存在！");
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        long fileLength = downloadFile.length();// 记录文件大小  
        long pastLength = 0;// 记录已下载文件大小  
        int rangeSwitch = 0;// 0：从头开始的全文下载；1：从某字节开始的下载（bytes=27000-）；2：从某字节开始到某字节结束的下载（bytes=27000-39000）  
        long toLength = 0;// 记录客户端需要下载的字节段的最后一个字节偏移量（比如bytes=27000-39000，则这个值是为39000）  
        long contentLength = 0;// 客户端请求的字节总量  
        String rangeBytes = "";// 记录客户端传来的形如“bytes=27000-”或者“bytes=27000-39000”的内容  
        RandomAccessFile raf = null;// 负责读取数据  
        OutputStream os = null;// 写出数据  
        OutputStream out = null;// 缓冲  
        int bsize = 1024;// 缓冲区大小  
        byte b[] = new byte[bsize];// 暂存容器  

        String range = request.getHeader("Range");
        // if(range == null)  
        // range = "bytes=0-";  
        int responseStatus = 206;
        if (range != null && range.trim().length() > 0 && !"null".equals(range)) {// 客户端请求的下载的文件块的开始字节  
            responseStatus = HttpServletResponse.SC_PARTIAL_CONTENT;
            rangeBytes = range.replaceAll("bytes=", "");
            if (rangeBytes.endsWith("-")) {// bytes=969998336-  
                rangeSwitch = 1;
                rangeBytes = rangeBytes.substring(0, rangeBytes.indexOf('-'));
                pastLength = Long.parseLong(rangeBytes.trim());
                contentLength = fileLength - pastLength;// 客户端请求的是  
                // 969998336之后的字节（包括bytes下标索引为969998336的字节）  
            } else {
            	// bytes=1275856879-1275877358  
                rangeSwitch = 2;
                String temp0 = rangeBytes.substring(0, rangeBytes.indexOf('-'));
                String temp2 = rangeBytes.substring(rangeBytes.indexOf('-') + 1, rangeBytes.length());
                // bytes=1275856879-1275877358，从第1275856879个字节开始下载  
                pastLength = Long.parseLong(temp0.trim());
                toLength = Long.parseLong(temp2);// bytes=1275856879-1275877358，到第  
                // 1275877358 个字节结束  
                contentLength = toLength - pastLength + 1;// 客户端请求的是  
                // 1275856879-1275877358  
                // 之间的字节  
            }
        } else {// 从开始进行下载  
            contentLength = fileLength;// 客户端要求全文下载  
        }

        /**
         * 如果设设置了Content-Length，则客户端会自动进行多线程下载。如果不希望支持多线程，则不要设置这个参数。 响应的格式是:
         * Content-Length: [文件的总大小] - [客户端请求的下载的文件块的开始字节]
         * ServletActionContext.getResponse().setHeader("Content-Length", new
         * Long(file.length() - p).toString());
         */
        // 来清除首部的空白行  
        response.reset();
		// 允许跨域的主机地址
		response.setHeader("Access-Control-Allow-Origin", "*");
		// 允许跨域的请求方法GET, POST, HEAD 等
		response.setHeader("Access-Control-Allow-Methods", "*");
		// 重新预检验跨域的缓存时间 (s)
		response.setHeader("Access-Control-Max-Age", "4200");
		// 允许跨域的请求头
		response.setHeader("Access-Control-Allow-Headers", "*");
		// 是否携带cookie
		response.setHeader("Access-Control-Allow-Credentials", "true");
        // 告诉客户端允许断点续传多线程连接下载,响应的格式是:Accept-Ranges: bytes
        response.setHeader("Accept-Ranges", "bytes");
        // 如果是第一次下,还没有断点续传,状态是默认的 200,无需显式设置;响应的格式是:HTTP/1.1  
        if (rangeSwitch != 0) {
            response.setStatus(responseStatus);
            // 不是从最开始下载，断点下载响应号为206  
            // 响应的格式是:  
            // Content-Range: bytes [文件块的开始字节]-[文件的总大小 - 1]/[文件的总大小]  
            // System.out.println("----------------------------片段下载，服务器即将开始断点续传...");
            switch (rangeSwitch) {
                case 1: {
                	// 针对 bytes=27000- 的请求  
                    String contentRange = new StringBuffer("bytes ")
                            .append(new Long(pastLength).toString()).append("-")
                            .append(new Long(fileLength - 1).toString())
                            .append("/").append(new Long(fileLength).toString())
                            .toString();
                    response.setHeader("Content-Range", contentRange);
                    break;
                }
                case 2: {
                	// 针对 bytes=27000-39000 的请求  
                    String contentRange = range.replace("=", " ") + "/"
                            + new Long(fileLength).toString();
                    response.setHeader("Content-Range", contentRange);
                    break;
                }
                default: {
                    break;
                }
            }
        } else {
            String contentRange = new StringBuffer("bytes ").append("0-")
                    .append(fileLength - 1).append("/").append(fileLength)
                    .toString();
            response.setHeader("Content-Range", contentRange);
        }

        try {
            response.setHeader("Content-Length", String.valueOf(contentLength));
            os = response.getOutputStream();
            out = new BufferedOutputStream(os);
            raf = new RandomAccessFile(downloadFile, "r");
            try {
                long outLength = 0;// 实际输出字节数  
                switch (rangeSwitch) {
                    case 0: {// 普通下载，或者从头开始的下载  
                        // 同1，没有break  
                    }
                    case 1: {// 针对 bytes=27000- 的请求  
                        raf.seek(pastLength);// 形如 bytes=969998336- 的客户端请求，跳过  
                        // 969998336 个字节  
                        int n = 0;
                        while ((n = raf.read(b)) != -1) {
                            out.write(b, 0, n);
                            outLength += n;
                        }
                        // while ((n = raf.read(b, 0, 1024)) != -1) {  
                        // out.write(b, 0, n);  
                        // }  
                        break;
                    }
                    case 2: {
                        // 针对 bytes=27000-39000 的请求，从27000开始写数据  
                        raf.seek(pastLength);
                        int n = 0;
                        long readLength = 0;// 记录已读字节数  
                        while (readLength <= contentLength - bsize) {// 大部分字节在这里读取  
                            n = raf.read(b);
                            readLength += n;
                            out.write(b, 0, n);
                            outLength += n;
                        }
                        if (readLength <= contentLength) {// 余下的不足 1024 个字节在这里读取  
                            n = raf.read(b, 0, (int) (contentLength - readLength));
                            out.write(b, 0, n);
                            outLength += n;
                        }
                        break;
                    }
                    default: {
                        break;
                    }
                }
                // System.out.println("Content-Length为：" + contentLength + "；实际输出字节数：" + outLength);
                out.flush();
            } catch (IOException ie) {
                /**
                 * 在写数据的时候， 对于 ClientAbortException 之类的异常，
                 * 是因为客户端取消了下载，而服务器端继续向浏览器写入数据时， 抛出这个异常，这个是正常的。
                 * 尤其是对于迅雷这种吸血的客户端软件， 明明已经有一个线程在读取 bytes=1275856879-1275877358，
                 * 如果短时间内没有读取完毕，迅雷会再启第二个、第三个。。。线程来读取相同的字节段， 直到有一个线程读取完毕，迅雷会 KILL
                 * 掉其他正在下载同一字节段的线程， 强行中止字节读出，造成服务器抛 ClientAbortException。
                 * 所以，我们忽略这种异常
                 */
                // ignore  
            }
        } catch (Exception e) {
        	System.out.println(/*Level.SEVERE,*/ e.getMessage());
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                	System.out.println(/*Level.SEVERE,*/ e.getMessage());
                }
            }
            if (raf != null) {
                try {
                    raf.close();
                } catch (IOException e) {
                	System.out.println(/*Level.SEVERE,*/ e.getMessage());
                }
            }
        }
	}

	private void printError(String msg, HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
		out.println("<HTML lang='zh-CN'>");
		out.println("  <HEAD><TITLE>提示</TITLE></HEAD>");
		out.println("  <BODY><h3>提示</h3>");
		out.print(msg);
		out.println("  </BODY>");
		out.println("</HTML>");
		out.flush();
		out.close();
	}
}
