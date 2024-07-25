package io.jeeyeon.app.ticketReserve.interfaces.presentation.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.UUID;

@Slf4j
public class ApiLogFilter implements Filter {

    private static final Logger apiLog = LoggerFactory.getLogger("API");

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        CachedBodyHttpServletRequestWrapper cachedRequest = new CachedBodyHttpServletRequestWrapper(httpRequest);
        String requestBody = cachedRequest.getReader().lines().reduce("", (accumulator, actual) -> accumulator + actual);
        String requestId = UUID.randomUUID().toString();

        apiLog.info("RequestId : {}, Request URL: {}, HTTP Method: {}, IP: {} Request Body: {}", requestId, cachedRequest.getRequestURL(), cachedRequest.getMethod(), cachedRequest.getRemoteAddr(), requestBody);

        chain.doFilter(cachedRequest, response);

        apiLog.info("RequestId : {}, Response Status: {}", requestId, httpResponse.getStatus());
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}
