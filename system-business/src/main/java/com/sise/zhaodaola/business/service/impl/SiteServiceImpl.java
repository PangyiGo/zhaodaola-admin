package com.sise.zhaodaola.business.service.impl;

import cn.hutool.core.date.DatePattern;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sise.zhaodaola.business.entity.Category;
import com.sise.zhaodaola.business.entity.Site;
import com.sise.zhaodaola.business.mapper.SiteMapper;
import com.sise.zhaodaola.business.service.SiteService;
import com.sise.zhaodaola.business.service.dto.SiteQueryDto;
import com.sise.zhaodaola.tool.dict.DictManager;
import com.sise.zhaodaola.tool.exception.BadRequestException;
import com.sise.zhaodaola.tool.utils.FileUtils;
import com.sise.zhaodaola.tool.utils.PageHelper;
import com.sise.zhaodaola.tool.utils.PageUtils;
import com.sise.zhaodaola.tool.utils.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: PangYi
 * @Date 2020/3/610:57 下午
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class SiteServiceImpl extends ServiceImpl<SiteMapper, Site> implements SiteService {


    @Override
    public PageHelper getSiteList(SiteQueryDto siteQueryDto) {
        Page<Site> sitePage = new Page<>(siteQueryDto.getPage(), siteQueryDto.getSize());
        IPage<Site> page = super.page(sitePage, wrapper(siteQueryDto));
        return PageUtils.toPage(page.getRecords(), page.getCurrent(), page.getSize(), page.getTotal());
    }

    @Override
    public List<Site> getSiteAll(SiteQueryDto siteQueryDto) {
        return super.list(wrapper(siteQueryDto));
    }

    @Override
    public void download(List<Site> siteList, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> mapList = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_PATTERN);
        siteList.forEach(site -> {
            Map<String, Object> map = new LinkedHashMap<>();
            map.put("认领站点地址", site.getAddress());
            map.put("负责人", site.getCharge());
            map.put("联系方式", site.getCharge());
            map.put("创建时间", formatter.format(site.getCreateTime()));
            map.put("更新时间", formatter.format(site.getUpdateTime()));
            map.put("状态", DictManager.show(site.getStatus()));
            mapList.add(map);
        });
        FileUtils.downloadExcel(mapList, response);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteSite(List<Integer> sites) {
        super.removeByIds(sites);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateSite(Site site) {
        site.setUpdateTime(LocalDateTime.now());
        super.updateById(site);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createSite(Site site) {
        site.setCreateTime(LocalDateTime.now());
        site.setUpdateTime(LocalDateTime.now());
        LambdaQueryWrapper<Site> eq = Wrappers.<Site>lambdaQuery().eq(Site::getAddress, site.getAddress());
        if (super.getOne(eq) != null)
            throw new BadRequestException("认领站点地址重复添加");
        super.save(site);
    }

    private LambdaQueryWrapper<Site> wrapper(SiteQueryDto siteQueryDto) {
        LambdaQueryWrapper<Site> wrapper = Wrappers.<Site>lambdaQuery();
        wrapper.eq(siteQueryDto.getStatus() != 0, Site::getStatus, siteQueryDto.getStatus());
        wrapper.and(StringUtils.isNoneBlank(siteQueryDto.getWord()), q -> {
            q.or().like(Site::getAddress, siteQueryDto.getWord());
            q.or().like(Site::getCharge, siteQueryDto.getWord());
        });
        return wrapper;
    }
}
