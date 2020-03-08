package com.sise.zhaodaola.core;

import com.sise.zhaodaola.tool.utils.StringUtils;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import javax.swing.*;
import java.util.Base64;
import java.util.Date;

/**
 * @Author: PangYi
 * @Date 2020/3/81:16 上午
 */
public class JwtTest {

    public static void main(String[] args) {
        String compact = Jwts.builder()
                .setIssuer("Pangyi")
                .setIssuedAt(new Date())
                .claim("User", "ok")
                .setSubject("Hello")
                .setExpiration(new Date(System.currentTimeMillis() + 2000 * 1000))
                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode("ZmQ0ZGI5NjQ0MDQwY2I4MjMxY2Y3ZmI3MjdhN2ZmMjNhODViOTg1ZGE0NTBjMGM4NDA5NzYxMjdjOWMwYWRmZTBlZjlhNGY3ZTg4Y2U3YTE1ODVkZDU5Y2Y3OGYwZWE1NzUzNWQ2YjFjZDc0NGMxZWU2MmQ3MjY1NzJmNTE0MzI=")), SignatureAlgorithm.HS512)
                .compact();
        System.out.println(compact);

        Claims body = (Claims) Jwts.parser().setSigningKey(Keys.hmacShaKeyFor(Decoders.BASE64.decode("ZmQ0ZGI5NjQ0MDQwY2I4MjMxY2Y3ZmI3MjdhN2ZmMjNhODViOTg1ZGE0NTBjMGM4NDA5NzYxMjdjOWMwYWRmZTBlZjlhNGY3ZTg4Y2U3YTE1ODVkZDU5Y2Y3OGYwZWE1NzUzNWQ2YjFjZDc0NGMxZWU2MmQ3MjY1NzJmNTE0MzI="))).parse(compact).getBody();
        System.out.println(body);

        String cityInfo = StringUtils.getCityInfo("219.133.250.136");
        System.out.println(cityInfo);
    }
}
