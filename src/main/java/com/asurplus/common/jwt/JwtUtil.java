package com.asurplus.common.jwt;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONObject;
import com.asurplus.common.redis.RedisConst;
import com.asurplus.common.redis.RedisUtil;
import com.asurplus.common.utils.CommonUtil;
import com.asurplus.common.utils.IPUtils;
import com.asurplus.common.utils.ResponseResult;
import com.asurplus.common.utils.SpringContextUtils;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.google.code.kaptcha.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;


/**
 * Jwt工具类，生成JWT和认证
 *
 * @author dongk
 * @date 2021-02-05 11:10:08
 */
@Slf4j
@Component
public class JwtUtil {

    private static RedisUtil redisUtil;

    @Autowired
    private void setRedisUtil(RedisUtil redisUtil){
        JwtUtil.redisUtil=redisUtil;
    }

    /*@Autowired
    private RedisUtil redisUtil;*/

    /**
     * 密钥
     */
    private static final String SECRET = "asurplus_secret";

    /**
     * 过期时间（单位：秒）
     **/
    private static final long EXPIRATION = 3600L;

    /**
     * 生成用户token,设置token超时时间
     *
     * @param userId
     * @return
     */
    public String createToken(Integer userId) {
        HttpServletRequest request = SpringContextUtils.getHttpServletRequest();
        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");
        String token = JWT.create()
                // 添加头部
                .withHeader(map)
                // 放入用户的id
                .withAudience(String.valueOf(userId))
                // 可以将基本信息放到claims中
//                .withClaim("browserName", JwtUtil.getBrowserName())//浏览器信息
                .withClaim("userId", String.valueOf(userId))//用户id
                .withClaim("IP", IPUtils.getIpAddress(request))//用户ip地址
                // 超时设置,设置过期的日期
                .withExpiresAt(new Date((System.currentTimeMillis() + EXPIRATION * 1000)*24))//一天
                // 签发时间
                .withIssuedAt(new Date())
                // SECRET加密
                .sign(Algorithm.HMAC256(SECRET));

        // 存入redis
//        redisUtil.set(RedisConst.Key.API_USER_TOKEN +":" + token,userId , System.currentTimeMillis() + EXPIRATION * 1000);
        String tokenUuid = UUID.randomUUID().toString();
        redisUtil.set(RedisConst.Key.API_USER_TOKEN +":" + tokenUuid,token , EXPIRATION*24);
        return tokenUuid;
    }

