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

    /**
     * 查询
     *
     * @param uuid /
     * @return /
     */
    Lost getByUuid(String uuid);

    /**
     * 获取首页寻取启事
     *
     * @return /
     */
    List<LostFoundQueryVo> getLostIndex();

    /**
     * 查看寻物启事信息
     *
     * @param id /
     * @return /
     */
    LostFoundQueryVo showLostOne(Integer id);

    /**
     * 相关推荐
     *
     * @param category /
     * @return /
     */
    List<LostFoundQueryVo> pushLost(String category, Integer slfe);

    /**
     * 删除寻物启事
     *
     * @param lostId /
     */
    void delete(Integer lostId);
}
