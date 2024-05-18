package com.zone.interceptor;

import com.zone.constant.JwtClaimsConstant;
import com.zone.context.BaseContext;
import com.zone.properties.JwtProperties;
import com.zone.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
public class JwtTokenUserInterceptor implements HandlerInterceptor {

    private final JwtProperties jwtProperties;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 1.判断当前拦截到的是Controller的方法还是其他资源
        if (!(handler instanceof HandlerMethod)) {
            //当前拦截到的不是动态方法，直接放行
            return false;
        }
        // 2.从请求头中获取令牌
        String token = request.getHeader(jwtProperties.getUserTokenName());
        // 3.校验令牌
        try {
            log.info("jwt校验:{}", token);
            Claims claims = JwtUtil.parseJWT(jwtProperties.getUserSecretKey(), token);
            Integer userId = Integer.valueOf(claims.get(JwtClaimsConstant.USER_ID).toString());
            log.info("当前用户的id：{}", userId);
            BaseContext.setCurrentId(userId);
            return true;
        } catch (Exception ex) {
            // 4.校验失败返回401
            response.setStatus(401);
            return false;
        }
    }



}
