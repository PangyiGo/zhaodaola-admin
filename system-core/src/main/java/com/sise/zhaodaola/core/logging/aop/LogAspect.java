package com.sise.zhaodaola.core.logging.aop;

import com.sise.zhaodaola.business.entity.Log;
import com.sise.zhaodaola.business.service.LogSerivce;
import com.sise.zhaodaola.tool.utils.RequestHolder;
import com.sise.zhaodaola.tool.utils.SecurityUtils;
import com.sise.zhaodaola.tool.utils.StringUtils;
import com.sise.zhaodaola.tool.utils.ThrowableUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import static com.sise.zhaodaola.tool.utils.SecurityUtils.getUsername;

/**
 * @Author: PangYi
 * @Date 2020/3/88:26 下午
 */
@Component
@Aspect
@Slf4j
public class LogAspect {

    private LogSerivce logSerivce;

    ThreadLocal<Long> currentTime = new ThreadLocal<>();

    public LogAspect(LogSerivce logSerivce) {
        this.logSerivce = logSerivce;
    }

    /**
     * 配置切入点
     */
    @Pointcut("@annotation(com.sise.zhaodaola.tool.annotation.Log)")
    private void logPointcut() {
    }

    /**
     * 配置环绕通知,使用在方法logPointcut()上注册的切入点
     *
     * @param joinPoint join point for advice
     */
    @Around("logPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result;
        currentTime.set(System.currentTimeMillis());
        result = joinPoint.proceed();
        Log log = new Log("INFO", System.currentTimeMillis() - currentTime.get());
        currentTime.remove();
        HttpServletRequest request = RequestHolder.getHttpServletRequest();
        logSerivce.save(getUsername(),
                StringUtils.getBrowser(request),
                StringUtils.getIp(request),
                joinPoint, log);
        return result;
    }

    /**
     * 配置异常通知
     *
     * @param joinPoint join point for advice
     * @param e         exception
     */
    @AfterThrowing(pointcut = "logPointcut()", throwing = "e")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
        Log log = new Log("ERROR", System.currentTimeMillis() - currentTime.get());
        currentTime.remove();
        // 异常信息
        log.setDetail(ThrowableUtil.getStackTrace(e));
        HttpServletRequest request = RequestHolder.getHttpServletRequest();
        logSerivce.save(getUsername(), StringUtils.getBrowser(request), StringUtils.getIp(request), (ProceedingJoinPoint) joinPoint, log);
    }

    private String getUsername() {
        try {
            return SecurityUtils.getUsername();
        } catch (Exception e) {
            return "";
        }
    }
}
