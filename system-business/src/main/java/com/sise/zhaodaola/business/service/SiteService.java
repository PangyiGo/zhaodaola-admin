package com.sise.zhaodaola.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sise.zhaodaola.business.entity.Announce;
import com.sise.zhaodaola.business.entity.Site;
import com.sise.zhaodaola.business.service.dto.SiteQueryDto;
import com.sise.zhaodaola.tool.utils.PageHelper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @Author: PangYi
 * @Date 2020/3/610:46 下午
 */
public interface SiteService extends IService<Site> {

    /**
     * 分页查询认领站点列表
     *
     * @param siteQueryDto /
     * @return /
     */
    PageHelper getSiteList(SiteQueryDto siteQueryDto);

    /**
     * 不分页查询认领站点列表
     *
     * @param siteQueryDto /
     * @return /
     */
    List<Site> getSiteAll(SiteQueryDto siteQueryDto);

    /**
     * 数据导出
     *
     * @param siteList /
     * @param response /
     */
    void download(List<Site> siteList, HttpServletResponse response) throws IOException;

    /**
     * 删除认领站点
     *
     * @param sites /
     */
    void deleteSite(List<Integer> sites);

    /**
     * 修改认领站点信息
     *
     * @param site /
     */
    void updateSite(Site site);

    /**
     * 新增认领站点信息
     *
     * @param site /
     */
    void createSite(Site site);
}
