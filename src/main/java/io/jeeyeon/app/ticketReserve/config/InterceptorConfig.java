package io.jeeyeon.app.ticketReserve.config;

import io.jeeyeon.app.ticketReserve.application.TokenManagerFacade;
import io.jeeyeon.app.ticketReserve.interfaces.presentation.Interceptor.TokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    TokenManagerFacade tokenManagerFacade;

    private static final String[] EXCLUDE_URLS  = {"/api/v*/users/**","/api/v*/concerts/*/token/**","/api/v*/token","/api/v1/concerts"};

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TokenInterceptor(tokenManagerFacade))
                .addPathPatterns("/api/v*/**")
                .excludePathPatterns(EXCLUDE_URLS);
    }
}
