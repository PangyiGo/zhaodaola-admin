package com.sise.zhaodaola.core.security.security;

import com.sise.zhaodaola.core.security.config.SpringSecurityProperities;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * @Author: PangYi
 * @Date 2020/3/84:19 下午
 */
@Slf4j
@Component
public class TokenProvider implements InitializingBean {

    private SpringSecurityProperities properities;

    private Key key;

    private static final String AUTHORITIES_KEY = "auth";

    public TokenProvider(SpringSecurityProperities properities) {
        this.properities = properities;
    }

    /**
     * 启动SpringbootApplication执行
     * 生成密钥
     *
     * @throws Exception /
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        byte[] keyBytes = Decoders.BASE64.decode(properities.getBase64Secret());
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * 创建JwtToken
     *
     * @param authentication 认证对象
     * @return jwt
     */
    public String createToken(Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();
        Date validity = new Date(now + properities.getTokenValidityInSeconds());

        return Jwts.builder()
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(validity)
                .compact();
    }

    /**
     * 获取认证对象
     *
     * @param token jwt
     * @return /
     */
    Authentication getAuthentication(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        User principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    /**
     * 验证token
     *
     * @param authToken /
     * @return /
     */
    boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(key).parseClaimsJws(authToken);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT signature.");
            e.printStackTrace();
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT token.");
            e.printStackTrace();
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT token.");
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            log.info("JWT token compact of handler are invalid.");
            e.printStackTrace();
        }
        return false;
    }

    public String getToken(HttpServletRequest request) {
        final String requestHeader = request.getHeader(properities.getHeader());
        if (requestHeader != null && requestHeader.startsWith(properities.getTokenStartWith())) {
            return requestHeader.substring(7);
        }
        return null;
    }
}
