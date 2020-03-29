package com.sise.zhaodaola.business.service.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sise.zhaodaola.business.entity.Log;
import com.sise.zhaodaola.business.mapper.LogMapper;
import com.sise.zhaodaola.business.service.LogService;
import com.sise.zhaodaola.business.service.dto.BasicQueryDto;
import com.sise.zhaodaola.business.service.dto.PageQueryCriteria;
import com.sise.zhaodaola.tool.utils.*;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: PangYi
 * @Date 2020/3/610:57 下午
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class LogServiceImpl extends ServiceImpl<LogMapper, Log> implements LogService {

    private static String INFO = "INFO";

    private static String ERROR = "ERROR";

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

    @Override
    public PageHelper getInfoLog(BasicQueryDto basicQueryDto, PageQueryCriteria queryCriteria, String type) {
        Page<Log> logPage = new Page<>(queryCriteria.getPage(), queryCriteria.getSize());
        Page<Log> result = super.page(logPage, wrapper(basicQueryDto, type));
        return PageUtils.toPage(result.getRecords(), result.getCurrent(), result.getSize(), result.getTotal());
    }

    @Override
    public List<Log> getAllList(BasicQueryDto basicQueryDto, String type) {
        return super.list(wrapper(basicQueryDto, type));
    }

    @Override
    public void download(List<Log> logList, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> download = new ArrayList<>(0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_PATTERN);
        logList.forEach(log1 -> {
            Map<String, Object> map = new HashMap<>();
            map.put("用户名", log1.getUsername());
            map.put("IP", log1.getRequestIp());
            map.put("IP来源", log1.getAddress());
            map.put("描述", log1.getDescription());
            map.put("浏览器", log1.getBrowser());
            map.put("请求耗时", log1.getTime());
            map.put("异常详情", log1.getDetail());
            map.put("创建日期", formatter.format(log1.getCreateTime()));
            download.add(map);
        });
        FileUtils.downloadExcel(download, response);
    }

    private LambdaQueryWrapper<Log> wrapper(BasicQueryDto basicQueryDto, String type) {
        LambdaQueryWrapper<Log> wrapper = Wrappers.<Log>lambdaQuery();
        if (ObjectUtils.isNotEmpty(basicQueryDto)) {
            wrapper.and(StringUtils.isNoneBlank(basicQueryDto.getWord()), q -> {
                q.or().like(Log::getUsername, basicQueryDto.getWord());
                q.or().like(Log::getDescription, basicQueryDto.getWord());
            });
            wrapper.eq(StringUtils.isNotBlank(type), Log::getLogType, type);
            if (StringUtils.isNotBlank(basicQueryDto.getStart()) && StringUtils.isNotBlank(basicQueryDto.getEnd()))
                wrapper.between(Log::getCreateTime, DateTimeUtils.dateTime(basicQueryDto.getStart(), DatePattern.NORM_DATETIME_PATTERN), DateTimeUtils.dateTime(basicQueryDto.getEnd(), DatePattern.NORM_DATETIME_PATTERN));
        }
        wrapper.orderByDesc(Log::getCreateTime);
        return wrapper;
    }
}
