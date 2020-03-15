package com.sise.zhaodaola.core.lostAndfound;

import com.sise.zhaodaola.business.entity.Category;
import com.sise.zhaodaola.business.entity.Site;
import com.sise.zhaodaola.business.service.SiteService;
import com.sise.zhaodaola.business.service.dto.CategoryQueryDto;
import com.sise.zhaodaola.business.service.dto.SiteQueryDto;
import com.sise.zhaodaola.tool.annotation.Log;
import com.sise.zhaodaola.tool.utils.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @Author: PangYi
 * @Date 2020/3/1512:00 下午
 */
@RestController
@RequestMapping("/api/site")
@Slf4j
public class SiteController {

    private SiteService siteService;

    public SiteController(SiteService siteService) {
        this.siteService = siteService;
    }

    @Log("认领站点查询")
    @PreAuthorize("@auth.check('site:list')")
    @PostMapping("/list")
    public ResponseEntity<Object> getSiteList(SiteQueryDto queryDto) {
        PageHelper siteList = siteService.getSiteList(queryDto);
        return ResponseEntity.ok(siteList);
    }

    @Log("认领站点数据导出")
    @PreAuthorize("@auth.check('site:download')")
    @PostMapping("/download")
    public void download(SiteQueryDto siteQueryDto, HttpServletResponse response) throws IOException {
        siteService.download(siteService.getSiteAll(siteQueryDto), response);
    }

    @Log("认领站点新增")
    @PreAuthorize("@auth.check('site:add')")
    @PostMapping("/create")
    public ResponseEntity<Object> create(@RequestBody Site site) {
        siteService.createSite(site);
        return new ResponseEntity<>("新增认领站点成功", HttpStatus.OK);
    }

    @Log("认领站点删除")
    @PreAuthorize("@auth.check('site:delete')")
    @PostMapping("/delete")
    public ResponseEntity<Object> delete(@RequestBody List<Integer> sites) {
        siteService.deleteSite(sites);
        return new ResponseEntity<>("认领站点删除成功", HttpStatus.OK);
    }

    @Log("认领站点修改")
    @PreAuthorize("@auth.check('site:update')")
    @PostMapping("/update")
    public ResponseEntity<Object> update(@RequestBody Site site) {
        siteService.updateSite(site);
        return new ResponseEntity<>("认领站点修改成功", HttpStatus.OK);
    }
}
