package com.zone.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.zone.constant.JwtClaimsConstant;
import com.zone.context.BaseContext;
import com.zone.properties.JwtProperties;
import com.zone.result.Result;
import com.zone.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * jwt令牌校验的拦截器
 */


@SuppressWarnings("ALL")
@Slf4j
@Component //交给IOC容器
public class LoginCheckInterceptor implements HandlerInterceptor {
    @Autowired
    private JwtProperties jwtProperties;

    @Override //从目标资源方法执行 返回true:放行 返回false:不放行
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("preHandle ...");
        //登录校验

        // 1.得到URL
        String url = request.getRequestURL().toString();
        log.info("请求的URL:{}",url);
        System.out.println(url);

        // 2.判断url是否包含login
        //如果包含说明是登录请求 直接放行
        if(url.contains("login")){
            log.info("登录操作,放行...");
            return true;
        }

        // 3.获取请求头中 的令牌(token)
        //请求头为token
        String jwt = request.getHeader("token");
        log.info("请求头中的token:{}",jwt);
        Claims claims = JwtUtil.parseJWT(jwtProperties.getUserSecretKey(), jwt);

        // 获取用户id
        Integer id = Integer.valueOf(claims.get(JwtClaimsConstant.DEFAULT).toString());
        BaseContext.setCurrentId(id);
        log.info("当前id:{}", id);

        // 4.判断令牌是否存在
        if(!StringUtils.hasLength(jwt)){
            //hasLength代表这个字符串是否有长度
            //如果为null或者为空 则进入到这个语句中
            log.info("请求头token为空,返回未登录的信息");
            Result error = Result.error("NOT_LOGIN"); //前端只要接受到这个NOT_LOGIN 就强制跳转到登录页面
            //手动转换 为JSON格式
            //使用alibaba的工具包
            String jsonString = JSONObject.toJSONString(error);

            //响应给浏览器
            response.getWriter().write(jsonString);
            return false; //否则代码会继续执行
        }

        // 5.校验jwt令牌
        try {
            JwtUtil.parseJWT("zone",jwt);
        } catch (Exception e) {
            e.printStackTrace();
            //只要报错 说明令牌是非法的
            log.info("解析令牌失败");
            Result error = Result.error("NOT_LOGIN");
            //手动转换 为JSON格式
            //使用alibaba的工具包
            String jsonString = JSONObject.toJSONString(error);

            //响应给浏览器
            response.getWriter().write(jsonString);
            return false; //否则代码会继续执行
        }
        // 6.解析.令牌成功 直接放行

        log.info("令牌合法");
        return true;
    }


}