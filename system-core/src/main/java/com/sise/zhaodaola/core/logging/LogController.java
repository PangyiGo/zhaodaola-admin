package com.sise.zhaodaola.core.logging;

import com.sise.zhaodaola.business.service.LogService;
import com.sise.zhaodaola.business.service.dto.BasicQueryDto;
import com.sise.zhaodaola.business.service.dto.PageQueryCriteria;
import com.sise.zhaodaola.tool.annotation.Log;
import com.sise.zhaodaola.tool.utils.PageHelper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * User: PangYi
 * Date: 2020-03-24
 * Time: 17:38
 * Description:
 */
@RestController
@RequestMapping("/api/log")
public class LogController {

    private LogService logService;

    public LogController(LogService logService) {
        this.logService = logService;
    }

    @PostMapping("/list/{type}")
    public ResponseEntity<Object> getLogList(@PathVariable("type") String type, BasicQueryDto basicQueryDto, PageQueryCriteria queryCriteria) {
        PageHelper pageHelper = logService.getInfoLog(basicQueryDto, queryCriteria, type);
        return ResponseEntity.ok(pageHelper);
    }

    @Log("日志导出")
    @PostMapping("/download/{type}")
    public void download(@PathVariable("type") String type, BasicQueryDto basicQueryDto, HttpServletResponse response) throws IOException {
        logService.download(logService.getAllList(basicQueryDto, type), response);
    }
}
