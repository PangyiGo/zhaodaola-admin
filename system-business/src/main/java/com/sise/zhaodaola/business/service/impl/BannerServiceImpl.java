package com.sise.zhaodaola.business.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sise.zhaodaola.business.entity.Banner;
import com.sise.zhaodaola.business.entity.News;
import com.sise.zhaodaola.business.mapper.BannerMapper;
import com.sise.zhaodaola.business.service.BannerService;
import com.sise.zhaodaola.business.service.NewsService;
import com.sise.zhaodaola.business.service.dto.BasicQueryDto;
import com.sise.zhaodaola.business.service.dto.PageQueryCriteria;
import com.sise.zhaodaola.business.service.vo.BannerQueryVo;
import com.sise.zhaodaola.tool.utils.DateTimeUtils;
import com.sise.zhaodaola.tool.utils.PageHelper;
import com.sise.zhaodaola.tool.utils.PageUtils;
import com.sise.zhaodaola.tool.utils.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: PangYi
 * @Date 2020/3/610:57 下午
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class BannerServiceImpl extends ServiceImpl<BannerMapper, Banner> implements BannerService {

    private NewsService newsService;

    public BannerServiceImpl(NewsService newsService) {
        this.newsService = newsService;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createBanner(Banner banner) {
        banner.setCreateTime(LocalDateTime.now());
        banner.setUpdateTime(LocalDateTime.now());
        banner.setUuid(IdUtil.simpleUUID());
        banner.setStatus(1);
        super.save(banner);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deleteBanner(List<Integer> bannerIds) {
        super.removeByIds(bannerIds);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateBanner(Banner banner) {
        banner.setUpdateTime(LocalDateTime.now());
        super.updateById(banner);
    }

    @Override
    public PageHelper getBannerList(BasicQueryDto basicQueryDto, PageQueryCriteria queryCriteria) {
        Page<Banner> bannerPage = new Page<>(queryCriteria.getPage(), queryCriteria.getSize());
        Page<Banner> page = super.page(bannerPage, wrapper(basicQueryDto));
        List<BannerQueryVo> bannerQueryVoList = new ArrayList<>(0);
        page.getRecords().forEach(banner -> {
            bannerQueryVoList.add(recode(banner));
        });
        return PageUtils.toPage(bannerQueryVoList, page.getCurrent(), page.getSize(), page.getTotal());
    }

    @Override
    public Banner getOne(Integer bannerId) {
        return super.getById(bannerId);
    }

    @Override
    public List<BannerQueryVo> getBanners(Integer type) {
        BasicQueryDto basicQueryDto = new BasicQueryDto();
        basicQueryDto.setCategory(type);
        basicQueryDto.setStatus(1);

        List<Banner> bannerList = super.list(wrapper(basicQueryDto).orderByDesc(Banner::getCreateTime));

        List<BannerQueryVo> bannerQueryVoList = new ArrayList<>(0);
        bannerList.forEach(banner -> {
            bannerQueryVoList.add(recode(banner));
        });
        return bannerQueryVoList;
    }

    private BannerQueryVo recode(Banner banner) {
        BannerQueryVo bannerQueryVo = new BannerQueryVo();
        BeanUtil.copyProperties(banner, bannerQueryVo);
        News news = newsService.getOne(Wrappers.<News>lambdaQuery().select(News::getId, News::getTitle).eq(StringUtils.isNotBlank(banner.getLink()), News::getUuid, banner.getLink()));
        if (ObjectUtil.isNotNull(news)) {
            bannerQueryVo.setNewsId(news.getId());
            bannerQueryVo.setNewsTitle(news.getTitle());
        }
        return bannerQueryVo;
    }

    private LambdaQueryWrapper<Banner> wrapper(BasicQueryDto basicQueryDto) {
        LambdaQueryWrapper<Banner> wrapper = Wrappers.<Banner>lambdaQuery();
        if (ObjectUtils.isNotEmpty(basicQueryDto)) {
            wrapper.and(StringUtils.isNoneBlank(basicQueryDto.getWord()), q -> {
                q.or().like(Banner::getTitle, basicQueryDto.getWord());
            });
            wrapper.eq(basicQueryDto.getStatus() != 0, Banner::getStatus, basicQueryDto.getStatus());
            wrapper.eq(basicQueryDto.getCategory() != 0, Banner::getType, basicQueryDto.getStatus());
            if (StringUtils.isNotBlank(basicQueryDto.getStart()) && StringUtils.isNotBlank(basicQueryDto.getEnd()))
                wrapper.between(Banner::getCreateTime, DateTimeUtils.dateTime(basicQueryDto.getStart(), DatePattern.NORM_DATETIME_PATTERN), DateTimeUtils.dateTime(basicQueryDto.getEnd(), DatePattern.NORM_DATETIME_PATTERN));
        }
        wrapper.orderByDesc(Banner::getCreateTime);
        return wrapper;
    }
}
