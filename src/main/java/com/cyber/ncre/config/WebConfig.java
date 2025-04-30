package com.cyber.ncre.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

import com.cyber.ncre.util.ServletUtil;
import org.apache.logging.log4j.LogManager;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public ViewResolver viewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/");
        resolver.setSuffix(".jsp");
        resolver.setViewClass(JstlView.class);
        return resolver;
    }

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.viewResolver(viewResolver());
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 配置webapp目录资源访问
        registry.addResourceHandler("/**")
                .addResourceLocations("file:src/main/webapp/");
        
        // 配置图片上传目录的资源访问 - 只使用绝对路径
        String absolutePathMapping = "/" + ServletUtil.UPLOAD_DIR_NAME + "/**";
        String absolutePathLocation = "file:" + ServletUtil.UPLOAD_DIR + "/";
        registry.addResourceHandler(absolutePathMapping)
                .addResourceLocations(absolutePathLocation);
        
        // 添加静态资源目录映射
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
        
        LogManager.getLogger().info("配置了以下静态资源映射:");
        LogManager.getLogger().info("- " + absolutePathMapping + " -> " + absolutePathLocation);
        LogManager.getLogger().info("- /static/** -> classpath:/static/");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }
}