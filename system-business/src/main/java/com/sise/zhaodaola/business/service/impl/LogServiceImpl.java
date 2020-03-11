package com.sise.zhaodaola.business.service.impl;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sise.zhaodaola.business.entity.Log;
import com.sise.zhaodaola.business.mapper.LogMapper;
import com.sise.zhaodaola.business.service.LogService;
import com.sise.zhaodaola.tool.utils.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * @Author: PangYi
 * @Date 2020/3/610:57 下午
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class LogServiceImpl extends ServiceImpl<LogMapper, Log> implements LogService {


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void save(String username, String browser, String ip, ProceedingJoinPoint joinPoint, Log log) {
        // 获取Log注解
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        com.sise.zhaodaola.tool.annotation.Log aopLog = method.getAnnotation(com.sise.zhaodaola.tool.annotation.Log.class);

        // 方法路径
        String methodName = joinPoint.getTarget().getClass().getName() + "." + signature.getName() + "()";

        StringBuilder params = new StringBuilder("{");
        //参数值
        Object[] argValues = joinPoint.getArgs();
        //参数名称
        String[] argNames = ((MethodSignature) joinPoint.getSignature()).getParameterNames();
        if (argValues != null) {
            for (int i = 0; i < argValues.length; i++) {
                params.append(" ").append(argNames[i]).append(": ").append(argValues[i]);
            }
        }
        // 描述
        if (log != null) {
            log.setDescription(aopLog.value());
        }

        // 获取IP
        assert log != null;
        log.setRequestIp(ip);

        String loginPath = "login";
        if (loginPath.equals(signature.getName())) {
            try {
                assert argValues != null;
                username = new JSONObject(argValues[0]).get("username").toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        log.setCreateTime(LocalDateTime.now());
        log.setAddress(StringUtils.getCityInfo(log.getRequestIp()));
        log.setMethod(methodName);
        log.setUsername(username);
        log.setParams(params.toString() + " }");
        log.setBrowser(browser);
        // 存储
        super.save(log);
    }
}
