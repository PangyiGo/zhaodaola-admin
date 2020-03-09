package com.sise.zhaodaola.core.security.rest;

import cn.hutool.core.util.IdUtil;
import com.sise.zhaodaola.core.security.config.SpringSecurityProperities;
import com.sise.zhaodaola.core.security.security.TokenProvider;
import com.sise.zhaodaola.core.security.security.vo.AuthUser;
import com.sise.zhaodaola.core.security.security.vo.JwtUser;
import com.sise.zhaodaola.tool.annotation.AnonymousAccess;
import com.sise.zhaodaola.tool.annotation.Log;
import com.sise.zhaodaola.tool.exception.BadRequestException;
import com.sise.zhaodaola.tool.utils.RedisUtils;
import com.sise.zhaodaola.tool.utils.SecurityUtils;
import com.sise.zhaodaola.tool.utils.StringUtils;
import com.wf.captcha.ArithmeticCaptcha;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author: PangYi
 * @Date 2020/3/85:07 下午
 */
@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthController {

    @Value("${loginCode.expiration}")
    private Long expiration;
    private RedisUtils redisUtils;
    private SpringSecurityProperities properities;
    private AuthenticationManagerBuilder authenticationManagerBuilder;
    private TokenProvider tokenProvider;
    private UserDetailsService userDetailsService;

    public AuthController(RedisUtils redisUtils,
                          SpringSecurityProperities properities,
                          AuthenticationManagerBuilder authenticationManagerBuilder,
                          TokenProvider tokenProvider,
                          UserDetailsService userDetailsService) {
        this.redisUtils = redisUtils;
        this.properities = properities;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.tokenProvider = tokenProvider;
        this.userDetailsService = userDetailsService;
    }

    /**
     * 用户身份登陆认证
     *
     * @param authUser /
     * @param request  /
     * @return /
     */
    @Log("用户登录")
    @AnonymousAccess
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody AuthUser authUser, HttpServletRequest request) {
        // 获取code
        String code = (String) redisUtils.get(authUser.getUuid());
        // 清除验证码
        redisUtils.del(authUser.getUuid());
        if (StringUtils.isBlank(code)) {
            throw new BadRequestException("验证码不存在或已过期");
        }
        if (StringUtils.isBlank(authUser.getCode()) || !authUser.getCode().equalsIgnoreCase(code)) {
            throw new BadRequestException("验证码错误");
        }

        // 身份认证
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(authUser.getUsername(), authUser.getPassword(), null);
        Authentication authenticate = this.authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authenticate);

        // 生成令牌
        String token = tokenProvider.createToken(authenticate);
        final JwtUser jwtUser = (JwtUser) authenticate.getPrincipal();
        // 返回 token 与 用户信息
        Map<String, Object> authInfo = new HashMap<String, Object>(2) {{
            put("token", properities.getTokenStartWith() + token);
            put("user", jwtUser);
        }};
        return ResponseEntity.ok(authInfo);
    }

    /**
     * 获取图形验证码
     *
     * @return /
     */
    @AnonymousAccess
    @PostMapping(value = "/code")
    public ResponseEntity<Object> getCode() {
        // 算术类型 https://gitee.com/whvse/EasyCaptcha
        ArithmeticCaptcha captcha = new ArithmeticCaptcha(111, 36);
        // 几位数运算，默认是两位
        captcha.setLen(2);
        // 获取运算的结果
        String result = captcha.text();
        String uuid = properities.getCodeKey() + IdUtil.simpleUUID();
        // 保存
        redisUtils.set(uuid, result, expiration, TimeUnit.MINUTES);
        // 验证码信息
        Map<String, Object> imgResult = new HashMap<String, Object>(2) {{
            put("img", captcha.toBase64());
            put("uuid", uuid);
        }};
        return ResponseEntity.ok(imgResult);
    }

    /**
     * 获取用户信息
     *
     * @return /
     */
    @PostMapping(value = "/info")
    public ResponseEntity<Object> getUserInfo() {
        JwtUser jwtUser = (JwtUser) userDetailsService.loadUserByUsername(SecurityUtils.getUsername());
        return ResponseEntity.ok(jwtUser);
    }
}
