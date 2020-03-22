package com.sise.zhaodaola.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sise.zhaodaola.business.entity.Announce;
import com.sise.zhaodaola.business.service.dto.BasicQueryDto;
import com.sise.zhaodaola.business.service.dto.PageQueryCriteria;
import com.sise.zhaodaola.tool.utils.PageHelper;

import java.util.List;

/**
 * @Author: PangYi
 * @Date 2020/3/610:46 下午
 */
public interface AnnounceService extends IService<Announce> {

    /**
     * 创建网站公告
     *
     * @param announce /
     */
    void createAnnounce(Announce announce);

    /**
     * 删除系统公告
     *
     * @param announceIds /
     */
    void deleteAnnounce(List<Integer> announceIds);

    /**
     * 修改系统公告
     *
     * @param announce /
     */
    void updateAnnonce(Announce announce);

    /**
     * 分页查询系统公告列表
     *
     * @param basicQueryDto /
     * @param queryCriteria /
     * @return /
     */
    PageHelper getAnnounceList(BasicQueryDto basicQueryDto, PageQueryCriteria queryCriteria);

    /**
     * 获取公告
     *
     * @param announceId /
     * @return /
     */
    Announce getOne(Integer announceId);
}