    /**
     * 获取用户id
     */
    public Integer getUserId() {
        HttpServletRequest request = SpringContextUtils.getHttpServletRequest();
        // 从请求头部中获取token信息
        String token = request.getHeader("Authorization");
        try {

            if(StringUtils.isBlank(token)){
                requestBack(401,"token不能为空");
                return null;
            }

            //验证ip
            /*String userIp =  JWT.decode(token).getClaim("IP").asString();
            if (StringUtils.isNotBlank(userIp)) {
                if(!userIp.equals(IPUtils.getIpAddress(request))){
                    requestBack(401,"非法ip访问");
                    return  null;
                }
            }*/

            if (StringUtils.isNotBlank(token)) {
                Object object = redisUtil.get(RedisConst.Key.API_USER_TOKEN +":" + token);
                if (null != object) {
                    String userid =  JWT.decode(object.toString()).getClaim("userId").asString();
                    return Integer.parseInt(userid);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        requestBack(401,"用户信息已过期，请重新登录");
        return null;
    }

    /**
     * 校验token并解析token
     */
    public boolean verity() throws IOException {
        HttpServletRequest request = SpringContextUtils.getHttpServletRequest();
        // 从请求头部中获取token信息
        String tokenUuid = request.getHeader("Authorization");
        if(StringUtils.isBlank(tokenUuid)){
            requestBack(401,"token不能为空");
            return false;
        }
        if (StringUtils.isNotBlank(tokenUuid)) {
            try {
                Object object = redisUtil.get(RedisConst.Key.API_USER_TOKEN +":" + tokenUuid);
                if (null != object) {
                    /*String userIp =  JWT.decode(object.toString()).getClaim("IP").asString();
                    if (StringUtils.isNotBlank(userIp)) {
                        String ip = IPUtils.getIpAddress(request);
                        if(!userIp.equals(ip)){
                            requestBack(401,"非法ip访问 :"+ip);
                            return  false;
                        }
                    }*/
                    // 刷新存活时间
                    redisUtil.setExpire(RedisConst.Key.API_USER_TOKEN +":" + tokenUuid, EXPIRATION*24 );
                    // 返回结果认证
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // 响应状态码为401
//        SpringContextUtils.getHttpServletResponse().setStatus(401);
//        SpringContextUtils.getHttpServletResponse().sendError(401,"用户信息已过期，请重新登录");
        requestBack(401,"用户信息已过期，请重新登录");
//        SpringContextUtils.getHttpServletResponse().sendError(401,"用户信息已过期，请重新登录");
        return false;
    }

    public Boolean verityIpOrBrowser() throws IOException {
        HttpServletRequest request = SpringContextUtils.getHttpServletRequest();
        //从请求头部中获取token信息
        String token = request.getHeader("Authorization");
        if(StringUtils.isBlank(token)){
            SpringContextUtils.getHttpServletResponse().sendError(401,"token不能为空");
            return false;
        }
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            if (null != jwt) {
                // 拿到我们放置在token中的信息
                List<String> audience = jwt.getAudience();
                String userIp = jwt.getClaim("IP").asString();
                String browserName = jwt.getClaim("browserName").asString();
//                JWT.decode(token).getClaim("IP").asString();

                if (CollectionUtil.isNotEmpty(audience)) {
//                    return ResponseResult.success("认证成功", audience.get(0));
                    if(!userIp.equals(IPUtils.getIpAddress(request))){
                        SpringContextUtils.getHttpServletResponse().sendError(401,"非法ip访问");
                        return  false;
                    }
                    /*if(!browserName.equals(JwtUtil.getBrowserName())){
                        SpringContextUtils.getHttpServletResponse().sendError(401,"非法浏览器访问");
                        return  false;
                    }*/
                    return true;
                }
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (JWTVerificationException e) {
            e.printStackTrace();
        }
        SpringContextUtils.getHttpServletResponse().sendError(401,"用户信息已过期，请重新登录");
        return  false;
    }

    /**
     * 返回接口状态以及信息
     */
    public static void requestBack(Integer code, String msg){
        try{
            HttpServletResponse response = SpringContextUtils.getHttpServletResponse();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json; charset=utf-8");
            JSONObject res = new JSONObject();
            res.put("success","false");
            res.put("code",code);
            res.put("msg",msg);
            PrintWriter out = null ;
            out = response.getWriter();
            out.write(res.toString());
            out.flush();
            out.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }



    /**
     * 获取浏览器信息
     * @return
     */
    public static String getBrowserName() {
        HttpServletRequest request = SpringContextUtils.getHttpServletRequest();

        String browserName = "";
        String userAgent = request.getHeader("User-Agent").toUpperCase();
        if (userAgent == null || userAgent.equals("")) {
            return "";
        }
        if (userAgent.indexOf("MSIE") > 0) {
            browserName = "IE";
        } else if (userAgent.indexOf("FIREFOX") > 0) {
            browserName = "Firefox";
        } else if (userAgent.indexOf("CHROME") > 0) {
            browserName = "Chrome";
        } else if (userAgent.indexOf("SAFARI") > 0) {
            browserName = "Safari";
        } else if (userAgent.indexOf("CAMINO") > 0) {
            browserName = "Camino";
        } else if (userAgent.indexOf("KONQUEROR") > 0) {
            browserName = "Konqueror";
        } else if (userAgent.indexOf("EDGE") > 0) {
            browserName = "Microsoft Edge";
        }
        return browserName;
    }


    /**
     * 校验token并解析token
     */
    /*public  ResponseResult verity1() {
        HttpServletRequest request = SpringContextUtils.getHttpServletRequest();
        // 从请求头部中获取token信息
        String token = request.getHeader("Authorization");
        if (StringUtils.isBlank(token)) {
            return ResponseResult.error(401, "用户信息已过期，请重新登录");
        }
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            if (null != jwt) {
                // 拿到我们放置在token中的信息
                List<String> audience = jwt.getAudience();
                if (CollectionUtil.isNotEmpty(audience)) {
                    return ResponseResult.success("认证成功", audience.get(0));
                }
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (JWTVerificationException e) {
            e.printStackTrace();
        }
        return ResponseResult.error(401, "用户信息已过期，请重新登录");
    }*/

    public static ResponseResult frontValidate(String registerCode) {
        HttpServletRequest request = SpringContextUtils.getHttpServletRequest();

        String ip = IPUtils.getIpAddress(request);
//        System.out.println(ip);

        String attribute = (String) redisUtil.get(Constants.KAPTCHA_SESSION_CONFIG_KEY+ip);

        if (!CommonUtil.isNotNull(attribute)) {
            return ResponseResult.error("验证码过期！");
        }
        if (!attribute.equals(registerCode)) {
            return ResponseResult.error("验证码错误！");
        }
        redisUtil.del(Constants.KAPTCHA_SESSION_CONFIG_KEY+ip);
        return  null;
    }

    public static void main(String[] args) {
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiIxNCIsIklQIjoiMTkyLjE2OC4xLjE1OCIsImV4cCI6MTY3MjkwMTE0NCwiaWF0IjoxNjcyOTAxMDg0fQ.thk8CU0MdLXKUnEvfOw-EougZO1ptyPS5iy2Gh8cmBY";
        token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiIxNiIsIklQIjoiMTE3LjE3Mi41My43MyIsImV4cCI6NDA0NjcwNzEyOTAsInVzZXJJZCI6IjE2IiwiaWF0IjoxNjg2MTI0MzcwfQ.0lcO8oPiyxjBQfaw4l1px7dYv0dFVJO690BF-W2J-xI";
        String s = JWT.decode(token).getClaim("IP").asString();
        System.out.println(s);
    }

}
