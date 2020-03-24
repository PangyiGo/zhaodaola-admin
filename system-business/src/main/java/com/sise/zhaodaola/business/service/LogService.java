package com.sise.zhaodaola.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sise.zhaodaola.business.entity.Log;
import com.sise.zhaodaola.business.service.dto.BasicQueryDto;
import com.sise.zhaodaola.business.service.dto.PageQueryCriteria;
import com.sise.zhaodaola.tool.utils.PageHelper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.scheduling.annotation.Async;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @Author: PangYi
 * @Date 2020/3/610:46 下午
 */
public interface LogService extends IService<Log> {

    /**
     * 保存日志数据
     *
     * @param username  用户
     * @param browser   浏览器
     * @param ip        请求IP
     * @param joinPoint /
     * @param log       日志实体
     */
    @Async
    void save(String username, String browser, String ip, ProceedingJoinPoint joinPoint, Log log);

    /**
     * 分页查询
     *
     * @param basicQueryDto /
     * @param queryCriteria /
     * @param type          /
     * @return /
     */
    PageHelper getInfoLog(BasicQueryDto basicQueryDto, PageQueryCriteria queryCriteria, String type);

    /**
     * 查询
     *
     * @param basicQueryDto /
     * @param type          /
     * @return /
     */
    List<Log> getAllList(BasicQueryDto basicQueryDto, String type);

    /**
     * 数据导出
     *
     * @param logList  /
     * @param response /
     */
    void download(List<Log> logList, HttpServletResponse response) throws IOException;
}
