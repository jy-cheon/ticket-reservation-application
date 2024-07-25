package io.jeeyeon.app.ticketReserve.interfaces.presentation.Interceptor;

import io.jeeyeon.app.ticketReserve.application.TokenManagerFacade;
import io.jeeyeon.app.ticketReserve.domain.common.exception.BaseException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import java.util.Map;

import static io.jeeyeon.app.ticketReserve.domain.common.exception.ErrorType.INVALID_TOKEN;

@Slf4j
@RequiredArgsConstructor
public class TokenInterceptor implements HandlerInterceptor {

    private final TokenManagerFacade tokenManagerFacade;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // Auth header 추출
        Long tokenId = Long.valueOf(request.getHeader("Auth"));

        // concertId 추출 from path variables
        Map<String, String> pathVariables = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        Long concertId = Long.valueOf(pathVariables.get("concertId"));

        if (tokenId == null) {
            throw new BaseException(INVALID_TOKEN);
        }

        tokenManagerFacade.validateActiveToken(concertId, tokenId);

        return true;
    }
}
