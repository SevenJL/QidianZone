package com.zone.config;

import com.zone.interceptor.JwtTokenAdminInterceptor;
import com.zone.interceptor.JwtTokenUserInterceptor;
import com.zone.json.JacksonObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

/**
 * 配置类，注册web层相关组件
 */
@Configuration
@Slf4j
@RequiredArgsConstructor
public class WebMvcConfiguration extends WebMvcConfigurationSupport {

    private final JwtTokenAdminInterceptor jwtTokenAdminInterceptor;

    private final JwtTokenUserInterceptor jwtTokenUserInterceptor;

    /**
     * 注册自定义拦截器
     *
     * @param registry
     */
    protected void addInterceptors(InterceptorRegistry registry) {
        log.info("开始注册自定义拦截器...");
        registry.addInterceptor(jwtTokenAdminInterceptor)
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/admin/login");

        registry.addInterceptor(jwtTokenUserInterceptor)
                .addPathPatterns("/user/**")
                .excludePathPatterns("/user/login");
    }

    /**
     * 扩展springmvc消息转换器
     */
    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 输出日志
        log.info("扩展消息转换器");

        // 创建一个消息转换器对象
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();

        // 需要为消息转换器设置一个对象转换器 (对象转换器可以将java对象序列化为json对象)
        converter.setObjectMapper(new JacksonObjectMapper());

        // 将自己的消息转换器加入到容器中
        converters.add(0, converter);// index = 0 表示 自己定义的消息转换 优先级是排在第一位的
    }
}
