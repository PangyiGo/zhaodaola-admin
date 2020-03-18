package com.sise.zhaodaola.business.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sise.zhaodaola.business.entity.Lost;
import com.sise.zhaodaola.business.service.dto.*;
import com.sise.zhaodaola.business.service.vo.LostFoundQueryVo;
import com.sise.zhaodaola.tool.utils.PageHelper;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

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

    /**
     * 分页查询寻物启事列表
     *
     * @param lostFoundQueryDto /
     * @return /
     */
    PageHelper getListToPage(LostFoundQueryDto lostFoundQueryDto, PageQueryCriteria queryCriteria);

    /**
     * 数据导出
     *
     * @param lostFoundQueryVos /
     * @param response          /
     */
    void download(List<LostFoundQueryVo> lostFoundQueryVos, HttpServletResponse response) throws IOException;

    /**
     * 不分页条件查询数据
     *
     * @param lostFoundQueryDto /
     * @return /
     */
    List<LostFoundQueryVo> getAll(LostFoundQueryDto lostFoundQueryDto);

    /**
     * 删除
     *
     * @param lostIds id
     */
    void deleteLost(List<Integer> lostIds);

    /**
     * 更新寻物启事信息
     */
    void updateLost(LostSingleUpdateDto lostSingleUpdateDto);

    /**
     * 获取单个寻物启事数据
     *
     * @param lostId /
     * @return /
     */
    LostSingleUpdateDto getOne(Integer lostId);
}
