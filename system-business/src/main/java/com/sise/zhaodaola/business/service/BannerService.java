package com.sise.zhaodaola.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sise.zhaodaola.business.entity.Banner;
import com.sise.zhaodaola.business.service.dto.BasicQueryDto;
import com.sise.zhaodaola.business.service.dto.PageQueryCriteria;
import com.sise.zhaodaola.tool.utils.PageHelper;

import java.util.List;

/**
 * @Author: PangYi
 * @Date 2020/3/610:46 下午
 */
public interface BannerService extends IService<Banner> {

    /**
     * 新增轮播图
     *
     * @param banner /
     */
    void createBanner(Banner banner);

    /**
     * 删除轮播图
     *
     * @param bannerIds /
     */
    void deleteBanner(List<Integer> bannerIds);

    /**
     * 修改轮播图
     *
     * @param banner /
     */
    void updateBanner(Banner banner);

    /**
     * 分页查询轮播图列表
     *
     * @param basicQueryDto /
     * @param queryCriteria /
     * @return /
     */
    PageHelper getBannerList(BasicQueryDto basicQueryDto, PageQueryCriteria queryCriteria);

    /**
     * 查询
     *
     * @param bannerId /
     * @return /
     */
    Banner getOne(Integer bannerId);
}
