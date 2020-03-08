package com.sise.zhaodaola.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sise.zhaodaola.business.entity.Announce;
import com.sise.zhaodaola.business.entity.Log;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.scheduling.annotation.Async;

/**
 * @Author: PangYi
 * @Date 2020/3/610:46 下午
 */
public interface LogSerivce extends IService<Log> {

    /**
     * 保存日志数据
     * @param username 用户
     * @param browser 浏览器
     * @param ip 请求IP
     * @param joinPoint /
     * @param log 日志实体
     */
    @Async
    void save(String username, String browser, String ip, ProceedingJoinPoint joinPoint, Log log);
}
