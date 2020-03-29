package com.sise.zhaodaola.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sise.zhaodaola.business.entity.Announce;
import com.sise.zhaodaola.business.entity.Thanks;
import com.sise.zhaodaola.business.service.dto.BasicQueryDto;
import com.sise.zhaodaola.business.service.dto.PageQueryCriteria;
import com.sise.zhaodaola.tool.utils.PageHelper;

/**
 * @Author: PangYi
 * @Date 2020/3/610:46 下午
 */
public interface ThanksService extends IService<Thanks> {

    /**
     * 发表留言
     *
     * @param thanks /
     */
    void createThankes(Thanks thanks);

    /**
     * 分页查询
     *
     * @param basicQueryDto /
     * @param queryCriteria /
     * @return /
     */
    PageHelper getList(BasicQueryDto basicQueryDto, PageQueryCriteria queryCriteria);
}
