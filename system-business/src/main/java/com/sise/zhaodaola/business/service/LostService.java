package com.sise.zhaodaola.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sise.zhaodaola.business.entity.Lost;
import com.sise.zhaodaola.business.service.dto.LostFoundBasicDto;

/**
 * @Author: PangYi
 * @Date 2020/3/610:46 下午
 */
public interface LostService extends IService<Lost> {

    /**
     * 寻物登记发布
     *
     * @param lostFoundBasicDto /
     */
    void publishLost(LostFoundBasicDto lostFoundBasicDto);
}
