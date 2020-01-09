package com.security.platform.config.us;

import com.security.platform.config.security.registry.SecureRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @version 1.0
 * @auther liupeiqing
 * @date 2019/10/18 13:35
 * @description 系统本身配置
 */

@Configuration
public class UsConfiguration implements WebMvcConfigurer {

    @Bean
    public SecureRegistry secureRegistry() {
        SecureRegistry secureRegistry = new SecureRegistry();
        secureRegistry.setEnable(true);
        secureRegistry.excludePathPatterns("/auth/**");
        secureRegistry.excludePathPatterns("/log/**");
        secureRegistry.excludePathPatterns("/system/menu/routes");
        secureRegistry.excludePathPatterns("/system/menu/auth-routes");
        secureRegistry.excludePathPatterns("/system/menu/top-menu");
        secureRegistry.excludePathPatterns("/flow/process/resource-view");
        secureRegistry.excludePathPatterns("/flow/process/diagram-view");
        secureRegistry.excludePathPatterns("/flow/manager/check-upload");
        secureRegistry.excludePathPatterns("/doc.html");
        secureRegistry.excludePathPatterns("/js/**");
        secureRegistry.excludePathPatterns("/webjars/**");
        secureRegistry.excludePathPatterns("/swagger-resources/**");
        secureRegistry.excludePathPatterns("/druid/**");
        return secureRegistry;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/cors/**")
                .allowedOrigins("*")
                .allowedHeaders("*")
                .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
                .maxAge(3600)
                .allowCredentials(true);
    }
}
