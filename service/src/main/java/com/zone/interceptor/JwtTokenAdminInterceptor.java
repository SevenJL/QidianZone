package com.zone.interceptor;

import com.zone.constant.JwtClaimsConstant;
import com.zone.context.BaseContext;
import com.zone.properties.JwtProperties;
import com.zone.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * jwt令牌校验的拦截器
 */
@Component
@Slf4j
@RequiredArgsConstructor
@SuppressWarnings("ALL")
public class JwtTokenAdminInterceptor implements HandlerInterceptor {

    private final JwtProperties jwtProperties;

    @Override
    public boolean preHandle(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response, Object handler) throws Exception {
        // 判断当前拦截到的是否是动态方法
        if (!(handler instanceof HandlerMethod)) {
            //当前拦截到的不是动态方法，直接放行
            return true;
        }
        log.info("执行JwtTokenAdminInterceptor");

        // 从请求头中获取token
        String token = request.getHeader(jwtProperties.getAdminTokenName());

        // 判断token是否为空 如果为空则返回401
        try {
            log.info("jwt校验:{}", token);
            Claims claims = JwtUtil.parseJWT(jwtProperties.getAdminSecretKey(), token);
            Integer adminId = Integer.valueOf(claims.get(JwtClaimsConstant.ADMIN_ID).toString());
            log.info("当前管理员id：{}", adminId);
            BaseContext.setCurrentId(adminId);
            return true;
        } catch (Exception ex) {
            response.setStatus(401);
            log.error("jwt校验失败:{}", ex.getMessage());
            return false;
        }
    }





}
