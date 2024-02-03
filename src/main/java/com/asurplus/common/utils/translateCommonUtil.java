package com.asurplus.common.utils;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author ljh
 */
public class translateCommonUtil {

    public static String httpclientGet (String url, Map<String,String> params, Map<String,String> headers){
        // 获取连接客户端工具
        CloseableHttpClient httpClient = HttpClients.createDefault();

        String entityStr = null;
        CloseableHttpResponse response = null;

        try {
            /*
             * 由于GET请求的参数都是拼装在URL地址后方，所以我们要构建一个URL，带参数
             */
            URIBuilder uriBuilder = new URIBuilder(url);
            /** 第一种添加参数的形式 */
            if (params!=null&&params.size()>0) {
                for (String key : params.keySet()) {
                    uriBuilder.addParameter(key,params.get(key) );
                }
            }
            /** 第二种添加参数的形式
             List<NameValuePair> list = new LinkedList<NameValuePair>();
             BasicNameValuePair param1 = new BasicNameValuePair("grant_type", "client_credentials");
             BasicNameValuePair param2 = new BasicNameValuePair("scope", "r,w HTTP/1.1");
             list.add(param1);
             list.add(param2);
             uriBuilder.setParameters(list);
             */
            // 根据带参数的URI对象构建GET请求对象
            HttpGet httpGet = new HttpGet(uriBuilder.build());
            //添加请求头信息
            if (headers!=null&&headers.size()>0) {
                for (String key : headers.keySet()) {
                    httpGet.addHeader(key, headers.get(key));
                }
            }
            // 执行请求
            response = httpClient.execute(httpGet);
            // 获得响应的实体对象
            HttpEntity entity = response.getEntity();
            // 使用Apache提供的工具类进行转换成字符串
            entityStr = EntityUtils.toString(entity, "UTF-8");
        } catch (ClientProtocolException e) {
            System.err.println("Http协议出现问题");
            e.printStackTrace();
        } catch (URISyntaxException e) {
            System.err.println("URI解析异常");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("IO异常");
            e.printStackTrace();
        } finally {
            // 释放连接
            if (null != response) {
                try {
                    response.close();
                    httpClient.close();
                } catch (IOException e) {
                    System.err.println("释放连接出错");
                    e.printStackTrace();
                }
            }
        }
        return entityStr;
    }


    /**
     * post请求调用
     * @param url 请求路径
     * @param params  请求参数
     * @param headers  请求头
     */
    public static String httpclientPost(String url,Map<String,String> params, Map<String,String> headers){
        // 获取连接客户端工具
        CloseableHttpClient httpClient = HttpClients.createDefault();

        //设置超时时间
        RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(5000)
                .setSocketTimeout(10000).setConnectTimeout(10000).build();

        String entityStr = null;
        CloseableHttpResponse response = null;
        try {
            // 创建POST请求对象
            HttpPost httpPost = new HttpPost(url);
            httpPost.setConfig(requestConfig);
            //添加请求头信息
            if (headers != null) {
                for (String key : headers.keySet()) {
                    httpPost.addHeader(key, headers.get(key));
                }
            }
            //设置参数
            //httpPost.setEntity(new StringEntity(params,"utf-8"));//json
            if (params != null) {
                List<NameValuePair> nvps = new ArrayList<NameValuePair>();
                for (String key : params.keySet()) {
                    nvps.add(new BasicNameValuePair(key, params.get(key)));
                }
                httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
            }
            // 执行请求
            response = httpClient.execute(httpPost);
            // 获得响应的实体对象
            HttpEntity entity = response.getEntity();
            // 使用Apache提供的工具类进行转换成字符串
            entityStr = EntityUtils.toString(entity, "UTF-8");
        } catch (ClientProtocolException e) {
            System.err.println("Http协议出现问题");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("IO异常");
            e.printStackTrace();
        } finally {
            // 释放连接
            if (null != response) {
                try {
                    response.close();
                    httpClient.close();
                } catch (IOException e) {
                    System.err.println("释放连接出错");
                    e.printStackTrace();
                }
            }
        }
        return entityStr;
    }
}
