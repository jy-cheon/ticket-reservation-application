package io.jeeyeon.app.ticketReserve.config;

import io.jeeyeon.app.ticketReserve.interfaces.presentation.filter.ApiLogFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<ApiLogFilter> requestWrapperFilter() {
        FilterRegistrationBean<ApiLogFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new ApiLogFilter());
        registrationBean.addUrlPatterns("/api/*");
        return registrationBean;
    }
}
