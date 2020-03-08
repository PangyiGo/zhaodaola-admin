package com.sise.zhaodaola.core.security.security;

import com.sise.zhaodaola.core.security.config.SpringSecurityProperities;
import com.sise.zhaodaola.tool.utils.SpringContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @Author: PangYi
 * @Date 2020/3/84:34 下午
 */
@Slf4j
public class TokenFilter extends GenericFilterBean {

    private final TokenProvider tokenProvider;

    TokenFilter(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String token = resolveToken(httpServletRequest);
        String requestRri = httpServletRequest.getRequestURI();
        // Token验证
        if (StringUtils.hasText(token) && tokenProvider.validateToken(token) && SecurityContextHolder.getContext().getAuthentication() == null) {
            Authentication authentication = tokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.debug("set Authentication to security context for '{}', uri: {}", authentication.getName(), requestRri);
        }else {
            log.debug("no valid JWT token found, uri: {}", requestRri);
        }
        chain.doFilter(request,response);
    }

    private String resolveToken(HttpServletRequest request) {
        SpringSecurityProperities properties = SpringContextHolder.getBean(SpringSecurityProperities.class);
        String bearerToken = request.getHeader(properties.getHeader());
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(properties.getTokenStartWith())) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
