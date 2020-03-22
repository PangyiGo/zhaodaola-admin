package com.sise.zhaodaola.business.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sise.zhaodaola.business.entity.News;
import com.sise.zhaodaola.business.mapper.NewsMapper;
import com.sise.zhaodaola.business.service.NewsService;
import com.sise.zhaodaola.business.service.NewsTypeService;
import com.sise.zhaodaola.business.service.dto.NewsQueryDto;
import com.sise.zhaodaola.business.service.dto.PageQueryCriteria;
import com.sise.zhaodaola.business.service.vo.NewsQueryVo;
import com.sise.zhaodaola.tool.dict.DictManager;
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
public class NewsServiceImpl extends ServiceImpl<NewsMapper, News> implements NewsService {

    private NewsTypeService newsTypeService;

    public NewsServiceImpl(NewsTypeService newsTypeService) {
        this.newsTypeService = newsTypeService;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void publishNews(News news) {
        news.setStatus(1);
        news.setCreateTime(LocalDateTime.now());
        news.setUpdateTime(LocalDateTime.now());
        news.setUuid(IdUtil.simpleUUID());
        super.save(news);
    }

    @Override
    public PageHelper getListNews(NewsQueryDto newsQueryDto, PageQueryCriteria queryCriteria) {
        Page<News> newsPage = new Page<>(queryCriteria.getPage(), queryCriteria.getSize());
        Page<News> resultPage = super.page(newsPage, wrapper(newsQueryDto));
        List<NewsQueryVo> queryVoList = recode(resultPage.getRecords());
        return PageUtils.toPage(queryVoList, resultPage.getCurrent(), resultPage.getSize(), resultPage.getTotal());
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(List<Integer> newsIds) {
        super.removeByIds(newsIds);
    }

    @Override
    public NewsQueryVo viewNews(Integer newsId) {
        News news = super.getById(newsId);
        NewsQueryVo newsQueryVo = new NewsQueryVo();
        BeanUtil.copyProperties(news, newsQueryVo);
        String newsType = newsTypeService.getById(news.getType()).getName();
        newsQueryVo.setType(newsType);
        newsQueryVo.setPlacement(DictManager.placement(news.getPlacement()));
        return newsQueryVo;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void update(News news) {
        news.setUpdateTime(LocalDateTime.now());
        super.updateById(news);
    }

    @Override
    public News edtior(Integer newsId) {
        return super.getById(newsId);
    }

    private List<NewsQueryVo> recode(List<News> newsList) {
        List<NewsQueryVo> newsQueryVoList = new ArrayList<>(0);
        newsList.forEach(news -> {
            NewsQueryVo newsQueryVo = new NewsQueryVo();
            BeanUtil.copyProperties(news, newsQueryVo);
            String newsType = newsTypeService.getById(news.getType()).getName();
            newsQueryVo.setType(newsType);
            newsQueryVo.setPlacement(DictManager.placement(news.getPlacement()));
            newsQueryVoList.add(newsQueryVo);
        });
        return newsQueryVoList;
    }

    private LambdaQueryWrapper<News> wrapper(NewsQueryDto newsQueryDto) {
        LambdaQueryWrapper<News> wrapper = Wrappers.<News>lambdaQuery();
        wrapper.and(StringUtils.isNotBlank(newsQueryDto.getWord()), query -> {
            query.or().like(News::getTitle, newsQueryDto.getWord());
            query.or().like(News::getAuthor, newsQueryDto.getWord());
        });
        if (StringUtils.isNotBlank(newsQueryDto.getStart()) && StringUtils.isNotBlank(newsQueryDto.getEnd()))
            wrapper.between(News::getCreateTime, DateTimeUtils.dateTime(newsQueryDto.getStart(), DatePattern.NORM_DATETIME_PATTERN), DateTimeUtils.dateTime(newsQueryDto.getEnd(), DatePattern.NORM_DATETIME_PATTERN));
        wrapper.eq(newsQueryDto.getStatus() > 0, News::getStatus, newsQueryDto.getStatus());
        wrapper.eq(newsQueryDto.getType() > 0, News::getType, newsQueryDto.getType());
        wrapper.eq(StringUtils.isNotBlank(newsQueryDto.getDept()), News::getDept, newsQueryDto.getDept());
        return wrapper;
    }

}
