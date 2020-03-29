package com.sise.zhaodaola.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sise.zhaodaola.business.entity.News;
import com.sise.zhaodaola.business.service.dto.NewsQueryDto;
import com.sise.zhaodaola.business.service.dto.PageQueryCriteria;
import com.sise.zhaodaola.business.service.vo.NewsQueryVo;
import com.sise.zhaodaola.tool.utils.PageHelper;

import java.util.List;

/**
 * @Author: PangYi
 * @Date 2020/3/610:46 下午
 */
public interface NewsService extends IService<News> {

    /**
     * 发布校园资讯
     *
     * @param news /
     */
    void publishNews(News news);

    /**
     * 查询校园资讯列表
     *
     * @param newsQueryDto  /
     * @param queryCriteria /
     * @return /
     */
    PageHelper getListNews(NewsQueryDto newsQueryDto, PageQueryCriteria queryCriteria);

    /**
     * 校园资讯专栏
     *
     * @param newsQueryDto  /
     * @param queryCriteria /
     * @return /
     */
    PageHelper showNewsList(NewsQueryDto newsQueryDto, PageQueryCriteria queryCriteria);

    /**
     * 删除校园资讯
     *
     * @param newsIds /
     */
    void delete(List<Integer> newsIds);

    /**
     * 查看单个校园资讯
     *
     * @param newsId /
     * @return /
     */
    NewsQueryVo viewNews(Integer newsId);

    /**
     * 修改校园资讯
     *
     * @param news /
     */
    void update(News news);

    /**
     * 校园资讯编辑
     *
     * @param newsId /
     * @return /
     */
    News edtior(Integer newsId);

    /**
     * 首页显示
     *
     * @return /
     */
    List<NewsQueryVo> showIndex();
}
