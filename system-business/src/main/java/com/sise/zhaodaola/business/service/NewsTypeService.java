package com.sise.zhaodaola.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sise.zhaodaola.business.entity.NewsType;
import com.sise.zhaodaola.business.service.dto.BasicQueryDto;
import com.sise.zhaodaola.business.service.dto.PageQueryCriteria;
import com.sise.zhaodaola.tool.utils.PageHelper;

import java.util.List;

/**
 * @Author: PangYi
 * @Date 2020/3/610:46 下午
 */
public interface NewsTypeService extends IService<NewsType> {

    /**
     * 查询校园资讯类型列表
     *
     * @param basicQueryDto /
     * @param queryCriteria /
     * @return /
     */
    PageHelper getList(BasicQueryDto basicQueryDto, PageQueryCriteria queryCriteria);

    /**
     * 不分页获取全部校园资讯分类
     *
     * @return /
     */
    List<NewsType> getAll();

    /**
     * 新增校园资讯类型
     *
     * @param newsType /
     */
    void createNewsType(NewsType newsType);

    /**
     * 修改校园资讯类型
     *
     * @param newsType /
     */
    void updateNewsType(NewsType newsType);

    /**
     * 删除校园资讯类型
     *
     * @param typeIds /
     */
    void deleteNewsType(List<Integer> typeIds);

}
