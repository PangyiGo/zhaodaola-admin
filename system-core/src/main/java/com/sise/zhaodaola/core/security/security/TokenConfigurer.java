package com.sise.zhaodaola.core.security.security;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @Author: PangYi
 * @Date 2020/3/812:28 下午
 */
public class TokenConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final TokenProvider tokenProvider;

    public TokenConfigurer(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    /**
     * 在认证前添加自定义拦截器
     *
     * @param http /
     */
    @Override
    public void configure(HttpSecurity http) {
        TokenFilter customFilter = new TokenFilter(tokenProvider);
        http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
