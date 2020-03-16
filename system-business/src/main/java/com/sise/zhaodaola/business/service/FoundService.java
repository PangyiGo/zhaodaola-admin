package com.sise.zhaodaola.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sise.zhaodaola.business.entity.Found;
import com.sise.zhaodaola.business.service.dto.LostFoundBasicDto;

/**
 * @Author: PangYi
 * @Date 2020/3/610:46 下午
 */
public interface FoundService extends IService<Found> {

    /**
     * 认领登记发布
     *
     * @param lostFoundBasicDto /
     */
    void publishLost(LostFoundBasicDto lostFoundBasicDto);
}
